package se.mah.ag7406.cifr.client;

import android.content.Context;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import message.Message;
import se.mah.ag7406.cifr.R;

/**
 * Created by Jens on 2017-04-19.
 */

public class FileHandler {
    private File file;
    private Context context;
    private File[] files;

    public FileHandler(){
        this.context = SuperClass.getContext();
        file = new File(context.getFilesDir()+ File.separator+"messages");
        update();
        fortest();
    }
    public void fortest(){
        //int type, String sender, String recipient, Object image
        saveToMachine(new Message(0,"klas", "Testare", BitmapFactory.decodeResource(context.getResources(), R.drawable.bilder1)));
        saveToMachine(new Message(0,"Testare", "klas", BitmapFactory.decodeResource(context.getResources(), R.drawable.bilder2)));
        saveToMachine(new Message(0,"klas", "Testare", BitmapFactory.decodeResource(context.getResources(), R.drawable.bilder3)));
        saveToMachine(new Message(0,"Testare", "klas", BitmapFactory.decodeResource(context.getResources(), R.drawable.bilder4)));
    }

    public void update(){

        files = file.listFiles();
    }

    public void delete(){
        update();
        for (int i=0; i<files.length;i++){
            files[i].delete();
        }

    }

    public void saveToMachine(Object object){
        update();
        String filename;
        if(files.length != 0){
            String lastFile = files[files.length-1].getName();
            String[] parts = lastFile.split("_");
            int number = Integer.parseInt(parts[1])+1;
            filename = "file_" + number;
        } else {
            filename = "file_1";
        }
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
        FileInputStream fis;
        Object obj;
        try {
            fis = new FileInputStream(filename);
            ObjectInputStream is = new ObjectInputStream(fis);
            obj = is.readObject();
            is.close();
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
        update();
        ArrayList<Object> list = new ArrayList();
        Object obj;
        for (int i=0; i < file.listFiles().length; i++) {
            obj = readObject(file + "/file_" + i+1);
            list.add(obj);
        }
        return list.toArray();
    }
}
