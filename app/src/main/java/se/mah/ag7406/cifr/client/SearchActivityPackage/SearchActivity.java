package se.mah.ag7406.cifr.client.SearchActivityPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import message.Message;
import se.mah.ag7406.cifr.R;
import se.mah.ag7406.cifr.client.ContactListPackage.ContactList;
import se.mah.ag7406.cifr.client.ControllerPackage.Controller;
import se.mah.ag7406.cifr.client.ControllerPackage.SuperClass;
import se.mah.ag7406.cifr.client.ConversationListPackage.ConversationList;
import se.mah.ag7406.cifr.client.StartActivities.LoginScreen;

/**
 * Class used for searching after other users, registered on the server.
 * Sends a message to the server with a string of the user searched for.
 * @Author Max Henriksson
 */

public class SearchActivity extends AppCompatActivity {
    private String userNameToAdd;
    private Controller controller;
    private Button buttonAdd;
    private Button buttonSearch;
    private EditText userNameSearch;
    private CheckBox checkBox;


    /**
     * Method called when the activity is first created.
     * Sets listeners and sets buttons inactive.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        controller = SuperClass.getController();
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setEnabled(false);
        userNameSearch = (EditText) findViewById(R.id.editText);
        userNameSearch.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String userNameSearched = userNameSearch.getText().toString().toLowerCase();
                    return true;
                }
                return false;
            }
        });
        buttonAdd = (Button) findViewById(R.id.addButton);
        buttonAdd.setFocusableInTouchMode(false);
        buttonAdd.setFocusable(false);
        buttonAdd.setEnabled(false);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!controller.getMyName().equals(userNameToAdd)) {
                    addUserToContacts(userNameToAdd);
                }
            }
        });
        buttonSearch = (Button) findViewById(R.id.searchButton);
        buttonSearch.setFocusableInTouchMode(false);
        buttonSearch.setFocusable(false);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!controller.getMyName().equals(userNameToAdd)) {
                    String userNameSearched = userNameSearch.getText().toString().toLowerCase();
                    ifExists(userNameSearched);
                }
            }
        });
    }

    /**
     * Sends a message to the server if the String is of the right format.
     * A message that later adds the user to the Contactlist. Otherwise the user
     * will be prompted with snackbar.
     * @param userNameToAdd username searched for
     */

    public void addUserToContacts(final String userNameToAdd) {

        if (userNameToAdd.toLowerCase().equals(checkUserList().toLowerCase())){
            this.runOnUiThread(new Runnable() {
                public void run() {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "You already have that person added!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            });
        }
         else if (!controller.getMyName().toLowerCase().equals(userNameToAdd.toLowerCase())) {
            controller.sendMessage(Message.CONTACTLIST_ADD, controller.getMyName(), userNameToAdd.toLowerCase());
            this.runOnUiThread(new Runnable() {
                public void run() {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "User added", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            });
        }
         else if (userNameToAdd.toLowerCase().equals(controller.getMyName().toLowerCase())) {
            this.runOnUiThread(new Runnable() {
                public void run() {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "You cant't add yourself!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            });
        }
        userNameSearch = (EditText) findViewById(R.id.editText);
        userNameSearch.setText("");
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setChecked(false);
        buttonAdd = (Button) findViewById(R.id.addButton);
        buttonAdd.setEnabled(false);
    }

    /**
     * Sends a message to the with the specified string to check
     * if the user exists.
     * @param userName name searched for
     */

    public void ifExists (final String userName) {
        controller.sendSearch(userName, this);
    }


    /**
     * Method called when a message is received from the server.
     * Compares the string received from the server with string the
     * user searched for.
     *
     * @param user name returned by server
     */
    public void response (final String user) {
        final Button buttonAdd = (Button) findViewById(R.id.addButton);
        final EditText userNameSearch = (EditText) findViewById(R.id.editText);
        final String name = userNameSearch.getText().toString();
        String newName = name.toLowerCase();
        if (user != null){
            user.toLowerCase();
        if (user.equals(newName)) {
            this.runOnUiThread(new Runnable() {
                public void run() {
                    buttonAdd.setEnabled(true);
                    CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
                    checkBox.setEnabled(true);
                    checkBox.setChecked(true);
                }
            });
            userNameToAdd = name;
        }
        } else {
            checkBox.setChecked(false);
            this.runOnUiThread(new Runnable() {
                public void run() {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "User does not exist. Try another username", Snackbar.LENGTH_LONG);
                    userNameSearch.setText("");
                    snackbar.show();
                }
            });
        }
    }

    /**
     * Used by the navigation menu to open the "home" screen. The "home" screen
     * is where the conversations are listed.
     * @param item Item pressed in the menu.
     */
    public void home(MenuItem item){
        Intent intent = new Intent(this, ConversationList.class);
        startActivity(intent);
        finish();
    }

    /**
     * Used by the navigation menu to open the contacts screen. The contacts
     * screen is where the conversation list is located.
     * @param item Item pressed in the menu.
     */
    public void contacts(MenuItem item){
        Intent intent = new Intent(this, ContactList.class);
        startActivity(intent);
        finish();
    }

    /**
     * Used by the navigation menu to open the search screen. The search screen
     * is where a user can search for other users and add these to the contactlist.
     * @param item Item pressed in the menu.
     */
    public void search(MenuItem item){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Used by the navigation menu to log out a user.
     * @param item Item pressed in the menu.
     */
    public void logout(MenuItem item){
        controller.logout();
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
        finish();
    }

    /**
     * Overrides the backbutton
     */
    public void onBackPressed(){
        Intent intent = new Intent(this, ConversationList.class);
        startActivity(intent);
    }

    /**
     * Checks if the user has the specified user searched
     * for already in her contactlist.
     * @return String user
     */
        public String checkUserList(){
            String[] list = controller.getContactList();
            String res = new String();
            if(list != null) {
            for (int i = 0 ; i< list.length; i++ ) {
                if (userNameToAdd.toLowerCase().equals(list[i].toLowerCase())) {
                    res = list[i];
                }
            }
                } else {
                    res = "ingen i listan";
                }
                return res;
        }
}