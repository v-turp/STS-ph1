package data.management;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

/**
 * Created by Who Dat on 1/8/2016.
 * TODO determine what this class should be used for. currently, this class is not used for anything
 */
public class ContactsLoader extends Activity implements LoaderManager.LoaderCallbacks<String> {

    private static final String TAG = ContactsLoader.class.getSimpleName();
    private static final int LOADER_ID = 777;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, ContactsLoader.this).forceLoad();
    }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        return new LoaderClass(ContactsLoader.this);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    public static class LoaderClass extends AsyncTaskLoader<String> {


        public LoaderClass(Context context){
            super(context);
        }
        @Override
        public String loadInBackground() {
            return null;
        }
    }
}
