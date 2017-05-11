package se.mah.ag7406.cifr.client.StartActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import se.mah.ag7406.cifr.R;

/**
 * The Home-Screen for the Cifr-app
 * Created by Jens Andreassen on 2017-04-06
 */
public class HomeScreen extends AppCompatActivity {

    /**
     *Runs on Creation of the Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.cifr_home_screen);
    }

    /**
     *  Method to be run on button login pressed, starts LoginScreen Activity
     * @param view
     */
    public void login(View view){
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
    }

    /**
     * Method to be run on button registration pressed, starts RegistrationScreen Activity
     */
    protected void register(View view){
        Intent intent = new Intent(this, RegistrationScreen.class);
        startActivity(intent);
    }
    public void onBackPressed(){

    }
}
