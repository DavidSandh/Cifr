package se.mah.ag7406.cifr.cifrClient;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Max on 2017-04-06.
 */

public class Client extends Thread {

    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Socket socket;
    private String message;

    protected void connect(Integer IP, String Name) {
        try {
            Socket socket = new Socket(Name,IP);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            new Thread(this).start();

        } catch (IOException e) {


        }
    }


        public void run(){

            while (!socket.isClosed()) {
                try {

                    Object in = ois.readObject();
                    Object out = message;
                    oos.writeObject(message);


                } catch(IOException | ClassNotFoundException e){


            }


            }

    }




    }



}
