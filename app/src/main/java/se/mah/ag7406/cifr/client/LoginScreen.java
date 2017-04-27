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
        controller = (Controller)getIntent().getSerializableExtra("Controller");
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

    public void response(boolean response){
        System.out.println("i response i login");
        if(response){
            Intent intent = new Intent(this, ConversationList.class);
            intent.putExtra("Controller", controller);
            Log.d("efter if sats"
                    , "vart true = kontakt med controller från login");
            startActivity(intent);

        } else {
            this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(LoginScreen.this , "Felaktigt lösenord eller användarnamn!",
                            Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}
