package se.mah.ag7406.cifr.client;

import android.app.Application;
import android.content.Context;

/**
 * Runs when application is started and handles instances classes used by the app
 * Created by Jens Andreassen on 2017-04-26.
 */

public class SuperClass extends Application {
    private static Application instance;
    private static Client client;
    private static Controller controller;

    /**
     * Runs on start of application stores context and creates new Controller
     */
    public void onCreate(){
        super.onCreate();
        instance = this;
        controller = new Controller();
    }

    /**
     * Static method witch returns Context
     * @return Context
     */
    public static Context getContext(){
        return instance.getApplicationContext();
    }

    /**
     * Static method witch returns Controller
     * @return controller
     */
    public static Controller getController(){return controller;}

    /**
     * Static method witch returns Client
     * @return client
     */
    public static Client getClient (){return client;}
}
