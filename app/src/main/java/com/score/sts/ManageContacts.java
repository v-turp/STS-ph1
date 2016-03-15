package com.score.sts;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.score.sts.util.BaseNetworkRequestQ;
import com.score.sts.util.BroadcastUtilScheduler;
import com.score.sts.util.C;
import com.score.sts.util.MediaFileManager;
import com.squareup.leakcanary.RefWatcher;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import data.management.ConnectContract;
import data.management.data.models.Contact;

public class ManageContacts extends Activity {

    private static final String IMAGE_URL = "http://www.stsbeta1.com/home/images/artist4.jpg";

    //--- EditText
    @Bind(R.id.etFirstName) EditText etFirstName;
    @Bind(R.id.etLastName) EditText etLastName;
    @Bind(R.id.etEmail) EditText etEmail;
    @Bind(R.id.etFirstNameSearch) EditText etFirstNameSearch;
    @Bind(R.id.etLastNameSearch) EditText etLastNameSearch;
    @Bind(R.id.etEmailSearch) EditText etEmailSearch;
    @Bind(R.id.etFirstNameUpdate) EditText etFirstNameUpdate;
    @Bind(R.id.etLastNameUpdate) EditText etLastNameUpdate;
    @Bind(R.id.etEmailUpdate) EditText etEmailUpdate;
    @Bind(R.id.etFirstNameDelete) EditText etFirstNameDelete;
    @Bind(R.id.etLastNameDelete) EditText etLastNameDelete;
    @Bind(R.id.etEmailDelete) EditText etEmailDelete;
    @Bind(R.id.ivNetworkImage) NetworkImageView ivNetworkImage;

    private static final String TAG = ManageContacts.class.getSimpleName();
    private RefWatcher watcher;
    private BaseNetworkRequestQ requestQ;
    private ImageLoader imageLoader;
    private MediaFileManager mediaFileManager;
    private static final int UPLOAD_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_contacts);
        ButterKnife.bind(this);
        watcher = SoundtracksAndScores.getRefWatcher(getApplication());
        init();
