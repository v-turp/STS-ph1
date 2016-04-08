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


    public CreateAccountFrag() {
        // Required empty public constructor
    }

/*

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_account, container, false);
    }
*/

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog signUpDialog = buildCreateAccountDialog(null);

        return signUpDialog;
    }

    private Dialog buildCreateAccountDialog(@Nullable Bundle savedInstanceState){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        alertDialog.setView(inflater.inflate(R.layout.fragment_create_account, null))
                   .setPositiveButton("Join", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {

                       }
                   })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        return alertDialog.create();
    }
}
