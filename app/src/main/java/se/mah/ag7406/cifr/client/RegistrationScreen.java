package se.mah.ag7406.cifr.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import se.mah.ag7406.cifr.R;

/**
 *
 */
public class RegistrationScreen extends AppCompatActivity {
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
        setContentView(R.layout.activity_cifr_registration_screen);
        controller = (Controller)getIntent().getSerializableExtra("Controller");
        controller.startClient();
    }

    /**
     *
     * @param view
     */
    public void login(View view){
        EditText username = (EditText) findViewById(R.id.usernameregister);
        EditText pass1 = (EditText) findViewById(R.id.password1register);
        EditText pass2 = (EditText) findViewById(R.id.password2register);
        String name = username.getText().toString();
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
    public void response(boolean response){
        if(response){
            Intent intent = new Intent(this, ConversationList.class);
            intent.putExtra("Controller", this.controller);
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
