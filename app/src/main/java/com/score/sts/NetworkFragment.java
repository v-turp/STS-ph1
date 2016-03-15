package com.score.sts;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.score.sts.util.C;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;

import butterknife.ButterKnife;
import data.management.ContactsUpdateService;
import data.management.data.models.Contact;

/**
 * Created by Who Dat on 2/9/2016.
 */
public class NetworkFragment extends Fragment{

    private static final String TAG = NetworkFragment.class.getSimpleName();
    private ContactRecyclerViewAdapter contactRecyclerViewAdapter;

//    @Bind(R.id.rvNetworkFrag)
//    RecyclerView rvNetworkFrag;


    private RecyclerView rvNetworkFrag;

    private RefWatcher watcher;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvNetworkFrag = (RecyclerView) getActivity().findViewById(R.id.rvNetworkFrag);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View networkView = inflater.inflate(R.layout.network_fragment, container, false);
        ButterKnife.bind(this, networkView);
        return networkView;
    }

    @Override
    public void onResume() {
        super.onResume();

        // register the receiver
        launchUpdateServiceWithBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(C.ACTION_CONTACT_UPDATE_SERVICE);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(resultBroadcastReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();

        // unregister the receiver
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(resultBroadcastReceiver);
    }

    private void launchUpdateServiceWithBroadcastReceiver(){
        // send the service instructions and launch the service
        Intent intent = new Intent(C.ACTION_CONTACT_UPDATE_SERVICE/*, null, getActivity(), ContactsUpdateService.class*/);
        intent.putExtra(ContactsUpdateService.INSTRUCTIONS, ContactsUpdateService.SHOW_CONTACTS_FROM_LOCAL_DB);
        getActivity().startService(intent);
    }

    private void setupRecyclerView(Bundle resultData){
        // get the results from the result receiver(in the Bundle), load and set the adapter and layout manager
        List<Contact> contactList;
        try {
            if(resultData != null){
                contactList = resultData.getParcelableArrayList("contact list");
                this.contactRecyclerViewAdapter = new ContactRecyclerViewAdapter(contactList, getActivity());
                rvNetworkFrag.setAdapter(contactRecyclerViewAdapter);
                rvNetworkFrag.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            }else{// TODO fix this else statement or remove it. it should display if there are no contacts in the database
                Toast.makeText(getActivity(), "You currently do not have any contacts", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getActivity(), "BroadcastReceiver reporting", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "BroadcastReceiver reporting: No data to display", Toast.LENGTH_LONG).show();
                }
            }
        }
    }; // end BroadcastReceiver
}
