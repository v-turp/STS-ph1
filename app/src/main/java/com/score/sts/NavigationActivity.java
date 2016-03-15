package com.score.sts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Who Dat on 2/9/2016.
 * This Activity is used to house the PageView capability
 * for tabs/fragments Profile, Network, Music and Videos
 */
public class NavigationActivity extends FragmentActivity{

    @Bind(R.id.navigation_pager) ViewPager vpNavigation;

    private static final String TAG = NavigationActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_activity);
        ButterKnife.bind(this);

        init();
    }

    public void init(){

        // initialize the fragments that will be used in the ViewPager
        ProfileFragment profileFragment = new ProfileFragment();
        NetworkFragment networkFragment = new NetworkFragment();
        MusicFragment musicFragment = new MusicFragment();
        VideosFragment videosFragment = new VideosFragment();

        // put all the fragments in a arrayList that will be used in the adapter
        ArrayList<Fragment> navigationFragmentsList = new ArrayList<>();
        navigationFragmentsList.add(profileFragment);
        navigationFragmentsList.add(networkFragment);
        navigationFragmentsList.add(musicFragment);
        navigationFragmentsList.add(videosFragment);

        // create and set the adapter
        NavigationPagerAdapter navigationPagerAdapter = new  NavigationPagerAdapter(getSupportFragmentManager(), navigationFragmentsList);
        vpNavigation.setAdapter(navigationPagerAdapter);

    }

    public class NavigationPagerAdapter extends FragmentStatePagerAdapter {

        ArrayList<Fragment> mFragmentList;

        public NavigationPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList){
            super(fm);
            this.mFragmentList = fragmentList;
        }
        @Override
        public Fragment getItem(int position) {

            Fragment returnFragment = null;
            switch (position){
                case 0:
                    returnFragment = mFragmentList.get(position);
                    break;
                case 1:
                    returnFragment = mFragmentList.get(position);
                    break;
                case 2:
                    returnFragment = mFragmentList.get(position);
                    break;
                case 3:
                    returnFragment = mFragmentList.get(position);
                    break;
                default:
//                    returnFragment = mFragmentList.get(0);
                    break;
            }

            return returnFragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // set the page title to be displayed in the child pagerTabStrip for each respective fragment
            CharSequence pageTitle = null;
            switch(position){
                case 0:
                    pageTitle = "PROFILE";
                    break;
                case 1:
                    pageTitle = "NETWORK";
                    break;
                case 2:
                    pageTitle = "MUSIC";
                    break;
                case 3:
                    pageTitle = "VIDEOS";
                    break;
                default:
                    break;
            }
            return pageTitle;
        }
    }
}
