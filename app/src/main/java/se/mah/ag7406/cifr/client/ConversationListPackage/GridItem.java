package se.mah.ag7406.cifr.client.ConversationListPackage;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Used to display ongoing conversations in the ConversationList activity.
 * These objects represents ongoing conversations by displaying the latest image
 * sent in a conversation, and the username of the conversation partner.
 * @author Viktor Ekström
 * Created by Viktor on 2017-04-06.
 */

public class GridItem implements Comparable<GridItem> {
    private String username;
    private Bitmap image;
    private Date dateAndTime;

    /**
     * Created with a String and a Bitmap.
     * @param username username of a conversation partner.
     * @param image Latest image sent in a conversation.
     */
    public GridItem(String username, Bitmap image, Date date) {
        this.username = username;
        this.image = image;
        this.dateAndTime = date;
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

    /**
     * Returns the date object, which is only used for sorting.
     * @return
     */
    public Date getDateAndTime() {
        return dateAndTime;
    }

    /**
     * Compares the date of the GridItem. Used for sorting the data in the conversation list.
     * @param gridItem The item to be compared with.
     * @return An int indicating if the item is before or after the compared to item.
     */
    public int compareTo(GridItem gridItem) {
        return getDateAndTime().compareTo(gridItem.getDateAndTime());
    }
}
