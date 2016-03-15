package com.score.sts.util;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.renderscript.ScriptGroup;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;

import com.score.sts.R;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Who Dat on 2/16/2016.
 * This class is used to assist in converting image and video files from the local
 * device to files that can be sent to the server and also stored in the local database
 * provided by STS. This class is a work in progress and may undergo several changes.
 */
public class MediaFileManager {

    private static final String TAG = MediaFileManager.class.getSimpleName();
    private final Context context;
    private static String[] ACCEPTED_MIME_TYPES;
    private static final String UNACCEPTABLE_MIME_TYPE = "Unacceptable Mime Type";

    private static final String FILE_CONTENT_URI = "file content uri";
    private static final String FILE_DATA_TYPE = "file data type";
    private static final String FILE_NAME = "file name";
    private static final String FILE_SIZE = "file size";

    public MediaFileManager(Context context){
        this.context = context;
    }

    private void init(){
        ACCEPTED_MIME_TYPES = context.getResources().getStringArray(R.array.acceptable_mime_types);
    }

    /**
     * this method is intended to get an input stream from either an intent or uri.
     * this would normally be used in onActivityResult() but not solely
     * @param intent
     * @param uri
     * @return
     */
    public InputStream getInputStreamFromUri(@Nullable Intent intent, @Nullable Uri uri){

        if(uri == null) {
            uri = intent.getData();
        }
            InputStream inputStream = null;
        try {
            inputStream = context.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return  inputStream;
    }
    public String getFileName(Intent returnIntent){
            // this method will get take the Intent from
        ParcelFileDescriptor parcelFileDescriptor;
        FileDescriptor fileDescriptor;
        String fileName = "";
        int fileSize = -1;
        boolean acceptableMimeType = false;
            /**
             * Get the file's content URI from the incoming Intent, then
             * get the file's MIME type, query the content provider with
             * the retrieved content uri and get the index of the column
             * that stores the file name and file size
             */
            Uri fileContentUri = returnIntent.getData(); // get the content Uri
            String fileDataType = context.getContentResolver().getType(fileContentUri);

            // TODO study the crash here that happens if the file/mime type doesn't match audio or video
            Cursor cursor = context.getContentResolver().query(fileContentUri, null, null, null, null);
            if(cursor != null) {
                int fileNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                int fileSizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);

                // get file name and file size
                cursor.moveToFirst();
                fileName = cursor.getString(fileNameIndex);
                fileSize = cursor.getInt(fileSizeIndex);
            }
            cursor.close();

            // --- logging the metadata --- //
            Log.d(TAG, "Data Type: " + fileDataType);
            Log.d(TAG, "Data Uri: " + returnIntent.getData().toString());
            Log.d(TAG, "Data String: " + returnIntent.getDataString());
            Log.d(TAG, "File Name: " + fileName);
            Log.d(TAG, "File size: " + fileSize);

            try
            {
                // the ParcelFileDescriptor and FileDescriptor are not currently being used
                parcelFileDescriptor = context.getContentResolver().openFileDescriptor(fileContentUri, "r");
                if(parcelFileDescriptor != null) {
                    fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                    parcelFileDescriptor.close();

                    Log.d(TAG, "File Descriptor: " +  fileDescriptor.toString());
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.e(TAG, "File not found...");
            } catch (IOException ioe){
                ioe.printStackTrace();
            }
        return fileName;
    }

    /**
     * TODO determine if this is only useful for files from content providers. if so, create another method to handle all other files
     * @param metaDataIntent
     * @return
     */
    public Map<String, String> getFileMetadata(Intent metaDataIntent){

        Map<String, String> metaDataMap = new HashMap<>();
        String fileName = "";
        int fileSize = -1;

        /**
         * Get the file's content URI from the incoming Intent to get
         * the available metadata. querying the content provider with
         * the retrieved content uri is necessary to get some metadata.
         */
        Uri fileContentUri = metaDataIntent.getData(); // get the content Uri

        Cursor cursor = context.getContentResolver().query(fileContentUri, null, null, null, null);
        if(cursor != null) {// These are standard columns for openable URIs
            int fileNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            int fileSizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);

            // get file name and file size
            cursor.moveToFirst();
            fileName = cursor.getString(fileNameIndex);
            fileSize = cursor.getInt(fileSizeIndex);
//            String[] columnNames = cursor.getColumnNames(); // TODO use this if the file is from a content provider. do not delete.
        }
        cursor.close();

        metaDataMap.put(FILE_CONTENT_URI, metaDataIntent.toString());
        metaDataMap.put(FILE_DATA_TYPE, context.getContentResolver().getType(fileContentUri));
        metaDataMap.put(FILE_NAME, fileName);
        metaDataMap.put(FILE_SIZE, String.valueOf(fileSize));

        return metaDataMap;
    }

    /**
     * this method is intended to convert input streams of images to byte array
     * @param inputStream
     * @return
     */
    public byte[] convertStreamToByteArray(InputStream inputStream){

        byte[] byteBuffer = null;
        int bytesRead, bytesAvailable, bufferSize, maxBufferSize;

        try {
            bytesAvailable = inputStream.available();
            maxBufferSize = inputStream.available(); // max buffer size will always be the size of the amount available bytes
            bufferSize = Math.min(bytesAvailable, maxBufferSize); // random value
            byteBuffer = new byte[bufferSize];

            bytesRead = inputStream.read(byteBuffer, 0, bufferSize); //the total number of bytes read into the buffer

            while(bytesRead > 0){ // this loop might be overkill but it will stay for the time being and most likey for good unless it breaks something.
                bytesAvailable = inputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = inputStream.read(byteBuffer, 0, bufferSize);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteBuffer;
   } // end method convertStreamToByteArray

    /**
     * using Base64 format, encode this byte array to a string
     * this is primarily used to send images over http using REST
     * @param byteArray
     * @return
     */
    public String encodeByteArrayToString(byte[] byteArray){

        String encodedArray = null;
        try {
            encodedArray = Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return encodedArray;
    } // end method encodeByteArrayToString
}
