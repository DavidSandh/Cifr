package se.mah.ag7406.cifr.client;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Jens on 2017-04-19.
 */

public class FileHandler {
    private ObjectOutputStream oos;
    private File file;
    private Context context;

    public FileHandler(Context context){
        Log.d("FileHandler", "Startad");
        this.context = context;

        try {
            FileInputStream fis = context.openFileInput("file name");
            ObjectInputStream ois = new ObjectInputStream(fis);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        file = context.getFilesDir();
    }

    public void save(Object object){
        Log.d("FileHandler", "Save to machine");
        File[] files = file.listFiles();
        String lastFile = files[files.length].getName();
        String[] parts = lastFile.split("_");
        int number = Integer.parseInt(parts[1])+1;
        String filename = "file_" + number;
        Log.d("FileHandler", "Save to machine location: " + filename);
        try {
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    public Object readObject(String filename) {
        Log.d("FileHandler", "I readObject");
        FileInputStream fis;
        Object obj;
        try {
            Log.d("FileHandler", "I readObject i try sats");
            fis = new FileInputStream(filename);
            ObjectInputStream is = new ObjectInputStream(fis);
            Log.d("FileHandler", "I readObject efter strömmar skapade");
            obj = is.readObject();
            is.close();
            Log.d("FileHandler", "I readObject slutet av try");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return obj;
    }

    public Object[] read(){
        Log.d("FileHandler", "I read");
        //int number = 1;
        ArrayList<Object> list = new ArrayList();
        Object obj;
        for (int i=0; i < file.listFiles().length; i++) {
            obj = readObject(file + "/file_" + i+1);
            list.add(obj);
            //number++;
        }
        Log.d("FileHandler", "I read efter for-loop");
        return list.toArray();
    }
}
