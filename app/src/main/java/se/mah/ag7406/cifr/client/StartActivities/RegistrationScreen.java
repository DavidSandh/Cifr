package se.mah.ag7406.cifr.client.StartActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import message.Message;
import se.mah.ag7406.cifr.R;
import se.mah.ag7406.cifr.client.ControllerPackage.Controller;
import se.mah.ag7406.cifr.client.ControllerPackage.SuperClass;
import se.mah.ag7406.cifr.client.ConversationListPackage.ConversationList;

/**
 * The Registration-Screen for the Cifr-app
 * Created by Jens Andreassen on 2017-04-06
 */
public class RegistrationScreen extends AppCompatActivity {
    private Controller controller;
    private String name;
    private ProgressBar spinner;

    /**
     * Runs on Creation of the Activity calls superclass for instance of controller
     * @param savedInstanceState savedInstanceState
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
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
    }

    /**
     * Method to be run on Done button pressed
     * Checks format of username and passwords and sends request if correct
     * @param view view
     */
    public void login(View view){
        Button btn = (Button) findViewById(R.id.registrationButtonRegister);
        btn.setEnabled(false);
        spinner.setVisibility(View.VISIBLE);
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
                Toast.makeText(this, "The passwords do not match or are in the wrong format",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "The username is in the wrong format",
                        Toast.LENGTH_LONG).show();
        }
        btn.setEnabled(true);
    }

    /**
     * Called by a click on the information icon. Displays in a dialog fragment
     * the information regarding username and password format.
     * @param view The view that is clicked. Required parameter for onClick implemenation.
     */
    public void showInformation(View view ) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        DialogFragment dialog = DialogFragmentInfo.newInstance();
        dialog.show(fragmentManager, "dialogfragment_info");
    }

    /**
     * Handles response from server regarding if the requested name is avalable
     * Starts ConversationList Activity if correct otherwise notifies user
     * @param response Message containing response
     */
    public void response(Message response){
        this.runOnUiThread(new Runnable(){
            public void run(){
                spinner.setVisibility(View.GONE);
            }});
        if (response.getType() == 3) {
            this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(RegistrationScreen.this, "Could not contact the server",
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
                    Toast.makeText(RegistrationScreen.this , "Username taken",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
        if(!response.getStatus()) {
            this.runOnUiThread(new Runnable() {
                public void run() {

                    Button btn = (Button) findViewById(R.id.loginbutton);
                    if(btn!=null){
                        btn.setEnabled(true);

                    }
                }
            });
        }
    }
}
