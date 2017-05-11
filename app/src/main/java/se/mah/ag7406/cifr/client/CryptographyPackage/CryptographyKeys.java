package se.mah.ag7406.cifr.client.CryptographyPackage;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import se.mah.ag7406.cifr.client.ControllerPackage.Controller;
import se.mah.ag7406.cifr.client.ControllerPackage.SuperClass;

/**
 * Created by Lucas on 11/05/2017.
 */

public class CryptographyKeys {
    private static File file;
    private static Context context;
    private static File[] files;
    private static Controller controller;

    public static void update(){
        file = context.getFilesDir();
        files = file.listFiles();
    }

    public static void savePrivateKey(Object object) {
        update();
        try {
            FileOutputStream fos = context.openFileOutput("key", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

    }

    public static Object readKey() {
        try {
            FileInputStream fis = new FileInputStream("key");
            ObjectInputStream is = new ObjectInputStream(fis);
            Object obj = is.readObject();
            is.close();
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
