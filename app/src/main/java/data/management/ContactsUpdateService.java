package data.management;

import android.app.Activity;
import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.os.ResultReceiver;

import com.score.sts.util.C;

import java.util.ArrayList;

import data.management.data.models.Contact;

/**
 * Created by Who Dat on 1/20/2016.
 * NOTE: this class will be used to access the database on the server
 * and then populate the sqlite database. it will not update the ui
 * and should run a database check each time the app is launched. This
 * could be launched or made to run on startup from the Application class.
 * // TODO still a work in progress
 */

public class ContactsUpdateService extends IntentService {

    private static final String TAG = ContactsUpdateService.class.getSimpleName();
    public static final String INTENT_FILTER_ACTION = "com.score.sts.action.CONTACT_UPDATE_SERVICE";
    public static final int SHOW_CONTACTS_FROM_LOCAL_DB = 1; // instructions whether to update recycler view or sync db, this is default
    public static final int UPDATE_LOCAL_DB_FROM_SERVER = 2;
    public static final String INSTRUCTIONS = "instructions";
    public static final String RESULT_RECEIVER = "result receiver";
    public static final String RESULT_STATUS = "result_status";

    public ContactsUpdateService(){
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // get the instructions from the intent and complete the appropriate task
        int instruction = intent.getIntExtra(INSTRUCTIONS, SHOW_CONTACTS_FROM_LOCAL_DB);

        switch(instruction)
        {
            case SHOW_CONTACTS_FROM_LOCAL_DB:
                sendLocalContacts();    // using BroadcastReceiver
                break;
            case UPDATE_LOCAL_DB_FROM_SERVER:
                break;
            default:
                break;
        } // end switch

    }

    // TODO NOTE: this is only here for example purposes only. It functions great but is not being used. It was causing a memory leak in ContactRecyclerView  - switched to BroadcastReceiver
    // for ResultReceiver
    private void sendLocalContacts(ResultReceiver resultReceiver){
        // query db for all contacts and send back the results
        Bundle resultBundle = new Bundle();
        ContentResolver resolver = getContentResolver();
        ArrayList<Contact> contactList = new ArrayList<>();
        Contact contact = new Contact();
        Cursor cursor;
        try {
             cursor = resolver.query(ConnectContract.Contacts.CONTACTS_URI, null, null, null, null); // get all rows
             if(cursor == null){
                 resultReceiver.send(Activity.RESULT_OK, null); // no bundle means no results
                 return;
             }
             cursor.moveToFirst();
             do {
                 contact.setFirstName(cursor.getString(0));
                 contact.setLastName(cursor.getString(1));
                 contact.setEmail(cursor.getString(2));
                 contactList.add(contact);
                 contact = new Contact();
             }while(cursor.moveToNext());

             cursor.close();
             // load the bundle and send it
             resultBundle.putParcelableArrayList("contact list", contactList);
             resultReceiver.send(Activity.RESULT_OK, resultBundle);

        } catch (SQLException sql) {
            sql.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // for BroadcastReceiver
    private void sendLocalContacts(){
        /*only send a bundle if there are results from the database otherwise send an empty Intent*/

        // for returning results
        Bundle resultBundle = new Bundle(); // Bundle to return with the Intent
        Intent resultIntent = new Intent(INTENT_FILTER_ACTION); // Intent to send in the BroadcastReceiver

        // query the content provider
        ContentResolver resolver = getContentResolver();
        ArrayList<Contact> contactList = new ArrayList<>();
        Contact contact = new Contact();
        Cursor cursor = null;
        try {
            cursor = resolver.query(ConnectContract.Contacts.CONTACTS_URI, null, null, null, null); // get all rows
            if(cursor == null){
                resultIntent.putExtra(RESULT_STATUS, Activity.RESULT_OK); // TODO consider changing status to cancelled if there are no results
                LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);
                return;
            }
            cursor.moveToFirst();
            do {
                contact.setFirstName(cursor.getString(0));
                contact.setLastName(cursor.getString(1));
                contact.setEmail(cursor.getString(2));
                contactList.add(contact);
                contact = new Contact();
            }while(cursor.moveToNext());
            cursor.close();

            // load the bundle inside the Intent and send the broadcast
            resultBundle.putParcelableArrayList("contact list", contactList);
            resultIntent.putExtra(C.EXTRA_PURPOSE, C.EXTRA_LOAD_RECYCLERVIEW_FROM_LOCAL_DB);
            resultIntent.putExtra("result_bundle", resultBundle);
            resultIntent.putExtra(RESULT_STATUS, Activity.RESULT_OK);
            LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);

        } catch (SQLException sql) {
            sql.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(cursor != null){
                cursor.close();
            }
        }

    }
}
