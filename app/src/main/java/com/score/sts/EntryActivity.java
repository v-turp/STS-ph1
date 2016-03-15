package com.score.sts;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.score.sts.util.BroadcastUtilScheduler;
import com.score.sts.util.C;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import data.management.ConnectContract;
import data.management.data.models.Contact;

public class EntryActivity extends Activity {

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

    //--- Home Navigation Drawer
    @Bind(R.id.rvNavDrawer)  RecyclerView rvLandingDrawer;
//    @Bind( R.id.lv_drawer_home) ListView lvDrawerHome;
    private ListView lvDrawerHome;


    private static final String TAG = EntryActivity.class.getSimpleName();
    private RefWatcher watcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        ButterKnife.bind(this);
        watcher = SoundtracksAndScores.getRefWatcher(getApplication());
//        init();
//        StrictMode.enableDefaults(); // TODO check for anything that might need to be put on a separate thread
        ArrayList<String> homeNavOptions = new ArrayList<>();
        homeNavOptions.add("Jobs");
        homeNavOptions.add("Messages");
        homeNavOptions.add("Home");
        homeNavOptions.add("Profile");
        homeNavOptions.add("Contacts");
        homeNavOptions.add("Register Music");

//        lvDrawerHome = (ListView) findViewById(R.id.left_drawer);
        //lvDrawerHome.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, homeNaveOptions));
        rvLandingDrawer.setAdapter(new ContactRecyclerViewAdapter(homeNavOptions, getApplication()));
        rvLandingDrawer.setLayoutManager( new LinearLayoutManager(this));

    }

    public void init(){

//        String[] homeNaveOptions = getResources().getStringArray(R.array.home_navigation_options);

        scheduleAlarm();
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
        Toast.makeText(getApplicationContext(), "Rows Deleted: " + rowsDeleted, Toast.LENGTH_LONG).show();
    } // end method deleteContact

    @OnClick(R.id.btnShowContacts)
    public void showContacts(){
//        Intent intent = new Intent(C.ACTION_CONTACT_RECYCLER_VIEW);
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
}
