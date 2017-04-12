package se.mah.ag7406.cifr.client;

/**
 * Created by Viktor on 2017-04-06.
 */

public class GridItem {
    private String username;
    private int image;

    public GridItem(String username, int image) {
        this.username = username;
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public int getImage() {
        return image;
    }
}
