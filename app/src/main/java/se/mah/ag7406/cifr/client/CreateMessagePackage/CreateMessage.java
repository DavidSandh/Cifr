package se.mah.ag7406.cifr.client.CreateMessagePackage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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

    /**
     * Called when activity is first created. Extracts the username of the
     * conversation partner from the intent. The message will be sent to this user.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);
        receiver = intent.getStringExtra("username");
        System.out.println("FELX: I CreateMessage: reciever: 2" + receiver);
        System.out.println("CreateMessage: Receiver är " + receiver);
        controller = SuperClass.getController();
        EditText editText = (EditText) findViewById(R.id.createMessageText);
        editText.setHint("Write message to " + receiver + " here...");
    }

    /**
     * Collects the data for the message object creation taking place in the
     * controller. Called when the user clicks the send button. The user is after
     * this sent back to the conversation.
     * @param view Required parameter for onClick implementation. The view
     *             which is clicked.
     */
    public void sendMessage(View view) {
        EditText messaget = (EditText) findViewById(R.id.createMessageText);
        String messageText = messaget.getText().toString();
        System.out.println("CreateMessage: Texten i messageText: " + messageText);
//        controller.sendMessage(receiver, messageText, (Object)convert(selectedImage));
        controller.sendMessage(receiver, messageText, selectedImage);

        Intent intent = new Intent(this, Conversation.class);
        System.out.println("FELX: I CreateMessage: reciever: 1 " + receiver);

        startActivity(intent);
        finish();
    }

//    public byte[] convert(Bitmap bit){//för test
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bit.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        byte[] byteArray = stream.toByteArray();
//        return byteArray;
//    }

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
 }
