package se.mah.ag7406.cifr.cifrClient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import se.mah.ag7406.cifr.ConversationList;
import se.mah.ag7406.cifr.R;

public class CifrLoginScreen extends AppCompatActivity {
    private Controller cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cifr_login_screen);
        cont = (Controller)getIntent().getSerializableExtra("Controller");
    }

    public void logIn(View view){
        EditText username = (EditText) findViewById(R.id.usernamelogin);
        EditText password = (EditText) findViewById(R.id.passwordlogin);
        String name = username.getText().toString();
        String pass = password.getText().toString();
        Log.d("före if sats", "username: "+ username + "passwoed: " + pass);
        if (cont.checkLogin(name, pass)){
            Intent intent = new Intent(this, ConversationList.class);
            startActivity(intent);
            Log.d("efter if sats", "vart true = kontakt med controller från login");
        } else {

        }


    }
}
