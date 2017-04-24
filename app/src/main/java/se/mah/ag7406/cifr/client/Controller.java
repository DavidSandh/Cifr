package se.mah.ag7406.cifr.client;

import android.content.Context;

import java.io.Serializable;

import message.Message;

/**
 * Created by Jens on 2017-04-06.
 */

public class Controller implements Serializable {
    private Client client;
    private LoginScreen login;
    private RegistrationScreen register;
    private Context context;
    private FileHandler filehandler;

    public Controller(Context context){
        this.context = context;
        filehandler = new FileHandler(context);
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

    public void recieveUserList(){

    }

    public void getGridItems(){

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
        if (name.length()>6 && name.length()<15){
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
