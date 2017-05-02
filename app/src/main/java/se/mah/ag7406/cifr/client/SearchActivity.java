package se.mah.ag7406.cifr.client;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.view.KeyEvent;
import android.view.View.OnKeyListener;

import se.mah.ag7406.cifr.R;


public class SearchActivity extends AppCompatActivity  {

    private Controller controller;

    String userNameToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        EditText userNameFound = (EditText) findViewById(R.id.editText);
        userNameFound.setKeyListener(null);
        EditText userName1 = (EditText) findViewById(R.id.editText2);
        userName1.setOnKeyListener( new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    search();
                    return true;
                }
                return false;
            }
        });
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addUserToContacts(userNameToAdd);
            }
        });

    }


    public void addUserToContacts(String user){

        //kod som adderar en sträng till "kontaktlistan" eller möjligen ett "user"-objekt till
        //"kontaktlistan" eller bara skickar användaren




        Snackbar snackbar0 =Snackbar.make( findViewById(android.R.id.content), "Användare tillagd", Snackbar.LENGTH_LONG );
        snackbar0.show();

    }






    public boolean ifExists (String userName){


        //send messgae till server med 1


        //kod som kommunicerar med servern för att få tillbaka en boolean som säger
        // om användaren finns eller inte...

        boolean res = false;

        return res;
    }


    public void search (){
        EditText userName = (EditText) findViewById(R.id.editText2);

        String name = userName.getText().toString();

        if(name.equals("Max") ){

            EditText userNameFound = (EditText) findViewById(R.id.editText);
            userNameFound.setText(name);

            findViewById(R.id.button).setVisibility(View.VISIBLE);
            findViewById(R.id.imageView).setVisibility(View.VISIBLE);
            userNameToAdd= name;

            Snackbar snackbar0 =Snackbar.make( findViewById(android.R.id.content), "Användare existerar", Snackbar.LENGTH_LONG );
            snackbar0.show();



        } else {

            Snackbar snackbar1 =Snackbar.make( findViewById(android.R.id.content), "Användare finns inte, skriv in nytt användarnamn.", Snackbar.LENGTH_LONG );
            userName.setText("");
            snackbar1.show();
        }

    }

    protected void contacts(MenuItem item){
        Intent intent = new Intent(this, ContactList.class);
        intent.putExtra("Controller", controller);
        startActivity(intent);
    }
    protected void search(MenuItem item){
        Intent intent = new Intent(this, ContactList.class);
        intent.putExtra("Controller", controller);
        startActivity(intent);

    }
    public void blocked(MenuItem item){

    }
    public void logout(MenuItem item){

    }




}
