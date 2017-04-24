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
        try {
            output.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleEvent(Message message){
        System.out.println("i Handleevent");
        int type = message.getType();
        switch (type){
            case 0 : controller.responseLogin(message.getStatus());
                    System.out.println("i case 0");
                    break;//Login
            case 1 : controller.responseRegister(message.getStatus());
                    System.out.println("i case 1");
                    break; //Register
            case 2 : controller.recieveMessage(message);
                    System.out.println("i case 2");
                    break;//Message
            case 3 : System.out.println("i case 3");
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



