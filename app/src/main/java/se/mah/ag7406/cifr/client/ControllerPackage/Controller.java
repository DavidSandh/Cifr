package se.mah.ag7406.cifr.client.ControllerPackage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import message.Message;
import se.mah.ag7406.cifr.client.ConversationListPackage.GridItem;
import se.mah.ag7406.cifr.client.ConversationPackage.Conversation;
import se.mah.ag7406.cifr.client.ConversationPackage.ConversationItem;
import se.mah.ag7406.cifr.client.SearchActivityPackage.SearchActivity;
import se.mah.ag7406.cifr.client.StartActivities.LoginScreen;
import se.mah.ag7406.cifr.client.StartActivities.RegistrationScreen;

/**
 * Acts as controller for the logik in the application
 * Created by Jens Andreassen and Viktor Ekström on 2017-04-06.
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
    private boolean flag;
    private String flagname;
    private Activity ConversationActivity;
    private ArrayList<String> notifications = new ArrayList();

    public Controller(){
        filehandler = new FileHandler(this); //Filehandler tar controller som argument pga test.
    }

    /**
     * Creates a new client and starts it.
     */
    public void startClient(){
//        this.client = new Client("192.168.1.83", 1337, this);
//        this.client = new Client("192.168.43.71", 1337, this);

        this.client = new Client("192.168.43.71",1337,this);

        //this.client = new Client("192.168.1.164",1337,this);
//        this.client = new Client("192.168.43.71", 1337, this);
        new Thread() {
            public void run() {
                client.clientRun();
            }
        }.start();
    }

    /**
     * Sets the active users name
     * @param name Username
     */
    public void setMyName(String name){
        this.myName = name;
    }

    /**
     * Returns the active users name
     * @return Username
     */
    public String getMyName(){
        return myName;
    }

    /**
     * Calls filehandler to read all files and sorts them according to conversations
     * with different users
     * @return Hashmap containg username as key and an ArrayList containing the messages
     */
    private HashMap<String, ArrayList<Message>> readFiles(){
        Object[] obj = filehandler.read();
        Message[] messages = Arrays.copyOf(obj, obj.length, Message[].class);
        HashMap<String, ArrayList<Message>> map = new HashMap();
        ArrayList<Message> messageArrayList;
        if(userList==null){
            return null;
        }
        for(int i =0; i<userList.length; i++){
            messageArrayList = new ArrayList<>();
            for(int j=0; j<messages.length; j++){
                if( messages[j]!=null ){

                if(userList[i].equalsIgnoreCase(messages[j].getSender())||userList[i].equalsIgnoreCase(messages[j].getRecipient())){
                    messageArrayList.add(messages[j]);
                }
                }


            }
            if(!messageArrayList.isEmpty()) {
                map.put(userList[i], messageArrayList);
            }
        }
        return map;
    }

    /**
     * Writes file to local storage by using filehandler
     * @param message Message to be saved
     */
    public void writeFile(Message message, String reciever){
        if(message==null){
            System.out.println("FÖRSÖKER SKRIVA ETT MESSAGE OBJEKT SOM ÄR NULL");
        } else {
            filehandler.saveToMachine(message, reciever);
        }
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
        final Message newMessage = new Message(Message.MESSAGE, myName, receiver, msgImage);
        writeFile(newMessage, newMessage.getRecipient());
        new Thread() {
            public void run() {
                client.sendRequest(newMessage);
            }
        }.start();
    }

    /**
     * Converts Bitmap to byte-array to help the serialization when saving and sending
     * @param bit Bitmap to be converted
     * @return The resulting byte-array
     */
    public byte[] convert(Bitmap bit){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    /**
     * Creates a thread and sends a message or request
     * @param type Message type
     * @param name Sender
     * @param user Reciever
     */
    public void sendMessage(final int type, final String name, final String user) {
        new Thread() {
            public void run() {
                if(name==null){
                    System.out.println("name null");
                    client.sendRequest(new Message(type, user));
                } else {
                    System.out.println("name null");
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
        byte[] bytes = bitmapEncoder.decode(image);
        String messageText = new String(bytes);
        return messageText;
    }

    /**
     * Called upon incoming messages, saves to local storage
     * @param message message to save
     */
    public void recieveMessage(Message message){
        writeFile(message, message.getSender());
        checkflag(message.getSender());
        setNotificationflag(message.getSender());
    }

    public void setNotificationflag(String sender){
        if (!notifications.contains(sender)){
            notifications.add(sender);
        }
    }

    public boolean getNotificationflag(String sender){
        if (notifications.contains(sender)){
            notifications.remove(sender);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets the contactlist recieved from the server.
     * @param list Contactlist
     */
    public void setUserList(String[] list){
        if(userList!=null){
            if(list.length>userList.length){
                search.sendNotification(search.getUserNameToAdd());
        }

        }
        this.userList = list;

    }

    /**
     * Returns the contactlist
     * @return contactlist
     */
    public String[] recieveUserList(){
        return userList;
    }

    /**
     * Returns the userlist of the user sorted alphabetically.
     * Used by the ContactList activity.
     * @return alphabetical userlist.
     */
    public String[] getContactList() {
        String[] contactList = userList;
        if(contactList != null) {
            Arrays.sort(contactList);
        }
        return contactList;
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
        if(userlist==null  ){
            return null;
        }
        for (int i=0; i<userlist.length; i++){
            if(map.containsKey(userlist[i])){
                System.out.println("Jag är i forloopen i griditems");
                ArrayList<Message> arr = map.get(userlist[i]);
                byte[] bild = (byte[])arr.get(arr.size() - 1).getImage();
                gridList.add(new GridItem(userlist[i], gridImageManipulation(bild), arr.get(arr.size() - 1).getDateObject()));
            }
        }
        Collections.sort(gridList, Collections.reverseOrder());
        return Arrays.copyOf(gridList.toArray(), gridList.toArray().length, GridItem[].class);
    }

    /**
     * Used to change the size of the image in the conversation list so all are equal size.
     * Would preferrably blur image as well.
     * @param image Byte array representation of a bitmap.
     * @return The scaled Bitmap image.
     */
    private Bitmap gridImageManipulation(byte[] image) {
        //int screenHeight = SuperClass.getContext().getResources().getDisplayMetrics().heightPixels;
        int screenWidth = SuperClass.getContext().getResources().getDisplayMetrics().widthPixels;
        Bitmap newBitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        newBitmap = Bitmap.createScaledBitmap(newBitmap, 20, 20, true);
        newBitmap = Bitmap.createScaledBitmap(newBitmap, (screenWidth/2)-10, (screenWidth/2)-10, true);
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
            conversationList.add(new ConversationItem(messageList.get(i).getDate().toString(), BitmapFactory.decodeByteArray(bytes, 0, bytes.length), messageList.get(i).getSender()));
        }
        return Arrays.copyOf(conversationList.toArray(), conversationList.toArray().length, ConversationItem[].class);
    }

    /**
     * Sends request regarding login-attempt and saves instance of LoginScreen for notification purposes
     * @param Username Username
     * @param Password Password
     * @param login Instance of LoginScreen
     */
    public void checkLogin(final String Username, final String Password, LoginScreen login){
        this.login = login;
        new Thread() {
            public void run(){
                client.sendRequest(new Message(Message.LOGIN, Username, Password));
            }
        }.start();
    }

    /**
     * Handles the response regarding loginattempt from the server
     * @param response Message containing response from server
     */
    public void responseLogin(Message response){
        if(login!=null) {
            login.response(response);
        } else {
            register.response(response);
        }
    }

    /**
     * Checks if the Username is taken via client
     * Stores instance of RegistrationScreen for notification purposes
     * @param name Wanted username
     * @param password Wanted password
     * @param register Instance of registrationScreen
     */
    public void checkUsername(final String name,final String password, RegistrationScreen register) {
        this.register = register;
        new Thread() {
            public void run(){
                client.sendRequest(new Message(Message.REGISTER, name, password));
            }
        }.start();
    }

    /**
     * Handles the response regarding Registration from server
     * @param response Message containing the response
     */
    public void responseRegister(Message response){
        register.response(response);
    }

    /**
     * Checks the format of the password and that both passwords entered are identical
     * @param pass1
     * @param pass2
     * @return true if correct otherwise false
     */
    public boolean checkpassword(String pass1, String pass2) {
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

    /**
     * Checks that the format of the username is correct
     * @param name Username
     * @return true if correct format
     */
    public boolean checkUsernameFormat(String name) {
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

    /**
     * Call upon logout, not finished
     */
    public void logout(){
        myName = null;
        userList=null;
        client.clientLogout();
        //koppla ner klient??
    }

    /**
     * Handles response regarding search
     * @param message Message containing response
     */
    public void recieveSearch(Message message) {
        System.out.println("Svar från servern" + message.getUsername());
        search.response(message.getUsername());
    }

    /**
     * Sends the username for searching for users on the server
     * Stores an instance of SearchActivity for notification purposes
     * @param user Username for the search
     * @param search  Instance of SearchActivity
     */
    public void sendSearch(final String user, SearchActivity search) {
        this.search = search;
        sendMessage(Message.SEARCH, getMyName(), user);
    }

    public void delete(String name) {
        filehandler.delete(name);
    }

    public void setflag(boolean b, String conversationUsername, Activity activity) {
        ConversationActivity = activity;
        flag = b;
        flagname = conversationUsername;
    }

    public void checkflag(String sender){
        if (flag && sender.equalsIgnoreCase(flagname)){
            Intent intent = new Intent(ConversationActivity, Conversation.class);
            intent.putExtra("username" ,flagname);
            ConversationActivity.startActivity(intent);
        }
    }
}
