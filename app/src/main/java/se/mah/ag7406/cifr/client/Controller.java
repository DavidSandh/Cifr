package se.mah.ag7406.cifr.client;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
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
    private final FileHandler filehandler;
    private transient String[] userList;
    private String myName;
    private BitmapEncoder bitmapEncoder = new BitmapEncoder();
    private SearchActivity search;

    public Controller(){
        filehandler = new FileHandler(this); //Filehandler tar controller som argument pga test.
    }

    public void startClient(){
//        this.client = new Client("192.168.1.83",1337, this);
//           this.client = new Client("192.168.43.71", 1337, this);


      this.client = new Client("10.2.24.208", 1337, this);

        new Thread() {
            public void run() {
                client.clientRun();
            }
        }.start();
    }
    public void setMyName(String name){
        this.myName = name;
    }

    public String getMyName(){
        return myName;
    }

    public HashMap<String, ArrayList<Message>> readFiles(){
        Log.d("I read i controller", "jkjkjkj");
        Object[] obj = filehandler.read();
        Message[] messages = Arrays.copyOf(obj, obj.length, Message[].class);
        HashMap<String, ArrayList<Message>> map = new HashMap();
        ArrayList<Message> messageArrayList;
        if(userList == null){
            return  null;
        }

        for(int i =0; i<userList.length; i++){
            messageArrayList = new ArrayList<>();
            for(int j=0; j<messages.length; j++){
                if(userList[i].equalsIgnoreCase(messages[j].getSender())||userList[i].equalsIgnoreCase(messages[j].getRecipient())){
                    messageArrayList.add(messages[j]);
                }
            }
            if(!messageArrayList.isEmpty()) {
                map.put(userList[i], messageArrayList);
            }
//            if(map.containsKey(sender)){
//                System.out.println("Controller: map.containsKey(sender) == true");
//                messageArrayList = map.get(sender);
//                messageArrayList.add(messages[i]);
//                map.put(sender, messageArrayList);
//            } else {
//                System.out.println("Controller: mapContainsKey(sender) == false");
//                senders.add(sender);
//                messageArrayList = new ArrayList<>();
//                messageArrayList.add(messages[i]);
//                map.put(sender, messageArrayList);
            }
//
//        ArrayList<Message> myMessages = map.get(myName);
//        if(myMessages==null){
//            System.out.println("Controller: myMessages är null, return map");
//            return map;
//        }
//        for(int i=0;i< myMessages.size();i++){
//            Message message = myMessages.get(i);
//            ArrayList<Message> reciever = map.get(message.getRecipient());
//            if(reciever==null){
//                System.out.println("Controller: receiver == null");
//                ArrayList<Message> newreciever = new ArrayList<>();
//                newreciever.add(message);
//                System.out.println("i true " + message);
//                map.put(message.getRecipient(), newreciever);
//            } else {
//                System.out.println("Controller: else sats, receiver != null");
//                reciever.add(message);
//                System.out.println("i false" + message.getRecipient());
//                map.put(message.getRecipient(), reciever);
//            }
//        }
//        map.remove(myName);
//        System.out.println("Controller: Storlek på map efter remove:" + map.size());
//        System.out.println("Controller: map.remove(myName)");
//        //setUserList(senders.toArray(new String[0]));
//        // för sortering, inge bra lösning
//        //for(int i=0; i<senders.size(); i++){
//        //    messageArrayList = map.get(senders.get(i));
//        //    Collections.sort(messageArrayList, new Comparator<Message>() {
//        //        @Override
//        //        public int compare(Message message, Message t1) {
//        //            return message.getDate().compareTo(t1.getDate());
//        //        }
//        //    });
//        //    map.remove(senders.get(i));
//        //    map.put(senders.get(i), messageArrayList);
//        //}
//        System.out.println("return map");
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
        Bitmap newImage = encodeBitmap(image, messageText);
        byte[] msgImage = convert(newImage);
//        Object msgImage = image;
//        Object imageObject = image;
//        final Message newMessage = new Message(Message.MESSAGE, myName, receiver, imageObject);
//        final Message newMessage = new Message(Message.MESSAGE, myName, receiver,(Object)image);
        final Message newMessage = new Message(Message.MESSAGE, myName, receiver, msgImage);
        filehandler.saveToMachine(newMessage);
        new Thread() {
            public void run() {
                client.sendRequest(newMessage);
            }
        }.start();
    }

    public byte[] convert(Bitmap bit){//för test
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void sendMessage(final int type, final String name, final String user) {
        new Thread() {
            public void run() {
                if(name==null){
                    client.sendRequest(new Message(type, user));
                } else {
                    client.sendRequest(new Message(type, name, user));
                }
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
//        ByteArrayOutputStream test = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.PNG, 0, test);
//        byte[] array = test.toByteArray();
        byte[] bytes = bitmapEncoder.decode(image);
        String messageText = new String(bytes);
        return messageText;
    }

    public void recieveMessage(Message message){
        Log.d("recieve", "Fick ett meddelande!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        filehandler.saveToMachine(message);
    }
    public void setUserList(String[] list){
        Log.d("Recieved Contactlist", "blabla");
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
     * @return The array of gathered GridItems for display.
     */
    public GridItem[] getGridItems(){
        HashMap<String, ArrayList<Message>> map = readFiles();
        String[] userlist = recieveUserList();
        ArrayList<GridItem> gridList = new ArrayList<>();
        if(userlist== null){
            return null;
        }
        for (int i=0; i<userlist.length; i++){
            if(map.containsKey(userlist[i])){
                System.out.println("Jag är i forloopen i griditems");
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
        System.out.println("I ConversationItem controller: Username: "+ username);
        HashMap<String, ArrayList<Message>> map = readFiles();
        ArrayList<Message> messageList = map.get(username);
        if(messageList == null) {
            System.out.println("FELX: Messagelist: ICOnversation i controller: " + messageList);
            return null;
        }
        ArrayList<ConversationItem> conversationList = new ArrayList();
        for(int i=0;i<messageList.size();i++){
            System.out.println("I for Loop I ConversationItem" +messageList.get(i).getSender());
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

    public void recieveSearch(Message message) {
        System.out.println("Svar från servern" + message.getUsername());
        search.response(message.getUsername());
        //search.response("Testare");
    }

    public void sendSearch(final String user, SearchActivity search) {
        this.search = search;
        new Thread() {
            public void run() {
                client.sendRequest(new Message(Message.SEARCH, user));
            }
        }.start();
        //sendMessage(Message.SEARCH, null, user);
    }

}
