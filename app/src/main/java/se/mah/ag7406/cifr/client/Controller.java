package se.mah.ag7406.cifr.client;

import java.io.Serializable;

import message.Message;

/**
 * Created by Jens on 2017-04-06.
 */

public class Controller implements Serializable {
    private static Client client = new Client("10.0.2.2",1337);

    public Controller(){

    }
    public void startClient(){
        new Thread() {
            public void run() {
                client.clientRun();

            }
        };
    }

    public boolean checkLogin(final String Username, final String Password){
        startClient();
        boolean response;
        System.out.println("Checkogin");
        new Thread() {
            public void run(){
                client.sendRequest(new Message(Message.LOGIN, Username, Password));
            }
        }.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        response = client.response();
        return response;
    }


    public boolean checkUsername(String name) {
        //kollar om användarnamnet är upptaget
        //kollar mot servern
        return true;
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
