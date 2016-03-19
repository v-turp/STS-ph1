package com.score.sts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Who Dat on 3/18/2016.
 */
public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        ButterKnife.bind(this);

        /**
         * this enables fullscreen for version 4.1+
         * set view hidden step 1
         * */
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // --- end fullscreen impl 4.1+

        //---set the delay for calling the next activity
        new View(this).postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), ManageContacts.class);
                startActivity(intent);
            }
        }, 5000);
    } // end method onCreate

} // end class Splash


