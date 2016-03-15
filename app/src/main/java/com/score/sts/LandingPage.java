package com.score.sts;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.score.sts.util.BaseNetworkRequestQ;
import com.score.sts.util.ImageManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import data.management.data.models.User;

/**
 * Created by Who Dat on 2/2/2016.
 * This is the initial start page of the app.
 * It is used to allow the user to login, or use
 * options on the drawer.
 */
public class LandingPage extends Activity {

    private static final String TAG = LandingPage.class.getSimpleName();
    private static boolean userExists = false;

    //--- Home Navigation Drawer
    @Bind(R.id.rvNavDrawer) RecyclerView rvLandingDrawer;
    @Bind(R.id.etUsername)  EditText etUsername;
    @Bind(R.id.etPassword)  EditText etPassword;
    @Bind(R.id.ivLandingLogo) ImageView ivLandingLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        // get the array from the resources file and set the adapter and layout
        ArrayList<String> homeNavOptions = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.home_navigation_options)));

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        rvLandingDrawer.setAdapter(new ContactRecyclerViewAdapter(homeNavOptions, getApplication()));
        rvLandingDrawer.setLayoutManager( new LinearLayoutManager(this));
        rvLandingDrawer.addItemDecoration(itemDecoration);
        // TODO these are my bitmap util test functions below and can be removed without consequence
        testImageClassFunctionality();
    }

    @OnClick(R.id.btnLogin)
    public void loginInUser(){
        String username =  etUsername.getText().toString();
        String password = etPassword.getText().toString();
        // TODO before this call, validate that the user has an internet connection, cross check this with preferences
//        validateUser(username, password);
        Intent intent = new Intent(getApplicationContext(),ManageContacts.class);
        startActivity(intent);

    }

    private void validateUser(String username, String password){

        String requestUrl = "http://stsbeta1.com/SoundServices/score/ContactService/login" + "/" + username + "/" + password;
        final BaseNetworkRequestQ networkRequest = BaseNetworkRequestQ.getInstance(this);
        RequestQueue requestQueue = networkRequest.getRequestQueue();

        Response.Listener responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Login Response successful" + "\n" + response);


                User parsedUser = new UserParser().parseUserData(response);
                Toast.makeText(LandingPage.this, "Successful REST response. User password/username: " + parsedUser.getPassword()+"/"+parsedUser.getUsername(), Toast.LENGTH_LONG).show();
                // TODO put in the logic to verify if the unam/pwd is correct. check the parsed results to see if null or some other check
                // TODO cont'd if the user credentials are valid launch the activity otherwise send an error message
                // TODO add a spinner here to display during login. After testing, I noticed it takes a long to get a response...can't leave the user hanging
//                Intent intent = new Intent(C.ACTION_NAVIGATION_ACTIVITY, null, getApplicationContext(), NavigationActivity.class); // this is the

                Intent intent = new Intent(getApplicationContext(),ManageContacts.class); // TODO temporary to put some data in the database


                startActivity(intent);
                clearFields();

                // my else goes here for the incorrect uname/pwd
//                Toast.makeText(LandingPage.this, "The username or password is incorrect", Toast.LENGTH_LONG).show();

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                final int statusCode = error.networkResponse.statusCode;
                Set<String> headersKeySet = error.networkResponse.headers.keySet(); // TODO write the keySetNames to the log

                //--- error logs ---//
                Log.d(TAG, "Login Error STATUS CODE: " + statusCode);
                Log.d(TAG, "Login Response Error: " + error.getMessage());
                Toast.makeText(LandingPage.this, "Unsuccessful REST response", Toast.LENGTH_LONG).show();
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestUrl, responseListener, errorListener);

        requestQueue.add(stringRequest);
    }

    private void clearFields(){
        etUsername.setText("");
        etPassword.setText("");
    }

    private final BroadcastReceiver loginReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            startActivity(intent);
        }
    };

    private void testImageClassFunctionality(){
        ImageManager imageManager = new ImageManager(this);
//        imageManager.getImageStats(getResources());
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.sts_logo_original, options);
        int sampleSize = imageManager.calculateSampleSize(options, 100, 100);
        options.inJustDecodeBounds = false;
        ivLandingLogo.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.sts_logo_original, options));

    }
}
