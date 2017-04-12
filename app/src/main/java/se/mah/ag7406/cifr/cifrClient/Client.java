package se.mah.ag7406.cifr.cifrClient;

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
    private boolean active = false;
    private boolean response = false;
    private ServerListener listener;
    private Buffer<Message> buffer = new Buffer();

    public Client(String IP, int port) {
        this.IP = IP;
        this.port=port;

        //clientRun();
    }
    public void clientRun() {
        try {
            System.out.println("föresocket");
            Socket socket = new Socket(IP, port);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            //output.writeObject(new Message(Message.LOGIN, "Tjeeeenare", "tjena"));
            System.out.println("eftersocket");
        }catch(IOException e){}
        listener = new ServerListener();
    }
    /**
     * Test av jens, behöver input av er andra
     */

    public void sendRequest(Message message){
        try {
            output.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean response(){  //behöver synchronizera, via active?

        return response;
    }
    public void handleEvent(Message message){
        // om meddelandet är svar på en förfrågan. via type?
        response = message.getStatus();
        active = true;
        // annars kör på meddelandehantering.
    }

    private class ServerListener extends Thread {

        public ServerListener() {
            Log.d("Serverlistener  ", "Konstruktor");

            start();
        }
        public void run() {
            Object message;
            Log.d("Serverlistener  ", "i run metod");
            while (true) {
                try {
                    message = (Object)input.readObject();
                    Message mess = (Message)message;
                    handleEvent(mess);//tillagt av jens för test
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } catch (ClassNotFoundException cnfe) {
                    cnfe.printStackTrace();
                }
            }
        }
    }
}



