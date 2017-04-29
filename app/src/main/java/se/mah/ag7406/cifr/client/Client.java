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
            System.out.println("föresocket");
            Socket socket = new Socket(IP, port);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            output.flush();
            System.out.println("eftersocket");
        }catch(IOException e){}
        new ServerListener().start();
    }

    public void sendRequest(Message message){
        Log.d("sendreq", "true");
        try {
            output.writeObject(message);
            Log.d("sendreq", "skickat");
        } catch (IOException e) {
            //controller.responseLogin(new Message(1,true));
            e.printStackTrace();
        }
    }

    public void handleEvent(Message message){
        Log.d("handleevent", "true");
        int type = message.getType();
        switch (type){
            case Message.LOGIN :
                controller.responseLogin(message);
                controller.setUserList(message.getContactList());
                    System.out.println("i case 0");
                    break;//Login
            case Message.REGISTER : controller.responseRegister(message.getStatus());
                    System.out.println("i case 1");
                    break; //Register
            case Message.MESSAGE : controller.recieveMessage(message);
                    System.out.println("i case 2");
                    break;//Message
            case Message.STATUS : System.out.println("i case 3");
                    break;//Status
            //case 4 : controller.setUserList(message.getUserList);
            //        break;
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



