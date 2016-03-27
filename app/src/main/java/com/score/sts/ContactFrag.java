package com.score.sts;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.leakcanary.RefWatcher;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFrag extends Fragment {

    //---Butterknife
    @Bind(R.id.rvContact) RecyclerView rvContact;

    //---variables
    private static String TAG = ContactFrag.class.getSimpleName();
    private ContactRecyclerViewAdapter contactAdapter;  // TODO this will be used when the RecyclerView is moved here
    private RefWatcher watcher;

    public ContactFrag() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View contactView = inflater.inflate(R.layout.contacts_recycler, container, false); // TODO in the process of possibly removing this layout.
        View contactView = inflater.inflate(R.layout.contact_fragment, container, false);
//        ButterKnife.bind(this, contactView); TODO try binding to contactView if binding to the activity doesn't work. In all the other frags, it is bound to the activity
        return contactView;
    }

    private void init(){
//        ButterKnife.bind(this, getActivity());
//        watcher = SoundtracksAndScores.getRefWatcher(getActivity());
    }

}
