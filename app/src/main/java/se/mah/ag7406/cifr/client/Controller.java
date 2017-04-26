package se.mah.ag7406.cifr.client;

import java.io.Serializable;

import message.Message;

/**
 * Created by Jens on 2017-04-06.
 */

public class Controller implements Serializable {
    private Client client;
    private LoginScreen login;
    private RegistrationScreen register;
    private FileHandler filehandler;

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

    public void recieveMessage(Message message){

    }

    public String[] recieveUserList(){
        String[] userlist = new String[5];
        return userlist;
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
        GridItem[] items = new GridItem[5];
        return items;
    }

    /**
     * Gathers the data of a conversation, to be displayed in the Conversation
     * activity. The data is collected in an array of ConversationItems, which consist
     * of the time a message was sent, and the image of the message.
     * @param username Username of the conversation partner, used as a reference for data gathering.
     * @return The array of gathered ConversationItems for display.
     */
    public ConversationItem[] getConversation(String username) {
        ConversationItem[] conversation = new ConversationItem[5];
        return conversation;
    }
    
    public void checkLogin(final String Username, final String Password, LoginScreen login){
        this.login = login;
        new Thread() {
            public void run(){
                client.sendRequest(new Message(Message.LOGIN, Username, Password));
            }
        }.start();

    }
    public void responseLogin(boolean response){
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
