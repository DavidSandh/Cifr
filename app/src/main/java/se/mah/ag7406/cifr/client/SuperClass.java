package se.mah.ag7406.cifr.client;

import android.app.Application;
import android.content.Context;

/**
 * Created by Jens on 2017-04-26.
 */

public class SuperClass extends Application {
    private static Application instance;
    private static Client client;
    private static Controller controller;
    public void onCreate(){
        super.onCreate();
        instance = this;
        controller = new Controller();
        //client = new Client("192.168.43.71",1337, controller);
    }

    public static Context getContext(){
        return instance.getApplicationContext();
    }
    public static Controller getController(){return controller;}
    public static Client getClient (){return client;}
}
