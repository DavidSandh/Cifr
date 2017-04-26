package se.mah.ag7406.cifr.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import se.mah.ag7406.cifr.R;

/**
 * The Home-Screen for the Cifr-app
 */
public class HomeScreen extends AppCompatActivity {

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
        setContentView(R.layout.cifr_home_screen);
        this.controller = new Controller();
    }

    /**
     *
     *
     */

    public void login(View view){
        Intent intent = new Intent(this, LoginScreen.class);
        intent.putExtra("Controller", this.controller);
        startActivity(intent);
    }

    /**
     *
     *
     */
    protected void register(View view){
        Intent intent = new Intent(this, RegistrationScreen.class);
        intent.putExtra("Controller", controller);
        startActivity(intent);
    }
}
