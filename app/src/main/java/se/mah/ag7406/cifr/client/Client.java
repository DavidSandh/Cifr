package se.mah.ag7406.cifr.client;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import message.Message;



public class Client {
    private String IP;
    private int port;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Controller controller;

    public Client(String IP, int port, Controller controller) {
        this.IP = IP;
        this.port=port;
        this.controller = controller;

    }
    public void clientRun() {
        try {
            Socket socket = new Socket(IP, port);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            output.flush();
        }catch(IOException e){}
        new ServerListener().start();
    }

    public void sendRequest(Message message){
        if(output==null){
            controller.responseLogin(new Message(3,true));
            clientRun();
        } else {
            try {
                output.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleEvent(Message message){
        int type = message.getType();
        switch (type){
            case Message.LOGIN :
                controller.responseLogin(message);
                controller.setUserList(message.getContactList());
                break;//Login
            case Message.REGISTER :
                controller.responseRegister(message);
                System.out.println("i case 1");
                break; //Register
            case Message.MESSAGE :
                controller.recieveMessage(message);
                System.out.println("i case 2");
                break;//Message
            case Message.STATUS :
                break;//Status
            case Message.SEARCH :

                break;
            case Message.CONTACTLIST :
                Log.d("Recieved Contactlist", "blabla");
                controller.setUserList(message.getContactList());
                break;//Status
            case Message.CONTACTLIST_ADD :
                break;//Status
            case Message.CONTACTLIST_REMOVE :
                break;//Status
        }
    }

    private class ServerListener extends Thread {

        public void run() {
            Object message;
            Log.d("Serverlistener  ", "i run metod");
            while (true) {
                try {
                    System.out.println("väntar på ett meddelande");
                    message = (Object)input.readObject();
                    Message mess = (Message)message;
                    System.out.println("fått ett meddelande");
                    Log.d("handleevent", "true");
                    handleEvent(mess);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } catch (ClassNotFoundException cnfe) {
                    cnfe.printStackTrace();
                }
            }
        }
    }
}



