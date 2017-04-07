package se.mah.ag7406.cifr.cifrClient;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import se.mah.ag7406.cifr.message.Message;

/**
 *
 */

public class Client {


    private String IP;
    private int port;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private boolean active = false;
    private boolean response = false;
    private ServerListener listener;

    public Client(String IP, int port) {
        this.IP = IP;
        this.port=port;
        listener = new ServerListener("127.0.0.1", 1337);
        listener.execute();
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
        while(!active){
            try {
                wait(30);
            } catch (InterruptedException e) {}
        }
        active = false;
        return response;
    }
    public void handleEvent(Message message){
        // om meddelandet är svar på en förfrågan. via type?
        response = message.getStatus();
        active = true;
        // annars kör på meddelandehantering.
    }

    private class ServerListener extends AsyncTask<Void, Void, Void> {
        private Socket socket;
        private String ip;
        private int port;
        /**
         *  Constructor that sets ip and port and opens a new input and output stream.
         *  Also sends username to server.
         * @param ip ip to use
         * @param port port to use
         */


        public ServerListener(String ip, int port) {
            Log.d("Serverlistener  ", "Konstruktor");
            this.ip = ip;
            this.port = port;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Message message;
            Log.d("Serverlistener  ", "i run metod");
            while (true) {
                try {
                    message = (Message)input.readObject();
                    handleEvent(message);//tillagt av jens för test
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } catch (ClassNotFoundException cnfe) {
                    cnfe.printStackTrace();
                }
                return null;
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        protected void onPreExecute(){
            try {
                socket = new Socket(ip, port);
                Log.d("Serverlistener  ", "efter socket");
            } catch (IOException ioe) {
                ioe.printStackTrace();
                Log.d("Serverlistener  ", "catch efter socket");
            }
            try {
                input = new ObjectInputStream(socket.getInputStream());
                output = new ObjectOutputStream(socket.getOutputStream());
                output.flush();
            } catch (IOException ioe) {
                ioe.printStackTrace();
                Log.d("Serverlistener  ", "Catch efter strömmar");
            }
        }

    }
}




