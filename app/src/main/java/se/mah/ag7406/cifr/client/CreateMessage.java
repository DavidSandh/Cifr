package se.mah.ag7406.cifr.client;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import se.mah.ag7406.cifr.R;

public class CreateMessage extends AppCompatActivity {
    private String receiver;
    private Bitmap selectedImage;
    private int PICK_IMAGE_REQUEST = 1;
    private Controller controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);
        receiver = intent.getStringExtra("conversationUsername");
        controller = SuperClass.getController();
        controller = (Controller) intent.getSerializableExtra("Controller");
    }

    public void sendMessage(View view) {
        //TextView messageTextView = (TextView) findViewById(R.id.createMessageText);
        EditText messaget = (EditText) findViewById(R.id.createMessageText);
        String messageText = messaget.getText().toString();
        //String messageText = (String) messageTextView.getText();
        SuperClass.getController().sendMessage(receiver, messageText, (Object)convert(selectedImage));
        //controller.sendMessage(receiver, messageText, selectedImage);
    }
    public byte[] convert(Bitmap bit){//för test
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void chooseImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Choose image"), PICK_IMAGE_REQUEST);
    }

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
