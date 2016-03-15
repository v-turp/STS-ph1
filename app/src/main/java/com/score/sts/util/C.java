package com.score.sts.util;

/**
 * Created by Who Dat on 1/26/2016.
 * this class contains any necessary constants other
 * than those in the ContentProvider contract class
 */
public final class C {

    /**
    * ACTIONS
    */

    public static final String ACTION_BROADCAST_UTIL_SCHEDULER = "com.score.sts.util.action.BROADCAST_UTIL_SCHEDULER";
    public static final String ACTION_CONTACT_UPDATE_SERVICE = "com.score.sts.action.CONTACT_UPDATE_SERVICE";
    public static final String ACTION_UTIL_SERVICE = "com.score.sts.util.action.UTIL_SERVICE";
    public static final String ACTION_NAVIGATION_ACTIVITY = "android.intent.action.NAVIGATION_ACTIVITY";

    /**
     * ACTIVITIES
     */


    /**
     *EXTRAS
     */
    public static final String EXTRA_PURPOSE = "Purpose";
    public static final String EXTRA_LOAD_RECYCLERVIEW_FROM_LOCAL_DB = "Load RecyclerView from local DB";

    /**
     * HTTP Configuration elements
     */
    public static final String CONNECTION = "Connection";
    public static final String CONTENT_LANGUAGE = "Content-Language";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String DATE = "Date";
    public static final String KEEP_ALIVE = "Keep-Alive";
    public static final String TEXT_HTML = "text/html";
    public static final String CHARSET_UTF8 = "charset=utf-8";
}
