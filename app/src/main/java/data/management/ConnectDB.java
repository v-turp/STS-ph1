package data.management;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Who Dat on 1/8/2016.
 * this database is used to store for sts user contacts. it may be expanded to include
 * other tables, data/columns as well including but not limited to login information(this may go in shared preferences etc..
 * see ConnectContract for basic db constructs for the database creation. field names
 * and variable names may change in the future.
 */
public class ConnectDB extends SQLiteOpenHelper {

    private static String TAG = ConnectDB.class.getSimpleName();

    public ConnectDB(Context context){
        super(context, ConnectContract.CONNECT_DB, null, ConnectContract.CONNECT_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ConnectContract.Contacts.CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // TODO the next statement is not mandatory. upgrade logic will change in the future and this statement will likely be removed.
        sqLiteDatabase.execSQL(ConnectContract.Contacts.DROP_CONTACTS_TABLE);
        onCreate(sqLiteDatabase);
    }
}
