package se.mah.ag7406.cifr.client;

import android.graphics.Bitmap;

/**
 * Created by Viktor on 2017-04-07.
 */

public class ConversationItem {
    private String timeAndDate;
    private Bitmap image;

    public ConversationItem(String timeAndDate, Bitmap image) {
        this.timeAndDate = timeAndDate;
        this.image = image;
    }

    public void setTimeAndDate(String timeAndDate) {
        this.timeAndDate = timeAndDate;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTimeAndDate() {
        return timeAndDate;
    }

    public Bitmap getImage() {
        return image;
    }
}
