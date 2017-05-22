package se.mah.ag7406.cifr.client.SearchActivityPackage;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
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

import static android.R.attr.button;
import static android.R.attr.fastScrollPreviewBackgroundLeft;


public class SearchActivity extends AppCompatActivity  {
    private String userNameToAdd;
    private Controller controller;
    private Button buttonAdd;
    private Button buttonSearch;
    private EditText userNameSearch;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        controller = SuperClass.getController();
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setEnabled(false);
       // userNameFound.setKeyListener(null);
        userNameSearch = (EditText) findViewById(R.id.editText);
        userNameSearch.setOnKeyListener( new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String userNameSearched = userNameSearch.getText().toString().toLowerCase();
                    //EditText userNameFound = (EditText) findViewById(R.id.searchUsername);

                    //userNameFound.setFocusableInTouchMode(false);
                    //userNameFound.setFocusable(false);
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
                if(!controller.getMyName().equals(userNameToAdd)){
                    addUserToContacts(userNameToAdd);
                }
            }
        });
     buttonSearch = (Button) findViewById(R.id.searchButton);
        buttonSearch.setFocusableInTouchMode(false);
        buttonSearch.setFocusable(false);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!controller.getMyName().equals(userNameToAdd)){
                    String userNameSearched = userNameSearch.getText().toString().toLowerCase();
                    ifExists(userNameSearched);
                }
            }
        });
    }

    public void addUserToContacts(final String userNameToAdd) {
        if (!controller.getMyName().toLowerCase().equals(userNameToAdd.toLowerCase())) {
            controller.sendMessage(Message.CONTACTLIST_ADD, controller.getMyName(), userNameToAdd.toLowerCase());

        }
        if(userNameToAdd.toLowerCase().equals(controller.getMyName().toLowerCase())) {
            this.runOnUiThread(new Runnable() {
                public void run() {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "You cant't add yourself!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    }
                });
            }
                else {
                this.runOnUiThread(new Runnable() {
                    public void run() {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "User added", Snackbar.LENGTH_LONG);
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

    public void ifExists (final String userName) {
        controller.sendSearch(userName, this);
    }

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
                    // EditText userNameFound = (EditText) findViewById(R.id.searchUsername);
                    // userNameFound.setText(user);
                    //findViewById(R.id.searchButton).setVisibility(View.VISIBLE);
                    //sendNotification(userNameToAdd);
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
     * Notifies the user when added to a contact list.
     * @param user user
     */
    public void sendNotification(String user ){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
              .setSmallIcon(R.drawable.ic_menu_share)
             .setContentTitle("A user has added you")
              .setContentText( user +" wants to add you to their contact list");

          Intent intent = new Intent(this, ContactList.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        int mNotificationId = 000;
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
        }

        public String getUserNameToAdd(){
            return userNameToAdd;
        }
}

