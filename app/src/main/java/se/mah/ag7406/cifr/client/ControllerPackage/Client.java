package se.mah.ag7406.cifr.client.ControllerPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import message.Message;

/**
 * Acts as Client to the server. Handles sorting of incoming messages and sending requests/messages.
 * Created by Jens Andreassen on 2017-04-06
 */

public class Client {
    private String IP;
    private int port;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Controller controller;
    private Socket socket;

    public Client(String IP, int port, Controller controller) {
        this.IP = IP;
        this.port=port;
        this.controller = controller;

    }

    /**
     * Starts the connection.
     */
    public void clientRun() {
        try {
            socket = new Socket(IP, port);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            output.flush();
        }catch(IOException e){}
        new ServerListener().start();
    }

    /**
     * Closes socket, output and input streams.
     */
    public void clientLogout() {
        try{
            socket.close();
            output.close();
            input.close();
        } catch(IOException e) {}
    }

    /**
     * Checks if connected and sends notification to controller if not, else sends request/message
     * @param message to be sent
     */
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

    /**
     * Sorts the incoming message and sends it to the right method in controller.
     * @param message incoming message
     */
    public void handleEvent(Message message){
        int type = message.getType();
        switch (type){
            case Message.LOGIN :
                controller.responseLogin(message);
                controller.setUserList(message.getContactList());
                break;
            case Message.REGISTER :
                controller.responseRegister(message);
                break;
            case Message.MESSAGE :
                controller.recieveMessage(message);
                break;
            case Message.STATUS :
                break;
            case Message.SEARCH :
                controller.recieveSearch(message);
                break;
            case Message.CONTACTLIST :
                System.out.println("FICK EN LISTA!!!!!!!!!!!!!!!!!");
                for(int i =0; i <message.getContactList().length; i++){
                    System.out.println(message.getContactList()[i]);
                }
                controller.setUserList(message.getContactList());
                break;
            case Message.CONTACTLIST_ADD :
                break;
            case Message.CONTACTLIST_REMOVE :
                break;
        }
    }

    /**
     * Listens to messages from the server
     */
    private class ServerListener extends Thread {
        public void run() {
            Object message;
            while (true) {
                try {
                    message = (Object)input.readObject();
                    Message mess = (Message)message;
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



