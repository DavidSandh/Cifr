package se.mah.ag7406.cifr.client.ConversationPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import se.mah.ag7406.cifr.R;
import se.mah.ag7406.cifr.client.ContactListPackage.ContactList;
import se.mah.ag7406.cifr.client.ControllerPackage.Controller;
import se.mah.ag7406.cifr.client.ControllerPackage.SuperClass;
import se.mah.ag7406.cifr.client.ConversationListPackage.ConversationList;
import se.mah.ag7406.cifr.client.CreateMessagePackage.CreateMessage;
import se.mah.ag7406.cifr.client.SearchActivityPackage.SearchActivity;
import se.mah.ag7406.cifr.client.StartActivities.LoginScreen;

/**
 * Activity for displaying an ongoing conversation. This will be in the shape of a list
 * where messages are displayed in chronological order. A message is represented by the image
 * of the message and the time it was received. The name of the conversation partner is visible
 * in the top, and in the bottom is a button for creating a new message.
 * @author Viktor Ekström
 */
public class Conversation extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ConversationItem[] conversationItems;
    private String conversationUsername;
    private Controller controller;

    /**
     * Call when activity is first created and will initialise the list with the data
     * collected from the controller, pertaining the particular conversation partner chosen,
     * and uses the custom ConversationAdapter for displaying this data in the list.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        conversationUsername = intent.getStringExtra("username");
        System.out.println("CONV USERNAME" + conversationUsername);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        controller = SuperClass.getController();
        controller.setflag(true, conversationUsername, this);
        conversationItems = controller.getConversation(conversationUsername);
        if(conversationItems == null) {
            Intent createMsgintent = new Intent(this, CreateMessage.class);
            createMsgintent.putExtra("username", conversationUsername);
            startActivity(createMsgintent);
            finish();
        } else {
            TextView usernameTextView = (TextView) findViewById(R.id.conversationUser);
            usernameTextView.setText(conversationUsername);
            recyclerView = (RecyclerView) findViewById(R.id.conversationView);
            layoutManager = new LinearLayoutManager(this);
            layoutManager.scrollToPosition(conversationItems.length - 1);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new ConversationAdapter(this, conversationItems);
            recyclerView.setAdapter(adapter);
        }
    }
    protected void onDestroy(){
        controller.setflag(false, null, null);
        super.onDestroy();
    }

    /**
     * Opens the activity where a message can be created and sent.
     * Called by the Button in the bottom of the screen.
     * @param view Required parameter for onClick implementation. Recognizes a view
     *             that was clicked.
     */
    public void sendMessageActivityButton(View view) {
        Intent intent = new Intent(this, CreateMessage.class);
        intent.putExtra("username", conversationUsername);
        startActivity(intent);
        finish();
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
        finish();
        
    }

}
