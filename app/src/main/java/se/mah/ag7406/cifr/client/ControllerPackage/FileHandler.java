package se.mah.ag7406.cifr.client.ControllerPackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
 * The class handles the reading and writing of messages to local storage.
 * Created by Jens Andreassen on 2017-04-19.
 */
public class FileHandler {
    private File file, folder;
    private Context context;
    private File[] files;
    private Controller controller;

    public FileHandler(Controller controller){
        this.controller = controller; //Detta är för testande/Viktor
        this.context = SuperClass.getContext();
        update();
    }

    /**
     * Only used for testing, creates a mockup of a message.
     */
    public void fortest() {//för test
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), R.drawable.bilder1);
        boolean imagenulltest = image == null;
        boolean controllernulltest = controller == null;
        Bitmap newimage = controller.encodeBitmap(image, "Detta är ett test!!");
        saveToMachine(new Message(0, "klas", "Testare", convert(newimage)));
        //saveToMachine(new Message(0,"klas", "Testare", convert(BitmapFactory.decodeResource(context.getResources(), R.drawable.bilder1))));
    }

    /**
     * Converts bitmap to byte-array. Only used by fortest for testing reasons.
     * @param bit bitmap to be convertet
     * @return The Bytearray
     */
    public byte[] convert(Bitmap bit){//för test
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    /**
     * Updates variables to have the latest state of the files stored.
     */
    public void update(){
        file = context.getFilesDir();
        files = file.listFiles();
    }

    /**
     * Deletes all messages stored. Only used for testing at the moment.
     */
    public void delete(){
        update();
        for (int i=0; i<files.length;i++){
            files[i].delete();
        }
    }

    /**
     * Saves object to local storage and names the file according to the existing files.
     * @param object to be saved
     */
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

    /**
     * Reads a file from the local storage.
     * @param filename file to be read.
     * @return the file.
     */
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

    /**
     * Reads all the files at the local storage with help from readObject()
     * @return an Object-array with all the messages.
     */
    public Object[] read(){
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
