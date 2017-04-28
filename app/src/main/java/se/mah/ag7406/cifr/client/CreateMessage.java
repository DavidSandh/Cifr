package se.mah.ag7406.cifr.client;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import se.mah.ag7406.cifr.R;

/**
 * Activitiy providing functionality for the user for creating and sending a message. This includes choosing an
 * image and writing a text that the image should contain. The recipient of the message is
 * pre-determined and cannot be chosen.
 * @author Viktor Ekström
 */
public class CreateMessage extends AppCompatActivity {
    private String receiver;
    private Bitmap selectedImage;
    private int PICK_IMAGE_REQUEST = 1;
    private Controller controller;

    /**
     * Standard onCreate method, what is to be done when the activity is first created.
     * It also pulls the recipient username from the intent to be used for the message.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);
        receiver = intent.getStringExtra("conversationUsername");
        controller = (Controller) intent.getSerializableExtra("Controller");
    }

    /**
     * Connected to the Send button of the layout. Gathers the information used in the
     * message creation, which is done in the Controller.
     * @param view Required parameter for onClick implementation. Recognizes a view
     *             that was clicked.
     */
    public void sendMessage(View view) {
        TextView messageTextView = (TextView) findViewById(R.id.createMessageText);
        String messageText = (String) messageTextView.getText();
        if(selectedImage!=null) {
            controller.sendMessage(receiver, messageText, selectedImage);
        }
    }

    /**
     * Connected to the ImageView of the activity which acts as a button for choosing
     * the image for the message. This will open the image gallery of the device.
     * @param view Required parameter for onClick implementation. Recognizes a view
     *             that was clicked.
     */
    public void chooseImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Choose image"), PICK_IMAGE_REQUEST);
    }

    /**
     * Handles the inputs of the user interaction in the image gallery. If an image is selected
     * than this image will used in a sent message.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ImageView imageView = (ImageView) findViewById(R.id.createMessageImage);
                imageView.setImageBitmap(selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
