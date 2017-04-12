package se.mah.ag7406.cifr.client;

/**
 * Created by Viktor on 2017-04-07.
 */

public class ConversationItem {
    private String timeAndDate;
    private int image;

    public ConversationItem(String timeAndDate, int image) {
        this.timeAndDate = timeAndDate;
        this.image = image;
    }

    public void setTimeAndDate(String timeAndDate) {
        this.timeAndDate = timeAndDate;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTimeAndDate() {
        return timeAndDate;
    }

    public int getImage() {
        return image;
    }
}
