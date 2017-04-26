package se.mah.ag7406.cifr.client;

import android.app.Application;
import android.content.Context;

/**
 * Created by Jens on 2017-04-26.
 */

public class SuperClass extends Application {
    private static Application instance;

    public void onCreate(){
        super.onCreate();
        instance = this;
    }

    public static Context getContext(){
        return instance.getApplicationContext();
    }
}
