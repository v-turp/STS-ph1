package com.score.sts.util;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Who Dat on 1/29/2016.
 * This class is create to test REST web services.
 * This class can be deleted without consequence.
 */
public class TestAsyncTask extends AsyncTask<Void, Void, String>{

    @Override
    protected String doInBackground(Void... voids) {

        String results="";

        try {
            String queryUrl = "http://stsbeta1.com/SoundServices/score/ContractService/contacts/username/password";
            String theUrl = "http://stsbeta1.com/SoundServices/score/ContactService/contacts";

            URL url = new URL(theUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            // TODO handle the response code
            BufferedReader br = new BufferedReader( new InputStreamReader(inputStream));


            String holder = "";
            while((holder=br.readLine()) != null){
                results+=holder;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException io){
            io.printStackTrace();
        }

        return results;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
