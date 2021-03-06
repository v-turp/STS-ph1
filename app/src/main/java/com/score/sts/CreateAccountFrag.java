package com.score.sts;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateAccountFrag extends DialogFragment {

    private static final String TAG = CreateAccountFrag.class.getSimpleName();
    private static final String JOIN = "JOIN";
    private static final String CANCEL = "CANCEL";


    public CreateAccountFrag() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return  buildCreateAccountDialog(null);
    }

    private Dialog buildCreateAccountDialog(@Nullable Bundle savedInstanceState){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        alertDialog.setView(inflater.inflate(R.layout.fragment_create_account, null))
                   .setPositiveButton( JOIN, new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {

                       }
                   })
                    .setNegativeButton(CANCEL, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        return alertDialog.create();
    }
}
