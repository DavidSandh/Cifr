package se.mah.ag7406.cifr.client;

import android.graphics.Bitmap;

/**
 * Created by Viktor on 2017-04-06.
 */

public class GridItem {
    private String username;
    private Bitmap image;

    public GridItem(String username, Bitmap image) {
        this.username = username;
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public Bitmap getImage() {
        return image;
    }
}
