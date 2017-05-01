package se.mah.ag7406.cifr.client;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.RenderScript;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import message.Message;

/**
 * Created by Jens on 2017-04-06.
 */

public class Controller implements Serializable {
    private Client client;
    private transient LoginScreen login;
    private transient RegistrationScreen register;
    private FileHandler filehandler;
    private transient String[] userList;
    private String myName;
    private BitmapEncoder bitmapEncoder = new BitmapEncoder();

    public Controller(){
        filehandler = new FileHandler(this); //Filehandler tar controller som argument pga test.
    }

    public void startClient(){
//        this.client = new Client("192.168.1.83",1337, this);
        this.client = new Client("10.0.2.2", 1337, this);
        new Thread() {
            public void run() {
                client.clientRun();
            }
        }.start();
    }
    public void setMyName(String name){
        this.myName = name;
    }

    public HashMap<String, ArrayList<Message>> readFiles(){
        Object[] obj = filehandler.read();
        Message[] messages = Arrays.copyOf(obj, obj.length, Message[].class);
        HashMap<String, ArrayList<Message>> map = new HashMap();
        ArrayList<Message> messageArrayList;
        ArrayList<String> senders = new ArrayList();
        for(int i =0; i<messages.length; i++){
            String sender = messages[i].getSender();
            if(map.containsKey(sender)){
                messageArrayList = map.get(sender);
                messageArrayList.add(messages[i]);
                map.put(sender, messageArrayList);
            } else {
                senders.add(sender);
                messageArrayList = new ArrayList<>();
                messageArrayList.add(messages[i]);
                map.put(sender, messageArrayList);
            }
        }
        ArrayList<Message> myMessages = map.get(myName);
        for(int i=0;i< myMessages.size();i++){
            Message message = myMessages.get(i);
            ArrayList<Message> reciever = map.get(message.getRecipient());
            reciever.add(message);
            map.put(message.getRecipient(), reciever);
        }
        map.remove(myName);
        setUserList(senders.toArray(new String[0]));
        // för sortering, inge bra lösning
        //for(int i=0; i<senders.size(); i++){
        //    messageArrayList = map.get(senders.get(i));
        //    Collections.sort(messageArrayList, new Comparator<Message>() {
        //        @Override
        //        public int compare(Message message, Message t1) {
        //            return message.getDate().compareTo(t1.getDate());
        //        }
        //    });
        //    map.remove(senders.get(i));
        //    map.put(senders.get(i), messageArrayList);
        //}
        return map;
    }

    public void writeFile(Message message){
        filehandler.saveToMachine(message);
    }

    /**
     * Creates a new message object to be sent. This method is called from CrateMessage activity.
     * Here the written message is hidden within the image, and the image is sent towards the client.
     * @param receiver The recipient of the message.
     * @param messageText Text that the image should hide.
     * @param image Bitmap that is to be sent.
     */
    public void sendMessage(String receiver, String messageText, Bitmap image) {
        image = encodeBitmap(image, messageText);
        Object imageObject = image;
        final Message newMessage = new Message(Message.MESSAGE, myName, receiver, imageObject);
        new Thread() {
            public void run() {
                //Message newMessage = new Message(Message.MESSAGE, "Testare", "Testare",(Object)image);
                client.sendRequest(newMessage);
            }
        }.start();
    }

    /**
     * Encodes a String into a bitmap using BitmapEncoder.
     * @param image The image used as a host for the encoding.
     * @param message The string that is to be hidden within the bitmap.
     * @return A bitmap with a message encoded within.
     */
    public Bitmap encodeBitmap(Bitmap image, String message) {
        byte[] messageInBytes = message.getBytes();
        Bitmap encodedBitmap = bitmapEncoder.encode(image, messageInBytes);
        return encodedBitmap;
    }

    /**
     * Decodes a message hidden within a bitmap using BitmapEncoder.
     * @param image The image to be decoded.
     * @return A string with the previously hidden text.
     */
    public String decodeBitmap(Bitmap image) {
        byte[] bytes = bitmapEncoder.decode(image);
        String messageText = new String(bytes);
        return messageText;
    }

