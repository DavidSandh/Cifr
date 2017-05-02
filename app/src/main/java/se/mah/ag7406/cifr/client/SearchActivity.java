package se.mah.ag7406.cifr.client;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.view.KeyEvent;
import android.view.View.OnKeyListener;
import android.widget.Toast;

import message.Message;
import se.mah.ag7406.cifr.R;


public class SearchActivity extends AppCompatActivity  {
    String userNameToAdd;
    private Controller controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        controller = SuperClass.getController();
        EditText userNameFound = (EditText) findViewById(R.id.editText);
        userNameFound.setKeyListener(null);
        final EditText userName = (EditText) findViewById(R.id.editText2);
        userName.setOnKeyListener( new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String userNameSearch = userName.getText().toString();
                    ifExists(userNameSearch);
                    System.out.println(userNameSearch);

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

    public void addUserToContacts(final String user){
        new Thread() {
            public void run() {
                controller.sendMessage(5,user);
            }
        }.start();
        this.runOnUiThread(new Runnable() {
            public void run() {

                Snackbar snackbar =Snackbar.make( findViewById(android.R.id.content), "Användare tillagd", Snackbar.LENGTH_LONG );
                snackbar.show();

            }
        });

    }

    public void ifExists (final String userName) {
        new Thread() {
            public void run() {
                controller.sendMessage(4, userName);

            }
        }.start();

        System.out.println("IFEXISTS");

    }



    public void response (String user) {
       final EditText userName = (EditText) findViewById(R.id.editText2);
       final String name = userName.getText().toString();
        System.out.println("RESPONSE");
        if(user.equals(name) ) {
            this.runOnUiThread(new Runnable() {
                public void run() {
                    EditText userNameFound = (EditText) findViewById(R.id.editText);
                    userNameFound.setText(name);
                    findViewById(R.id.button).setVisibility(View.VISIBLE);
                    findViewById(R.id.imageView).setVisibility(View.VISIBLE);


                }
            });
            userNameToAdd = name;
            addUserToContacts(userNameToAdd);
            
        }  else {
                this.runOnUiThread(new Runnable() {
                    public void run() {

                        Snackbar snackbar =Snackbar.make( findViewById(android.R.id.content), "Användare finns inte, skriv in nytt användarnamn.", Snackbar.LENGTH_LONG );
                        userName.setText("");
                        snackbar.show();

                    }
                });
        }

    }


    }

