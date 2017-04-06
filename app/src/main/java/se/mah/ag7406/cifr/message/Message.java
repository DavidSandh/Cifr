package se.mah.ag7406.cifr.message;

import android.media.Image;
import java.util.Date;

/**
 * Message class which contains the message being sent from and to server/client.
 * @author Lucas Knutsäter
 *
 */
public class Message implements Serializable {
    static final int LOGIN = 0, REGISTER = 1, MESSAGE = 2, STATUS = 3;
    private Image image;
    private String sender;
    private String recipient;
    private String username;
    private String password;
    private boolean status;
    private Date date;
    private int type;

    /**
     *  Constructor add all information and put date when sent.
     * @param sender Sender of message
     * @param recipient Recipient of message
     * @param image image to send
     */
    public Message(int type, String sender, String recipient, Image image) {
        this.sender = sender;
        this.type = 1;
        this.recipient = recipient;
        this.image = image;
        this.date = new Date();
    }
    public Message(int type, String username, String password) {
        this.username = username;
        this.password = password;
        this.type = type;
    }
    public Message(int type, boolean status) {
        this.status = status;
        this.type = type;
    }
    /**
     * returns image
     * @return image in message
     */
    public Image getImage() {
        return image;
    }
    /**
     * returns sender
     * @return sender of message
     */
    public String getSender() {
        return sender;
    }
    /**
     * returns recipient
     * @return recipient of message
     */
    public String getRecipient() {
        return recipient;
    }
    /**
     * returns Date object
     * @return Date object
     */
    public Date getDate() {
        //ska nog göras om till att retunera tid istället och inte objektet.
        return date;
    }
    /**
     * returns Type.
     * @return type to return
     */
    public int getType() {
        return type;
    }
    /**
     * returns username
     * @return username
     */
    public String getUsername() {
        return username;
    }
    /**
     * returns password
     * @return password
     */
    public String getPassword() {
        return password;
    }
    /**
     * returns status
     * @return status
     */
    public boolean getStatus() {
        return status;
    }
}