//        StrictMode.enableDefaults(); // TODO check for anything that might need to be put on a separate thread
    }

    public void init(){

          mediaFileManager = new MediaFileManager(ManageContacts.this);
        requestQ = BaseNetworkRequestQ.getInstance(this);

//        String[] homeNaveOptions = getResources().getStringArray(R.array.home_navigation_options);

//        scheduleAlarm();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.btnSaveContact)
    public void saveContact(){
        ContentValues contentValues = new ContentValues();
        Editable editable;

        try {
            editable = etFirstName.getText();
            String firstName = editable.toString();
            contentValues.put(ConnectContract.Contacts.COL_FIRST_NAME, firstName);

            editable = etLastName.getText();
            String lastName = editable.toString();
            contentValues.put(ConnectContract.Contacts.COL_LAST_NAME, lastName);

            editable = etEmail.getText();
            String email = editable.toString();
            contentValues.put(ConnectContract.Contacts.COL_EMAIL, email);


            Uri _uri = getContentResolver().insert(ConnectContract.Contacts.CONTACTS_URI, contentValues);
            if(_uri != null){
                Log.d(TAG, "The URI of the inserted row: " + _uri.toString());
            }
            editable.clear();
//            watcher.watch(editable);
//            watcher.watch(contentValues);
//            watcher.watch(_uri);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "There was a problem saving the contact", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    } // end method saveContact

    @OnClick(R.id.btnSearchContacts)
    public void searchContacts(){
        Editable editable;
        String[] selectionArguments = new String[3];
        List<Contact> contactList = new ArrayList<>();
        try {
            editable = etFirstNameSearch.getText();
            String firstNameSearch = editable.toString();
            selectionArguments[0] = firstNameSearch;

            editable = etLastNameSearch.getText();
            String lastNameSearch = editable.toString();
            selectionArguments[1] = lastNameSearch;

            editable = etEmailSearch.getText();
            String emailSearch = editable.toString();
            selectionArguments[2] = emailSearch;

            String selection =  ConnectContract.Contacts.COL_FIRST_NAME +  "= ? OR " +
                    ConnectContract.Contacts.COL_LAST_NAME +  "= ? OR " +
                    ConnectContract.Contacts.COL_EMAIL + " = ? ";
            Cursor cursor = getContentResolver().query(ConnectContract.Contacts.CONTACTS_URI, ConnectContract.Contacts.CONTACTS_PROJECTION_ALL, selection, selectionArguments, null);
            Contact contact;
            if(cursor != null && cursor.moveToFirst()){ // This method will return false if the cursor is empty - moveToFirst
                do{ // get all contacts that are in the cursor, close the cursor, put them in an array, then display all contacts in a toast message.
                    contact = new Contact();
                    contact.setFirstName(cursor.getString(0));
                    contact.setLastName(cursor.getString(1));
                    contact.setEmail(cursor.getString(2));
                    contactList.add(contact);
                }while(cursor.moveToNext());
                cursor.close();
                String personAdded = "db Search Results\n ";
                for(int i=0; i < contactList.size(); i++){
                    personAdded+=contactList.get(i).getFirstName() + " " + contactList.get(i).getLastName() + " " + contactList.get(i).getEmail() + "\n";
                }
                Toast.makeText(getApplicationContext(), personAdded, Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(), "No contacts found", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // end method searchContacts

    @OnClick(R.id.btnUpdateContact)
    public void updateContact(){
        checkWebService();     // TODO this is only for testing and can be removed without consequence...after testing the image loading capabilities
        ContentValues contentValues = new ContentValues();
        int rowsUpdated;

        // get what needs to be updated
        String firstName = etFirstNameUpdate.getText().toString();
        String lastName = etLastNameUpdate.getText().toString();
        String email = etEmailUpdate.getText().toString();

//        watcher.watch(firstName);

        String[] selectionArgs = new String[]{"john"};


        contentValues.put(ConnectContract.Contacts.COL_FIRST_NAME, firstName);
        contentValues.put(ConnectContract.Contacts.COL_LAST_NAME, lastName);
        contentValues.put(ConnectContract.Contacts.COL_EMAIL, email);

        String updateString = ConnectContract.Contacts.COL_FIRST_NAME + "= ?";

        rowsUpdated = getContentResolver().update(ConnectContract.Contacts.CONTACTS_URI, contentValues, updateString, selectionArgs);
        Toast.makeText(getApplicationContext(), "Total rows updated: " + rowsUpdated, Toast.LENGTH_LONG).show();
    } // end method updateContact

    @OnClick(R.id.btnDeleteContact)
    public void deleteContact(){
         /*
                * NOTE TO SELF: TODO instead of modifying the delete mechanism to search the row first(which would be done only to retrieve the row id and have it included in the
                * [TODO cont'd:] delete statement), do this. Since the contacts will be stored in a list anyway, display the row id in the list as part of the contact info. This way
                * [TODO cont'd:] you can always retrieve the row id from the list without having to make any crazy or double hits on the database. Another option is to have a custom dialogue
                * [TODO cont'd:] displaying all entries that match the search along with the row id's and give the user an option of which to delete(this is the lazy way out - i think)
                * */
        String firstName = etFirstNameDelete.getText().toString();
        String lastName = etLastNameDelete.getText().toString();
        String email = etEmailDelete.getText().toString();

        String[] selectionArgs = new String[3];

        selectionArgs[0] = firstName;
        selectionArgs[1] = lastName;
        selectionArgs[2] = email;

                /*String delete = ConnectContract.Contacts.COL_FIRST_NAME + "= ? OR "+
                                ConnectContract.Contacts.COL_LAST_NAME + "= ? OR " +
                                ConnectContract.Contacts.COL_EMAIL + "= ?";*/

        String delete = ConnectContract.Contacts.COL_FIRST_NAME + "= ? OR "+
                ConnectContract.Contacts.COL_LAST_NAME + "= ? OR " +
                ConnectContract.Contacts.COL_EMAIL + "= ?";

        int rowsDeleted = getContentResolver().delete(ConnectContract.Contacts.CONTACTS_URI, delete, selectionArgs);
//        Toast.makeText(getApplicationContext(), "Rows Deleted: " + rowsDeleted, Toast.LENGTH_LONG).show();
        checkWebService();
    } // end method deleteContact

    @OnClick(R.id.btnShowContacts)
    public void showContacts(){
        Intent intent = new Intent(C.ACTION_NAVIGATION_ACTIVITY);
        if(intent.resolveActivity(getPackageManager()) != null) { // this call verifies that there is an Activity able to respond to this action in the intent
            startActivity(intent);
        }
    }

    public void scheduleAlarm(){
        Intent intent = new Intent(C.ACTION_BROADCAST_UTIL_SCHEDULER);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, BroadcastUtilScheduler.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis();
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.btnUpload)
    public void uploadContent(View view){
        /**
        * @ OPTION 1. this option only opens the gallery application of photos and videos.
        * setting the type to audio/* will result in an the "No Activity found" toast.
        * apparently this only opens native applications. i say this b/c the limited
        * amount of apps(1) that can perform the action. if no type is specified, the
        * google toast will appear "you must have at least 1 google account setup before you
        * can use this application" - this is not what we want. at least 1 type should be set.
        * setting type to any mp3/4 extension will result in the Activity not being found...strange
        */
//        Intent pickIntent = new Intent(Intent.ACTION_PICK);
//        pickIntent.setType("image/jpg");
//        pickIntent.setType("image/bmp");
//        pickIntent.setType("image/png");
//        pickIntent.setType("video/*");
//        if(pickIntent.resolveActivity(getPackageManager()) != null){
//            startActivityForResult(pickIntent, 0);
//        } else {
//            Toast.makeText(this, "No Activity found to handle this Intent", Toast.LENGTH_LONG).show();
//        }

        /**
        * @ OPTION 2. setting type to asterisk/asterisk will provide several apps to get the content
        * this is a more flexible option and is showing respect for the audio/mpeg type. what I have
        * decided to do is create a FileManager class and in this class, check the returned file's type
        * and if the type is not on of our accepted types, send a message to the user.
        */
        Intent uploadIntent = new Intent(Intent.ACTION_GET_CONTENT);
        uploadIntent.setType("*/*");
        if(uploadIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(uploadIntent, UPLOAD_REQUEST_CODE);
        }else{
            Toast.makeText(this, "No Activity found to handle this Intent", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( resultCode != RESULT_OK) {
           return;
        } else if(resultCode == RESULT_OK && requestCode == UPLOAD_REQUEST_CODE) {

            try {
                //--- this gets the input stream, converts it to a byte array and then encodes the array to a Base64 string.
                InputStream inputStream = mediaFileManager.getInputStreamFromUri(data, null);
                final byte[] imageByteArray = mediaFileManager.convertStreamToByteArray(inputStream);
                final String strEncodedFile = Base64.encodeToString(imageByteArray, Base64.DEFAULT);

                inputStream.close();

            // TODO call the web service to create the file on the server here
            String url = "http://stsbeta1.com/SoundServices/score/ContactService/userMediaFile" ; //--- the base64 encoded String appended to the web service url
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Response from creating file on the server... " + response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.d(TAG, "Login Error STATUS CODE: " + error.networkResponse.statusCode);
                    Log.d(TAG, "Login Response Error: " + error.getMessage());
                    Log.d(TAG, "The VolleyError report: " + error.toString());
                }
            }){
                /**
                 * Overridden methods of the StringRequest method.
                 * @param -- retryPolicy
                 * @return
                 */

                    /*@Override
                public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
                    retryPolicy = new DefaultRetryPolicy(5000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);//  initialTimeoutMs, maxNumRetries,  backoffMultiplier
                    return super.setRetryPolicy(retryPolicy);
                }

                @Override
                public String getUrl() {
                    return super.getUrl(); // BREAKPOINT 4th HIT
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put(C.CONNECTION, C.KEEP_ALIVE);
                    headers.put(C.CONTENT_TYPE, C.TEXT_HTML);
                    headers.put(C.CONTENT_TYPE, C.CHARSET_UTF8);
                    return super.getHeaders();
                }*/

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("encodedImage", strEncodedFile );
                    params.put("newFile", "newFileName");
                    return params; // BREAKPOINT 1st HIT, 3rd HIT
                }
                /*
                @Override
                protected String getParamsEncoding() {
                    return super.getParamsEncoding();
                }

                @Override
                public String getBodyContentType() {
                    return super.getBodyContentType();
                }*/



                @Override
                public byte[] getBody() throws AuthFailureError {
                    // TODO see if this body value has the \n new line character break in the string. I think this was causing the error
                    // the byte array is put here instead of the request url as it is supposed to be via jersey
                    return imageByteArray;
                }
                /*
                @Override
                public Priority getPriority() {
                    return super.getPriority();
                }

                @Override
                public RetryPolicy getRetryPolicy() {
                    return super.getRetryPolicy();
                }*/

            };

            requestQ.addToRequesQueue(request);
//            Log.d(TAG, "The file name: " + fileName);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void checkWebService(){

        // TODO this is a work-in-progress to get an image from the server...still learning how the classes work together
        imageLoader = requestQ.getImageLoader();
        ImageLoader.ImageListener imageListener = new ImageLoader.ImageListener(){
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Log.d(TAG, "The ImageListener returned an image");
                Bitmap bitmap = response.getBitmap();
                ivNetworkImage.setImageBitmap(bitmap);

                //---response logging---//
                Log.d(TAG, "Image Request Url: " +  response.getRequestUrl());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "The ImageListener returned an error: " + error.getMessage());
            }
        };

        imageLoader.get(IMAGE_URL, imageListener);
    }
}
