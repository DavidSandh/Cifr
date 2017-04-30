package se.mah.ag7406.cifr.client;

import android.graphics.Bitmap;

/**
 * Used to display ongoing conversations in the ConversationList activity.
 * These objects represents ongoing conversations by displaying the latest image
 * sent in a conversation, and the username of the conversation partner.
 * @author Viktor Ekström
 * Created by Viktor on 2017-04-06.
 */

public class GridItem {
    private String username;
    private Bitmap image;

    /**
     * Created with a String and a Bitmap.
     * @param username username of a conversation partner.
     * @param image Latest image sent in a conversation.
     */
    public GridItem(String username, Bitmap image) {
        this.username = username;
        this.image = image;
    }

    /**
     * Returns the username of this GridItem.
     * @return A string with the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the image of this GridItem.
     * @return A bitmap with the image.
     */
    public Bitmap getImage() {
        return image;
    }
}
