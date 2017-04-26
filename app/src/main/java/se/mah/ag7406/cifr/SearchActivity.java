package se.mah.ag7406.cifr;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }







    public boolean ifExists (String userName){
        boolean res = false;

        return res;
    }


    public void search (){
        EditText userName = (EditText) findViewById(R.id.Searchbar);
        String name = userName.getText().toString();

        if(ifExists(name)== true ){

            EditText userNameFound = (EditText) findViewById(R.id.editText);
            userNameFound.setKeyListener(null);
            userNameFound.setText(name);

            findViewById(R.id.button5).setVisibility(View.VISIBLE);

        } else {

            Snackbar snackbar =Snackbar.make( findViewById(android.R.id.content), "Användare finns inte, skriv in nytt användarnamn.", Snackbar.LENGTH_SHORT );
            userName.setText("");
            snackbar.show();
        }

    }



}
