package se.mah.ag7406.cifr.client.ControllerPackage;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * The class handles the reading and writing of messages to local storage.
 * Created by Jens Andreassen on 2017-04-19.
 */
public class FileHandler {
    private File file, folder;
    private Context context;
    private File[] files;
    private Controller controller;

    public FileHandler(Controller controller) {
        this.controller = controller;
        this.context = SuperClass.getContext();
        update();
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
    public void saveToMachine(Object object, String reciever){
        update();
        FilenameFilter fileNameFilter = new FileFilter();
        String filename;
        int number = 0;
        String[] fileList = file.list(fileNameFilter);
        for(int i = 0; i < fileList.length; i++) {
            if(fileList[i].contains(reciever)) {
                number++;
            }
        }
        filename = controller.getMyName() + "%" + reciever + "%" + number;
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
        FileFilter fileNameFilter = new FileFilter();
        String[] fileList = file.list(fileNameFilter);
        for (int i=0; i < fileList.length; i++) {
            obj = readObject(file + "/" + fileList[i]);
            list.add(obj);
        }
        return list.toArray();
    }

    public void delete(String name) {
        FileFilter fileNameFilter = new FileFilter();
        String[] fileList = file.list(fileNameFilter);
        for(int i = 0; i < fileList.length; i++) {
            if(fileList[i].contains(name)) {

                File toDelete = new File(file + "/" + fileList[i]);
                toDelete.delete();
            }
        }
    }

    private class FileFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            if(name.contains(controller.getMyName())) {
                return true;
            }
            return false;
        }
    }
}