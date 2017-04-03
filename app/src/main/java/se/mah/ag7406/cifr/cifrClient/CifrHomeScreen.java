package se.mah.ag7406.cifr.cifrClient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import se.mah.ag7406.cifr.R;

/**
 * The Home-Screen for the Cifr-app
 */
public class CifrHomeScreen extends AppCompatActivity {
    private boolean loggedIn = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(loggedIn == true) {
            setContentView(R.layout.activity_main);
            System.out.println("main screen ");
        }else{
            System.out.println("login screen ");
            setContentView(R.layout.activity_cifr_login_screen);
        }


    }
    /*
    Byter skärm
     */
    public void testView(View view ){
    }
}
