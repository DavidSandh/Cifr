package se.mah.ag7406.cifr.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import message.Message;
import se.mah.ag7406.cifr.R;

/**
 * The Registration-Screen for the Cifr-app
 * Created by Jens Andreassen on 2017-04-06
 */
public class RegistrationScreen extends AppCompatActivity {
    private Controller controller;
    private String name;

    /**
     * Runs on Creation of the Activity calls superclass for instance of controller
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cifr_registration_screen);
        controller = SuperClass.getController();
        controller.startClient();
    }

    /**
     * Method to be run on Done button pressed
     * Checks format of username and passwords and sends request if correct
     * @param view
     */
    public void login(View view){
        EditText username = (EditText) findViewById(R.id.usernameregister);
        EditText pass1 = (EditText) findViewById(R.id.password1register);
        EditText pass2 = (EditText) findViewById(R.id.password2register);
        name = username.getText().toString();
        String password1 = pass1.getText().toString();
        String password2 = pass2.getText().toString();
        if(controller.checkUsernameFormat(name)){
            if(controller.checkpassword(password1, password2)){
                controller.checkUsername(name , password1, this);
            } else {
                Toast.makeText(this, "Lösenordet är inte detsamma eller i fel format",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Användarnamnet är på fel format",
                        Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Handles response from server regarding if the requested name is avalable
     * Starts ConversationList Activity if correct otherwise notifies user
     * @param response Message containing response
     */
    public void response(Message response){
        if (response.getType() == 3) {
            this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(RegistrationScreen.this, "Kunde inte koppla upp till servern!",
                            Toast.LENGTH_LONG).show();
                }
            });
        } else if(response.getStatus()){
            controller.setMyName(name);
            Intent intent = new Intent(this, ConversationList.class);
            startActivity(intent);
        } else {
            this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(RegistrationScreen.this , "Användarnamnet upptaget",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
