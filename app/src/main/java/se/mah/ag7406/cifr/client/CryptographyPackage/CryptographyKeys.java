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
        context = SuperClass.getContext();
        file = context.getFilesDir();
        files = file.listFiles();
        for(int i = 0; i < files.length; i++) {
            System.out.println(files[i].toString() + " " + i);
        }
    }

    public static void savePrivateKey(Object object, String username) {
        System.out.println("1");
        update();
        System.out.println("2");
        try {
            System.out.println("3");
            FileOutputStream fos = context.openFileOutput(username.toLowerCase(), Context.MODE_PRIVATE);
            System.out.println("4");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            System.out.println("5");
            oos.writeObject(object);
            System.out.println("5");
            oos.close();
            System.out.println("5");
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

    }

    public static Object readKey(String username) {
        update();
        try {
            String filename = file + "/" + username;
            FileInputStream fis = new FileInputStream(filename.toLowerCase());
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
