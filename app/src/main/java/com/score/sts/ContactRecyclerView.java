/*
package com.score.sts;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.score.sts.util.C;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import data.management.ContactsUpdateService;
import data.management.data.models.Contact;

*/
/**
 * Created by Who Dat on 1/19/2016.
 * this activity is used only to display the contacts in a RecyclerView.
 * NOTE: this may be moved to a Fragment later and instantiated in an activity
 *//*


// TODO NOTE: this class should never sync the database. that is not its purpose. the database will be synced according to the alarm manager.
public class ContactRecyclerView extends Activity {

    private static final String TAG = ContactRecyclerView.class.getSimpleName();
    private BaseResultReceiver resultReceiver;
    private ContactRecyclerViewAdapter contactRecyclerViewAdapter;
    private Bundle savedBundle; // for saving state

    @Bind(R.id.rvContact)  RecyclerView rvContacts;

    private RefWatcher watcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_recycler);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        launchUpdateServiceWithBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(C.ACTION_CONTACT_UPDATE_SERVICE);
        LocalBroadcastManager.getInstance(this).registerReceiver(resultBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(resultBroadcastReceiver);
    }

    private void init(){
        ButterKnife.bind(this);
        watcher = SoundtracksAndScores.getRefWatcher(getApplication());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        */
/*
        * TODO: use restore/save state callbacks so the service is not called on each rotation. although the service
        * is being called on every rotation, this will never cause the app to crash but is slightly slower in performance
        * than well configured state management(saveInstance/restoreInstance). this is not a primary concern at the moment
        * but will need to implemented especially if other data will be added later. .
        *//*

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // TODO complete the logic of this method. the logic in this method should closely mirror setupRecyclerView method. check for memory leaks
        // TODO NOTE: see comments in onSaveInstanceState
    }

    // TODO NOTE: this is only here for example purposes only. It functions great but is not being used. It was causing a memory leak in ContactRecyclerView  - switched to BroadcastReceiver
    private void setupResultReceiver(){
        resultReceiver = new BaseResultReceiver(new Handler());
        resultReceiver.setReceiver(new BaseResultReceiver.Receiver(){
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                if(resultCode == Activity.RESULT_OK){
                    savedBundle = resultData; // for savedInstanceState
                    setupRecyclerView(resultData);
                    Toast.makeText(getApplicationContext(), "ResultReceiver reporting", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplication(), "The result code is not OK", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // TODO NOTE: this is only here for example purposes only. It functions great but is not being used. It was causing a memory leak in ContactRecyclerView  - switched to BroadcastReceiver
    private void launchUpdateService(){
        // send the service instructions, ResultReceiver and launch the service
        Intent intent = new Intent(this, ContactsUpdateService.class);
        intent.putExtra(ContactsUpdateService.INSTRUCTIONS, ContactsUpdateService.SHOW_CONTACTS_FROM_LOCAL_DB);
        intent.putExtra(ContactsUpdateService.RESULT_RECEIVER, resultReceiver);
        startService(intent);
    }

    private void launchUpdateServiceWithBroadcastReceiver(){
        // send the service instructions and launch the service
        Intent intent = new Intent(ContactsUpdateService.INTENT_FILTER_ACTION);
        intent.putExtra(ContactsUpdateService.INSTRUCTIONS, ContactsUpdateService.SHOW_CONTACTS_FROM_LOCAL_DB);
        startService(intent);
    }

    private void setupRecyclerView(Bundle resultData){
        // get the results from the result receiver(in the Bundle), load and set the adapter and layout manager
        List<Contact> contactList;
        try {
            if(resultData != null){
                contactList = resultData.getParcelableArrayList("contact list");
                this.contactRecyclerViewAdapter = new ContactRecyclerViewAdapter(contactList, getApplication());
                rvContacts.setAdapter(this.contactRecyclerViewAdapter);
                rvContacts.setLayoutManager(new LinearLayoutManager(this));
            }else{// TODO fix this else statement or remove it. it should display if there are no contacts in the database
                Toast.makeText(getApplicationContext(), "You currently do not have any contacts", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // end method setupRecyclerView

    private final BroadcastReceiver resultBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // if the purpose is to load the RecyclerView with data from the local db, execute tasks
            if (intent.getStringExtra(C.EXTRA_PURPOSE) == C.EXTRA_LOAD_RECYCLERVIEW_FROM_LOCAL_DB) {
                // get the bundle from the intent and use it to setup the recycler view only if the result is ok from the intent, -5 is an arbitrary value
                if (intent.getIntExtra(ContactsUpdateService.RESULT_STATUS, -5) == Activity.RESULT_OK) {
                    Bundle resultData = intent.getBundleExtra("result_bundle");
                    setupRecyclerView(resultData);
                    Toast.makeText(getApplicationContext(), "BroadcastReceiver reporting", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "BroadcastReceiver reporting: No data to display", Toast.LENGTH_LONG).show();
                }
            }
        }
    }; // end BroadcastReceiver
}
*/
