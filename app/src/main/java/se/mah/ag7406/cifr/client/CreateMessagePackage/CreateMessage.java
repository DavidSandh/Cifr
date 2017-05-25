package se.mah.ag7406.cifr.client.CreateMessagePackage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;

import se.mah.ag7406.cifr.R;
import se.mah.ag7406.cifr.client.ControllerPackage.Controller;
import se.mah.ag7406.cifr.client.ControllerPackage.SuperClass;
import se.mah.ag7406.cifr.client.ConversationPackage.Conversation;

/**
 * Activity for creating a new message. The user is able to choose an image and
 * input the text that is to be hidden in the image. The receiver of this message is
 * pre-determined depending on the previous conversation activity.
 * @author Viktor Ekström
 */
public class CreateMessage extends AppCompatActivity {
    private String receiver;
    private Bitmap selectedImage;
    private int PICK_IMAGE_REQUEST = 1;
    private Controller controller;
    private ProgressBar spinner;

    /**
     * Called when activity is first created. Extracts the username of the
     * conversation partner from the intent. The message will be sent to this user.
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);
        receiver = intent.getStringExtra("username");
        controller = SuperClass.getController();
        EditText editText = (EditText) findViewById(R.id.createMessageText);
        editText.setHint("Write message to " + receiver + " here...");
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
    }

    /**
     * Collects the data for the message object creation taking place in the
     * controller. Called when the user clicks the send button. The user is after
     * this sent back to the conversation.
     * @param view Required parameter for onClick implementation. The view
     *             which is clicked.
     */
    public void sendMessage(View view) {
        Button btn = (Button) findViewById(R.id.btnSend);
        btn.setEnabled(false);
        EditText message = (EditText) findViewById(R.id.createMessageText);
        String messageText = message.getText().toString();
        if (selectedImage == null){
            Toast.makeText(CreateMessage.this, "No image added",
                    Toast.LENGTH_LONG).show();
            btn.setEnabled(true);
        }
        if(selectedImage != null) {
            this.runOnUiThread(new Runnable(){
                public void run(){
                    spinner.setVisibility(View.VISIBLE);
                }});
            controller.sendMessage(receiver, messageText, resizeImage(selectedImage));
            Intent intent = new Intent(this, Conversation.class);
            intent.putExtra("username", receiver);
            this.runOnUiThread(new Runnable(){
                public void run(){
                    spinner.setVisibility(View.GONE);
                }});
            startActivity(intent);
            finish();
        }
    }

    /**
     * Resize the image to be sent. Is based on this device's screen size. Will keep
     * the current ratio.
     * @param image Image to be resized.
     * @return Resized image.
     */
    private Bitmap resizeImage(Bitmap image) {
        int finalWidth = image.getWidth();
        int finalHeight = image.getHeight();
        int maxWidth = SuperClass.getContext().getResources().getDisplayMetrics().widthPixels - 10;
        int maxHeight = SuperClass.getContext().getResources().getDisplayMetrics().heightPixels - 10;

        if(image.getWidth() > maxWidth) {
            finalWidth = maxWidth;
            finalHeight = (finalWidth * image.getHeight()) / image.getWidth();
        }

        if(image.getHeight() > maxHeight) {
            finalHeight = maxHeight;
            finalWidth = (finalHeight * image.getWidth()) / image.getHeight();
        }
        image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
        return image;
    }

    /**
     * Enables choosing of a message from the image gallery of the device.
     * Called by clicking the ImageView in the activity.
     * @param view Required parameter for onClick implementation. The ImageView
     *             that is clicked.
     */
    public void chooseImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Choose image"), PICK_IMAGE_REQUEST);
    }

    /**
     * Called when the image gallery is closed and the result is provided.
     * @param requestCode Identifies where this results originate.
     * @param resultCode Result code returned by child activity through setResult().
     * @param data Intent used for returning information.
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

    /**
     * Finish when back is pressed.
     */
    @Override
    public void onBackPressed() {
        finish();
    }
}
