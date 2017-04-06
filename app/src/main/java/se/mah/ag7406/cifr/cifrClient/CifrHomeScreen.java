package se.mah.ag7406.cifr.cifrClient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import se.mah.ag7406.cifr.R;

/**
 * The Home-Screen for the Cifr-app
 */
public class CifrHomeScreen extends AppCompatActivity {
    private boolean loggedIn = true;
    private Controller cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.cifr_home_screen);
        cont = new Controller();
    }

    public void logIn(View view){
        Intent intent = new Intent(this, CifrLoginScreen.class);
        intent.putExtra("Controller", cont);
        startActivity(intent);
    }


    public void register(View view){
        Intent intent = new Intent(this, CifrRegistrationScreen.class);
        intent.putExtra("Controller", cont);
        startActivity(intent);

    }
}
