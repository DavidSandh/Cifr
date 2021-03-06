package se.mah.ag7406.cifr.client.ConversationPackage;

import android.graphics.Bitmap;

/**
 * Used to display an ongoing conversation in the Conversation activity.
 * The image represent a sent message and the time and date when this message
 * was received.
 * @author Viktor Ekström
 * Created by Viktor on 2017-04-07.
 */

public class ConversationItem {
    private String timeAndDate;
    private Bitmap image;
    private String sender;

    /**
     * Creates the object with a String and a Bitmap.
     * @param timeAndDate The date and time when this message was received.
     * @param image Image of the message.
     * @param username Sender of the message.
     */
    public ConversationItem(String timeAndDate, Bitmap image, String username) {
        this.timeAndDate = timeAndDate;
        this.image = image;
        this.sender=username;
    }

    /**
     * Sets the date when a message was received.
     * @param timeAndDate String representing time and date.
     */
    public void setTimeAndDate(String timeAndDate) {
        this.timeAndDate = timeAndDate;
    }

    /**
     * Sets image attribute to be displayed.
     * @param image Bitmap to display.
     */
    public void setImage(Bitmap image) {
        this.image = image;
    }

    /**
     * Returns the String attribute representing the time and date of some message's
     * receiving.
     * @return String showing the time and date.
     */
    public String getTimeAndDate() {
        return timeAndDate;
    }

    /**
     * Returns the Bitmap attribute representing the image content of some message.
     * @return Bitmap
     */
    public Bitmap getImage() {
        return image;
    }

    /**
     * Returns the sender of this message, so it can be displayed in the conversaiton.
     * @return String with the username of sender.
     */
    public String getSender(){
        return sender;
    }
}
