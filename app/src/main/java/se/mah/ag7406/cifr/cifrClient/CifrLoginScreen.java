package se.mah.ag7406.cifr.cifrClient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import se.mah.ag7406.cifr.ConversationList;
import se.mah.ag7406.cifr.R;

public class CifrLoginScreen extends AppCompatActivity {
    //private Controller cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cifr_login_screen);
        //cont = new Controller();
    }

    public void logIn(View view){
        Intent intent = new Intent(this, ConversationList.class);
        //cont.testprint();
        startActivity(intent);

    }
}
