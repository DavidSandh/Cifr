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
import android.widget.EditText;

import message.Message;
import se.mah.ag7406.cifr.R;
import se.mah.ag7406.cifr.client.ContactListPackage.ContactList;
import se.mah.ag7406.cifr.client.ControllerPackage.Controller;
import se.mah.ag7406.cifr.client.ControllerPackage.SuperClass;
import se.mah.ag7406.cifr.client.ConversationListPackage.ConversationList;
import se.mah.ag7406.cifr.client.StartActivities.LoginScreen;


public class SearchActivity extends AppCompatActivity  {
    private String userNameToAdd;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        controller = SuperClass.getController();
        EditText userNameFound = (EditText) findViewById(R.id.searchUsername);


        userNameFound.setKeyListener(null);
        final EditText userName = (EditText) findViewById(R.id.searchEnterUsername);
        userName.setOnKeyListener( new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String userNameSearch = userName.getText().toString().toLowerCase();
                    ifExists(userNameSearch);
                    EditText userNameFound = (EditText) findViewById(R.id.searchUsername);

                    userNameFound.setFocusableInTouchMode(false);
                    userNameFound.setFocusable(false);
                    return true;
                }
                return false;
                
            }
        });
        Button button = (Button) findViewById(R.id.searchButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(controller.getMyName() != userNameToAdd){
                    addUserToContacts(userNameToAdd);
                }
            }
        });
    }
    public void addUserToContacts(final String userNameToAdd) {
        System.out.println("ANVÄNDARNAMENT I CONTROLLLER::    " + controller.getMyName());
        if (!controller.getMyName().toLowerCase().equals(userNameToAdd.toLowerCase())) {
            System.out.println("ANVÄNDARNAMENT I CONTROLLLER::    " + controller.getMyName() + "INNE I IFSATSEN");
            controller.sendMessage(Message.CONTACTLIST_ADD, controller.getMyName(), userNameToAdd.toLowerCase());
            this.runOnUiThread(new Runnable() {
                public void run() {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "User added", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            });
        }
        EditText userNameFound = (EditText) findViewById(R.id.searchUsername);

        userNameFound.setText("");
        findViewById(R.id.searchButton).setVisibility(View.INVISIBLE);
    }
    public void ifExists (final String userName) {
        controller.sendSearch(userName, this);
        System.out.println("IFEXISTS");
    }
    public void response (final String user) {
       final EditText userNameSearch = (EditText) findViewById(R.id.searchEnterUsername);
       final String name = userNameSearch.getText().toString();
        String newname = name.toLowerCase();
        System.out.println("RESPONSE" + user + " = " + newname);

        if(user!= null){
            user.toLowerCase();

        if(user.equals(newname) ) {
            this.runOnUiThread(new Runnable() {
                public void run() {
                    EditText userNameFound = (EditText) findViewById(R.id.searchUsername);
                    userNameFound.setText(user);
                    findViewById(R.id.searchButton).setVisibility(View.VISIBLE);
                   //sendNotification(userNameToAdd);


                }
            });
            userNameToAdd = name;
        }
        }  else {
                this.runOnUiThread(new Runnable() {
                    public void run() {
                        Snackbar snackbar =Snackbar.make( findViewById(android.R.id.content), "User does not exist. Try another username", Snackbar.LENGTH_LONG );
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
    protected void home(MenuItem item){
        Intent intent = new Intent(this, ConversationList.class);
        startActivity(intent);
        finish();
    }
    /**
     * Used by the navigation menu to open the contacts screen. The contacts
     * screen is where the conversation list is located.
     * @param item Item pressed in the menu.
     */
    protected void contacts(MenuItem item){
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
    public void onBackPressed(){
        Intent intent = new Intent(this, ConversationList.class);
        startActivity(intent);
    }
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

