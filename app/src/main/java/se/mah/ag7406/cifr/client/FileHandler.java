package se.mah.ag7406.cifr.client;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
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
    private File file, folder;
    private Context context;
    private File[] files;
    private Controller controller;

    public FileHandler(Controller controller){
        this.controller = controller; //Detta är för testande/Viktor
        this.context = SuperClass.getContext();
        //delete();
        update();
        //fortest();
    }
    public void fortest(){//för test
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), R.drawable.bilder1);
        boolean imagenulltest = image == null;
        boolean controllernulltest = controller == null;
        Log.d("test", "kollar om image är null: " + imagenulltest);
        Log.d("test", "kollar om controller är null: " + controllernulltest);
        Bitmap newimage = controller.encodeBitmap(image, "Detta är ett test!!");
        saveToMachine(new Message(0,"klas", "Testare", convert(newimage)));
//        saveToMachine(new Message(0,"klas", "Testare", convert(BitmapFactory.decodeResource(context.getResources(), R.drawable.bilder1))));
        saveToMachine(new Message(0,"Testare", "klas", convert(BitmapFactory.decodeResource(context.getResources(), R.drawable.bilder2))));
        saveToMachine(new Message(0,"klas", "Testare", convert(BitmapFactory.decodeResource(context.getResources(), R.drawable.bilder3))));
        saveToMachine(new Message(0,"Testare", "klas", convert(BitmapFactory.decodeResource(context.getResources(), R.drawable.bilder4))));
    }
    public byte[] convert(Bitmap bit){//för test
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void update(){
        file = context.getFilesDir();
        files = file.listFiles();
    }

    public void delete(){
        update();
        for (int i=0; i<files.length;i++){
            files[i].delete();
        }
    }

    public void saveToMachine(Object object){
        Log.d("jag skriver meddelandet","    ");
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
        Log.d("jag läser meddelandet:","   ");

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
        System.out.println("Jag läser meddelandet i FIlehandler");
        update();
        ArrayList<Object> list = new ArrayList();
        Object obj;
        int number = 1;
        for (int i=0; i < file.listFiles().length; i++) {
            obj = readObject(file + "/file_" + number);
            number++;
            list.add(obj);
        }
        return list.toArray();
    }
}
