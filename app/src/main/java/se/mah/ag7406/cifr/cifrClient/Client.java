package se.mah.ag7406.cifr.cifrClient;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import se.mah.ag7406.cifr.message.Message;

/**
 * Created by Max on 2017-04-06.
 */

public class Client extends Thread {

    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Socket socket;
    private Message message;


    public Client(Message message, Integer IP, String Name ) {
        this.message = message;




    }

    protected void connect(Integer IP, String Name) {
        try {
            Socket socket = new Socket(Name,IP);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            new Thread(this).start();

        } catch (IOException e) {


        }
    }


        public boolean sendMessage(Object obj) throws IOException, ClassNotFoundException {
            //spara inkommande bild
            Object out = obj;
            //skicka bild
            oos.writeObject(obj);
            Message mess =(Message)ois.readObject();
            Boolean aBoolean = message.getStatus();
            oos.flush();
            return aBoolean;

        }


        public void run(){

            while (!socket.isClosed()) {
                try {

                    Object in = ois.readObject();
                    sendMessage(new Object());




                } catch(IOException | ClassNotFoundException e){


            }


            }

    }




    }




