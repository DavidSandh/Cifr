package se.mah.ag7406.cifr.client.StartActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import message.Message;
import se.mah.ag7406.cifr.R;
import se.mah.ag7406.cifr.client.ControllerPackage.Controller;
import se.mah.ag7406.cifr.client.ConversationListPackage.ConversationList;
import se.mah.ag7406.cifr.client.ControllerPackage.SuperClass;

/**
 * The Login-Screen for the Cifr-app
 * Created by Jens Andreassen on 2017-04-06
 */
public class LoginScreen extends AppCompatActivity {
    private Controller controller;
    private String username;

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
        setContentView(R.layout.activity_cifr_login_screen);
        controller = SuperClass.getController();
        controller.startClient();
    }

    /**
     * Method to be run on Login button pressed
     * Sends request on login and sets name of active user
     */
    public void login(View view){
        Button btn = (Button) findViewById(R.id.loginbutton);
        btn.setEnabled(false);
        EditText username = (EditText) findViewById(R.id.usernamelogin);
        EditText password = (EditText) findViewById(R.id.passwordlogin);
        String name = username.getText().toString().toLowerCase();
        String pass = password.getText().toString();
        this.username = name;
        Log.d("före if sats", "username: "+ name + "passwoed: " + pass);
        controller.checkLogin(name, pass, this);
        //controller.setMyName(name); //För test

    }
    /**
     * Handles response from server and notifies user of failed attempt
     * or starts ConversationList activity on succes
     */
    public void response(Message response) {
        if (response.getType() == 3) {
            this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(LoginScreen.this, "Could not contact the server",
                            Toast.LENGTH_LONG).show();
                }
            });
        } else if (response.getStatus()) {
            controller.setMyName(username); //För test
            Intent intent = new Intent(this, ConversationList.class);
            startActivity(intent);
        } else {
            this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(LoginScreen.this, "Incorrect username or password",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        if(!response.getStatus()) {
            Button btn = (Button) findViewById(R.id.loginbutton);
//            btn.setEnabled(true);
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
}
