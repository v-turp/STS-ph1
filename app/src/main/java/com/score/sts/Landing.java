package com.score.sts;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


public class Landing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        init();
    }

    public void init(){
        // TODO attach the fragments here
        FragmentManager supportFragmentMgr = getSupportFragmentManager();
        FragmentTransaction supportFragmentTransaction = supportFragmentMgr.beginTransaction();

        MusicFragment mMusicFragment = new MusicFragment();
        ProfileFragment mProfileFragment = new ProfileFragment();
        ContactFrag mContactFragment = new ContactFrag();
        VideosFragment mVideosFragment = new VideosFragment();
        RegisterWorkFragment mRegisterWorkFragment = new RegisterWorkFragment();
        LoginFragment mLoginFragment = new LoginFragment();

        //--- set fragments according to orientation
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            supportFragmentTransaction.add(R.id.flMusicFragmentContainer, mMusicFragment);
            supportFragmentTransaction.add(R.id.flProfileFragmentContainer, mProfileFragment);
            supportFragmentTransaction.add(R.id.flContactsFragmentContainer, mContactFragment);
            supportFragmentTransaction.add(R.id.flVideoFragmentContainer, mVideosFragment);
            supportFragmentTransaction.add(R.id.flRegisterWorksFragmentContainer, mRegisterWorkFragment);
            supportFragmentTransaction.add(R.id.flLoginFragmentContainer, mLoginFragment);
            supportFragmentTransaction.commit();
        }else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            supportFragmentTransaction.add(R.id.flProfileFragmentContainer, mProfileFragment);
            supportFragmentTransaction.add(R.id.flRegisterWorksFragmentContainer, mRegisterWorkFragment);
            supportFragmentTransaction.add(R.id.flLoginFragmentContainer, mLoginFragment);
            supportFragmentTransaction.commit();
        }
    }
}
