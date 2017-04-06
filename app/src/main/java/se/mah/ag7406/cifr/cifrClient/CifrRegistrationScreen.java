package se.mah.ag7406.cifr.cifrClient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import se.mah.ag7406.cifr.ConversationList;
import se.mah.ag7406.cifr.R;

public class CifrRegistrationScreen extends AppCompatActivity {
    private Controller cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cifr_registration_screen);
        cont = (Controller)getIntent().getSerializableExtra("Controller");
    }

    public void logIn(View view){
        EditText username = (EditText) findViewById(R.id.usernameregister);
        EditText pass1 = (EditText) findViewById(R.id.password1register);
        EditText pass2 = (EditText) findViewById(R.id.password2register);
        String name = username.getText().toString();
        String password1 = pass1.getText().toString();
        String password2 = pass2.getText().toString();
        Log.d("test registration", "Un: " + name + " p1: " + password1 + " p2: " + password2); // för test

        if(cont.checkUsername(name)){
            Log.d("test registration", "kollat username och godkänt"); // för test
            if(cont.checkpassword(password1, password2)){
                Intent intent = new Intent(this, ConversationList.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Lösenordet måste vara blabla",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d("test registration", "kollat username inte godkänt"); // för test
            if (cont.checkUsernameFormat(name)){
                Toast.makeText(this, "Användarnamnet är upptaget",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Användarnamnet måste innehålla blalbla",
                        Toast.LENGTH_LONG).show();
            }
        }

    }
}
