package se.mah.ag7406.cifr.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import message.Message;
import se.mah.ag7406.cifr.R;

public class LoginScreen extends AppCompatActivity {
    private Controller controller;

    /**
     *
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
     *
     *
     */
    public void login(View view){
        EditText username = (EditText) findViewById(R.id.usernamelogin);
        EditText password = (EditText) findViewById(R.id.passwordlogin);
        String name = username.getText().toString();
        String pass = password.getText().toString();
        Log.d("före if sats", "username: "+ name + "passwoed: " + pass);
        controller.checkLogin(name, pass, this);
        controller.setMyName(name); //För test

    }

    public void response(Message response) {
        if (response.getType() == 3) {
            this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(LoginScreen.this, "Kunde inte koppla upp till servern!",
                            Toast.LENGTH_LONG).show();
                }
            });
        } else if (response.getStatus()) {
            Intent intent = new Intent(this, ConversationList.class);
            startActivity(intent);
        } else {
            this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(LoginScreen.this, "Felaktigt lösenord eller användarnamn!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
