package data.management;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Who Dat on 1/7/2016.
 * when querying the content provider, use the URI for the specific
 * table because each CRUD method call is filtered through a UriMatcher
 * using a switch statement
 */
public class ConnectionsProvider extends ContentProvider {

    private ConnectDB mConnectDB;
    private static final String TAG = ConnectionsProvider.class.getSimpleName();

    public ConnectionsProvider() {
        super();
    }

    @Override
    public boolean onCreate() {

         /*
         * Creates a new helper object. This method always returns quickly.
         * Notice that the database itself isn't created or opened
         * until SQLiteOpenHelper.getWritableDatabase is called
         */

        mConnectDB = new ConnectDB(getContext());

        return true;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        SQLiteDatabase db = mConnectDB.getWritableDatabase();
        int match = mUriMatcher.match(uri);

        switch(match){
            case ALL_CONTACTS:
                int rowsUpdated = db.update(ConnectContract.Contacts.CONTACTS_TABLE, contentValues, selection, selectionArgs);
                /**
                 * return the number of rows updated
                 */
                if(rowsUpdated > 0){
                    getContext().getContentResolver().notifyChange(uri, null);
                    return rowsUpdated;
                }
            default:
                return 0;
        }
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mConnectDB.getWritableDatabase();
        int match = mUriMatcher.match(uri);

        switch(match){
            case ALL_CONTACTS:
                int rowsDeleted = db.delete(ConnectContract.Contacts.CONTACTS_TABLE, selection, selectionArgs);
                Log.d(TAG, "Last Path Segments: " + uri.getLastPathSegment());
                if(rowsDeleted > 0){
                    getContext().getContentResolver().notifyChange(uri, null);
                    return rowsDeleted;
                }
            default:
                return 0;
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {

        SQLiteDatabase db = mConnectDB.getWritableDatabase();
        int match = mUriMatcher.match(uri);
        long rowId;

        switch(match)
        {
            case ALL_CONTACTS:
                 // add a new contact
                 rowId = db.insert(ConnectContract.Contacts.CONTACTS_TABLE, null, contentValues);
                 // if a contact is added successfully, build the uri of the new row and notify content observers of the change
                 if(rowId > 0){
                      Uri _uri = ContentUris.withAppendedId(ConnectContract.Contacts.CONTACTS_URI, rowId);
                      getContext().getContentResolver().notifyChange(_uri, null);
                      Toast.makeText(getContext(), "New Uri: " + _uri, Toast.LENGTH_LONG).show(); // TODO testing row
                      return _uri;
                  } else{
                       Toast.makeText(getContext(), "No contact found", Toast.LENGTH_LONG).show();
                  }
            default:
                    return null;
        } // end switch
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projectionIn, String selection, String[] selectionArgs, String groupBy) {

        /*
        * sample query
        * String selection =  ConnectContract.Contacts.COL_FIRST_NAME +  "= ? AND " +
        *                     ConnectContract.Contacts.COL_LAST_NAME +  "= ? AND " +
        *                     ConnectContract.Contacts.COL_EMAIL + " = ? ";
        */
        SQLiteDatabase db = mConnectDB.getWritableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        int match = mUriMatcher.match(uri);

        switch(match)
        {
            case ALL_CONTACTS:
                    queryBuilder.setTables(ConnectContract.Contacts.CONTACTS_TABLE);
                break;

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        Cursor cursor = queryBuilder.query(db, projectionIn, selection, selectionArgs, groupBy, null, null);
        /**
         * register to watch a content URI for changes
         * make sure that potential listeners are getting notified
         */
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    /**
     * The list of possible URI patters for the provider
     */
    private static final int ALL_CONTACTS = 1;

    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        mUriMatcher.addURI(ConnectContract.AUTHORITY, ConnectContract.Contacts.CONTACTS_TABLE, ALL_CONTACTS);
    }
}
