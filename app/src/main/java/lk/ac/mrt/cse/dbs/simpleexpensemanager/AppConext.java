package lk.ac.mrt.cse.dbs.simpleexpensemanager;

import android.app.Application;
import android.content.Context;

/**
 * Created by SHAEDI on 06/12/2015.
 */
public class AppConext extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();

    }

    public static Context getContext(){
        return context;
    }
}
