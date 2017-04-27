package se.mah.ag7406.cifr.client;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import message.Message;

/**
 * Created by Jens on 2017-04-06.
 */

public class Controller implements Serializable {
    private transient Client client;
    private transient LoginScreen login;
    private transient RegistrationScreen register;
    private transient FileHandler filehandler;
    private transient String[] userList;
    private transient String myName;

    public Controller(){
        filehandler = new FileHandler();
    }

    public void startClient(){
        this.client = new Client("10.0.2.2",1337, this);
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
        filehandler = new FileHandler();
        Message[] messages =(Message[])filehandler.read();// måste kolla om null;
        HashMap<String, ArrayList<Message>> map = new HashMap();
        ArrayList<Message> messageArrayList;
        ArrayList<String> senders = new ArrayList();
        for(int i =0; i<messages.length; i++){
            String sender = messages[i].getSender();
            if(map.containsKey(sender)){
                messageArrayList = map.get(sender);
                messageArrayList.add(messages[i]);
                map.remove(sender);
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
            map.remove(message.getRecipient());
            map.put(message.getRecipient(), reciever);
        }
        map.remove(myName);
        setUserList((String[])senders.toArray());
        for(int i=0; i<senders.size(); i++){
            messageArrayList = map.get(senders.get(i));
            Collections.sort(messageArrayList, new Comparator<Message>() {
                @Override
                public int compare(Message message, Message t1) {
                    return message.getDate().compareTo(t1.getDate());
                }
            });
            map.remove(senders.get(i));
            map.put(senders.get(i), messageArrayList);
        }
        return map;
    }
    public void writeFile(Message message){
        filehandler = new FileHandler();
        filehandler.saveToMachine(message);
    }

    public void recieveMessage(Message message){
        filehandler = new FileHandler();
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
                gridList.add(new GridItem(userlist[i],(Bitmap)arr.get(0).getImage()));
            }
        }
        return (GridItem[])gridList.toArray();
        //GridItem[] items = new GridItem[5];
        //return items;
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
        ArrayList<Message> list = map.get(username);
        ArrayList<ConversationItem> arrayList = new ArrayList();
        for(int i=0;i<list.size();i++){
            arrayList.add(new ConversationItem(list.get(i).getDate().toString(), (Bitmap)list.get(i).getImage()));
        }
        return (ConversationItem[])arrayList.toArray();
        //ConversationItem[] conversation = new ConversationItem[5];
        //return conversation;
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
        login.response(response);
    }


    public void checkUsername(final String name,final String password, RegistrationScreen register) {
        this.register = register;
        new Thread() {
            public void run(){
                client.sendRequest(new Message(Message.REGISTER, name, password));
            }
        }.start();
    }

    public void responseRegister(boolean response){
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
}
