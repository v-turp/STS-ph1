package com.score.sts;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import data.management.data.models.Contact;

/**
 * Created by Who Dat on 1/15/2016.
 * This adapter is created to map contacts in the local
 * database to the recycler view.
 *
 * NOTE: As of 01/28/2016 this adapter is being retrofitted to be the base adapter.
 * New constructors are being implemented. Additional changes may be made to make
 * this class more scalable and universal.
 */
public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ContactViewHolder>{

    private static final String TAG = ContactRecyclerViewAdapter.class.getSimpleName();
    private List<Contact> contactList;
    private ArrayList<String> genericList; // used by general users of this adapter that have lists
    private final Context context;
    private RefWatcher watcher;

    public ContactRecyclerViewAdapter(List<Contact> theContactList, Context theContext){
        this.contactList = theContactList;
        this.context = theContext;
        watcher = SoundtracksAndScores.getRefWatcher(theContext);
    }

    public ContactRecyclerViewAdapter(ArrayList<String> newList, Context theContext){

            this.genericList = newList;
            this.context = theContext;
    }
    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // since each view has and knows its context and has a unique view id, we use one context object throughout the switch statement
        // and perform the tasks according to recyclerview id(from the respective layout)
        Context parentContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parentContext);
        View itemView;

        final int parentViewId = parent.getId();

        switch (parentViewId)
        {
            case R.id.rvNavDrawer:
                Log.d(TAG, "Correct View Id? " +(parent.getId() == (R.id.rvNavDrawer)));
                itemView = inflater.inflate(R.layout.item_landing_page_drawer, parent, false);
                return new ContactViewHolder(itemView, parentViewId);

            case R.id.rvNetworkFrag:
                Log.d(TAG, "Correct View Id? " +(parent.getId() == (R.id.rvNetworkFrag)));
                itemView = inflater.inflate(R.layout.item_network_card_layout, parent, false);
                return new ContactViewHolder(itemView, parentViewId);

            default:    // TODO cannot return a ContactViewHolder with null argument because this may cause NullPointerException. Expect problems here
                return null;
        }

    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder ITEM_VIEW_PARENT_ID: " + holder.ITEM_VIEW_PARENT_ID );

        int itemViewParentId = holder.ITEM_VIEW_PARENT_ID;

        switch( itemViewParentId )
        {
            case R.id.rvNavDrawer:
                holder.tvNavOption.setText(genericList.get(position));
                // set the image based on the position in the navigation drawer
                setNavigationDrawerIcon(position, holder);

            case R.id.rvNetworkFrag:
                try { // TODO this may produce a NullPointerException so catch the exception so the app doesn't crash
                    holder.ivContactPhoto.setImageResource(R.drawable.default_avatar);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }

        // debugging
        if( contactList != null){
            Log.d(TAG, "The name from the Contact object passed in: " + contactList.get(position).getFirstName() + " " + contactList.get(position).getLastName());
        }
    }

    @Override
    public int getItemCount() {
        // if this getItemCount method returns 0, no rows will be populated - even if the data is not null
        if( contactList != null) {
            return contactList.size();
        } else if (genericList != null){
            return genericList.size();
        } else
            return 0;
    }

    // set the images based on the position in the navigation drawer
    private void setNavigationDrawerIcon(int position, ContactViewHolder holder){
        switch(position){
            case 0:
                holder.ivNavIcon.setImageResource(R.drawable.ic_fingerprint_black_24dp);
                break;
            case 1:
                holder.ivNavIcon.setImageResource(R.drawable.ic_account_circle_black_24dp);
                break;
            case 2:
                holder.ivNavIcon.setImageResource(R.drawable.ic_share_black_24dp);
                break;
            case 3:
                holder.ivNavIcon.setImageResource(R.drawable.ic_local_movies_black_24dp);
                break;
            case 4:
                holder.ivNavIcon.setImageResource(R.drawable.ic_audiotrack_black_24dp);
                break;
            case 5:
                holder.ivNavIcon.setImageResource(R.drawable.ic_star_border_black_24dp);
                break;
            default:
                break;
        }
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder{

        // This is the id of the parent(recyclerView) view of the itemView(inflated layout for the row/item)
        final int ITEM_VIEW_PARENT_ID;

        // these are the views in the navigation drawer on the landing page
        @Nullable @Bind(R.id.tvNavOption) TextView tvNavOption;
        @Nullable @Bind(R.id.ivNavIcon) ImageView ivNavIcon;

        //---for the network contacts network_fragment/item_network_card_layout respectively
        @Nullable @Bind(R.id.ivContactPhoto) ImageView ivContactPhoto;
        @Nullable @Bind(R.id.ivMusic) ImageView ivMusic;
        @Nullable @Bind(R.id.ivVideo) ImageView ivVideo;
        @Nullable @Bind(R.id.ivContact) ImageView ivContact;
        @Nullable @Bind(R.id.ivBio) ImageView ivBio;

        public ContactViewHolder(View itemView, int parentViewId){
            super(itemView);
            this.ITEM_VIEW_PARENT_ID = parentViewId;

            ButterKnife.bind(this, itemView);
        }


    }
}