    public void recieveMessage(Message message){
        Log.d("recieve", "Fick ett meddelande!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        filehandler.saveToMachine(message);
    }
    public void setUserList(String[] list){
        this.userList = list;
    }

    public String[] recieveUserList(){
        return userList;
    }

    /**
     * Gathers the data for the screen displaying the list of conversations.
     * This data consists of a GridItem array. GridItem is an object containing
     * the last sent image, and the username of the conversation partner. Each item
     * of the array represents an ongoing conversation.
     * @param username Username of the logged in user, used as a reference for the
     *                 data gathering.
     * @return The array of gathered GridItems for display.
     */
    public GridItem[] getGridItems(String username){
        //Ta username, som är användaren, fyll arrayen med data.
        //Den referensen behövs kanske inte ens, beror på hur datan ska sparas.
        HashMap<String, ArrayList<Message>> map = readFiles();
        String[] userlist = recieveUserList();
        ArrayList<GridItem> gridList = new ArrayList<>();
        for (int i=0; i<userlist.length; i++){
            if(map.containsKey(userlist[i])&&userlist[i]!=myName){
                ArrayList<Message> arr = map.get(userlist[i]);
                byte[] bild = (byte[])arr.get(0).getImage();// Borde byta message bild till byte-array
//                gridList.add(new GridItem(userlist[i], BitmapFactory.decodeByteArray(bild, 0, bild.length)));
                gridList.add(new GridItem(userlist[i], gridImageManipulation(bild)));
            }
        }
        return Arrays.copyOf(gridList.toArray(), gridList.toArray().length, GridItem[].class);
    }

    /**
     * Used to change the size of the image in the conversation list so all are equal size.
     * Would preferrably blur image as well.
     * @param image Byte array representation of a bitmap.
     * @return The scaled Bitmap image.
     */
    private Bitmap gridImageManipulation(byte[] image) {
        Bitmap newBitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        newBitmap = Bitmap.createScaledBitmap(newBitmap, 20, 20, true); //Gräsligt! /Viktor
        newBitmap = Bitmap.createScaledBitmap(newBitmap, 500, 500, true);
        return newBitmap;
    }

    /**
     * Gathers the data of a conversation, to be displayed in the Conversation
     * activity. The data is collected in an array of ConversationItems, which consist
     * of the time a message was sent, and the image of the message.
     * @param username Username of the conversation partner, used as a reference for data gathering.
     * @return The array of gathered ConversationItems for display.
     */
    public ConversationItem[] getConversation(String username) {
        HashMap<String, ArrayList<Message>> map = readFiles();
        ArrayList<Message> messageList = map.get(username);
        ArrayList<ConversationItem> conversationList = new ArrayList();
        for(int i=0;i<messageList.size();i++){
            byte[] bytes = (byte[])messageList.get(i).getImage();
            conversationList.add(new ConversationItem(messageList.get(i).getDate().toString(), BitmapFactory.decodeByteArray(bytes, 0, bytes.length)));
        }
        return Arrays.copyOf(conversationList.toArray(), conversationList.toArray().length, ConversationItem[].class);
    }
    
    public void checkLogin(final String Username, final String Password, LoginScreen login){
        this.login = login;
        new Thread() {
            public void run(){
                client.sendRequest(new Message(Message.LOGIN, Username, Password));
            }
        }.start();

    }

    public void responseLogin(Message response){
        if(login!=null) {
            login.response(response);
        } else {
            register.response(response);
        }
    }

    public void checkUsername(final String name,final String password, RegistrationScreen register) {
        this.register = register;
        new Thread() {
            public void run(){
                client.sendRequest(new Message(Message.REGISTER, name, password));
            }
        }.start();
    }

    public void responseRegister(Message response){
        register.response(response);
    }

    public boolean checkpassword(String pass1, String pass2) {
        //kollar om lösenorden stämmer överens samt är på rätt format
        boolean number = false, uppercase = false;
        if (pass1.equals(pass2) && pass1.length()>6 && pass1.length()<15){
            for(int i=0;i<pass1.length(); i++) {
                if (Character.isDigit(pass1.charAt(i))) {
                    number = true;
                } else if (Character.isUpperCase(pass1.charAt(i))) {
                    uppercase = true;
                }
            }
        }
        if (number && uppercase){
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUsernameFormat(String name) {
        // KOllar formatet på username
        if (name.length()>=6 && name.length()<=15){
            for(int i=0;i<name.length(); i++){
                if(name.charAt(i)==','){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void logout(){
        myName = null;
        //koppla ner klient??
    }
}
