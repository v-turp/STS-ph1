package data.management;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Who Dat on 1/9/2016.
 * this is the contract for the local connect database.
 * each class represents a separate table
 */

// TODO setup a uri_matcher for this class and look into setting up content types
public final class ConnectContract {

    private static String TAG = ConnectContract.class.getSimpleName();

    /**
    * The authority of the Connections provider.
    */
    public static final String AUTHORITY = "com.sts.provider.Connections";

    /**
     * The content URL for the top-level connections authority
     */
    public static final String URL = "content://" + AUTHORITY;

    /**
     * The content URI for the top-level connections authority
     */
    public static final Uri CONNECTIONS_CONTENT_URI = Uri.parse(URL);

    /**
     * Connect database name in the connections provider
     */
    public static final String CONNECT_DB = "connect";

    /**
     * Connect database version in the connections provider
     */
    public static int CONNECT_DB_VERSION = 1;

        /**
         * Constants for the contacts table of the connections provider
         */
        public static final class Contacts implements BaseColumns{
            /**
             * The table name
             */
            public static final String CONTACTS_TABLE = "contacts";

            /**
             * The content URI for contacts table
             */
            public static final Uri CONTACTS_URI = Uri.withAppendedPath(CONNECTIONS_CONTENT_URI, CONTACTS_TABLE);

            /**
             * The contacts table columns
             */
            public static String COL_FIRST_NAME = "first_name ";
            public static String COL_LAST_NAME = "last_name ";
            public static String COL_EMAIL = "email ";
//            public static String _ID = "_id "; // TODO removed from contract because not necessary for recyclerview

            /**
            * A projection of all columns in the contacts table
            */
            public static final String[] CONTACTS_PROJECTION_ALL = {COL_FIRST_NAME, COL_LAST_NAME, COL_EMAIL};

            /**
            * The default sort order for queries containing COL_LAST_NAME fields.
            */
            public static final String SORT_ORDER_DEFAULT = COL_LAST_NAME + " ASC";

            /**
            * The default connect table construct/creation
            */
            public static final String CREATE_CONTACTS_TABLE = "CREATE TABLE " + CONTACTS_TABLE + "(" +
                                                                COL_FIRST_NAME + "TEXT NOT NULL, " +
                                                                COL_LAST_NAME + "TEXT NOT NULL, " +
                                                                COL_EMAIL + "TEXT )";

            /**
             * drop the contacts table
             */
            public static final String DROP_CONTACTS_TABLE = "DROP TABLE IF EXISTS " + CONTACTS_TABLE;

        } // end class Contacts

} // end ConnectContract class
