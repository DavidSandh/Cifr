package se.mah.ag7406.cifr.cifrClient;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import se.mah.ag7406.cifr.message.Message;

/**
 *
 */

public class Client extends Thread {

    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Socket socket;
    private Message message;
    private String IP;
    private int port;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public Client(Message message, String IP, int port) {
        this.message = message;
        this.IP = IP;
        this.port=port;
        new ServerListener(IP, port);
    }

    private class ServerListener extends Thread {
        private String ip;
        private int port;
        /**
         *  Constructor that sets ip and port and opens a new input and output stream.
         *  Also sends username to server.
         * @param ip ip to use
         * @param port port to use
         */
        public ServerListener(String ip, int port) {
            this.ip = ip;
            this.port = port;
            try {
                socket = new Socket(ip, port);

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            try {
                input = new ObjectInputStream(socket.getInputStream());
                output = new ObjectOutputStream(socket.getOutputStream());
                output.flush();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            start();
        }
        /**
         * Method that recieves messages from server.
         */
        public void run() {
            Message message;
            while (true) {
                try {
                    message = (Message)input.readObject();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } catch (ClassNotFoundException cnfe) {
                    cnfe.printStackTrace();
                }
            }
        }
    }
        public boolean sendMessage(Object obj) throws IOException, ClassNotFoundException {

            return false;
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




