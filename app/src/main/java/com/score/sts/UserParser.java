package com.score.sts;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

import data.management.data.models.User;

/**
 * Created by Who Dat on 2/3/2016.
 * This class is used to parse the any xml document
 * that refers to the user. This class may be added to
 * depending on the information that is added in the database
 * for the user
 * TODO: Building will be a gradual process to make sure it is most efficient
 */
public class UserParser {

    private static final String TAG = UserParser.class.getSimpleName();
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String USERNAME = "username";
    private static final String USER_PASSWORD = "password";
    private static final String CITY = "city";
    private static final String STATE = "state";
    private static final String COUNTRY = "country";
    private static final String EMAIL = "email";
    private static final String MUSIC_GENRE = "music_genre";
    private static final String FILM_GENRE = "film_genre";
    private static final String TITLE = "title";
    private static final String BIO = "bio";




    public User parseUserData(String userData){

        User user = new User();
        //========NOTICE===========TODO this setup works well and has undergone several rounds of testing. Do some cleanup and minor refactoring, create 2 inner classes:
        //=========================TODO User inner class and Contact inner class. Each class will have its own parsing process. In the future there will be a company class.
        //=========================TODO LASTLY: rename this class to accommodate for the new inner classes that will go in here.
        try {
            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            parserFactory.setNamespaceAware(true);
            XmlPullParser userParser = parserFactory.newPullParser();

            userParser.setInput(new StringReader(userData));
            int eventType = userParser.getEventType();

            while(eventType != userParser.END_DOCUMENT){

                    switch (eventType){
                       case XmlPullParser.START_TAG:
                                if(userParser.getName().equals(FIRST_NAME)){
                                    user.setFirstName(userParser.nextText());
                                }else if(userParser.getName().equals(LAST_NAME)){
                                    user.setLastName(userParser.nextText());
                                }else if(userParser.getName().equals(USER_PASSWORD)) {
                                    user.setPassword(userParser.nextText());
                                }else if(userParser.getName().equals(USERNAME)){
                                    user.setUsername(userParser.nextText());
                                }else if(userParser.getName().equals(CITY)){
                                    user.setCity(userParser.nextText());
                                }else if(userParser.getName().equals(STATE)){
                                    user.setState(userParser.nextText());
                                }else if(userParser.getName().equals(COUNTRY)){
                                    user.setCountry(userParser.nextText());
                                }else if(userParser.getName().equals(EMAIL)){
                                    user.setEmail(userParser.nextText());
                                }else if(userParser.getName().equals(MUSIC_GENRE)){
                                    user.setMusicGenre(userParser.nextText());
                                }else if(userParser.getName().equals(FILM_GENRE)){
                                    user.setFilmGenre(userParser.nextText());
                                }else if(userParser.getName().equals(BIO)){
                                    user.setBio(userParser.nextText());
                                }
                        break;
                   } // start tag switch

                eventType = userParser.next();
            } // end while
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }


}

   /**
   * String nextText()
   *If current event is START_TAG then if next element is TEXT then element content is returned
   * or if next event is END_TAG then empty string is returned, otherwise exception is thrown.
   * */
