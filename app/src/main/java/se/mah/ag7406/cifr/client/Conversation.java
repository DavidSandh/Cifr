package se.mah.ag7406.cifr.client;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import se.mah.ag7406.cifr.R;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        controller = SuperClass.getController();
        conversationItems = controller.getConversation(conversationUsername); //Metod i controller för att få data till conversationen. /Viktor
        if(conversationItems == null) {
            Intent createMsgintent = new Intent(this, CreateMessage.class);
            createMsgintent.putExtra("username", conversationUsername);
            startActivity(createMsgintent);
        } else {
            startAdapter();
        }
    }

    public void startAdapter() {
        TextView usernameTextView = (TextView) findViewById(R.id.conversationUser);
        usernameTextView.setText(conversationUsername);
        recyclerView = (RecyclerView) findViewById(R.id.conversationView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ConversationAdapter(this, conversationItems);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Test method, to be removed.
     */
    public void setConversationData() { //Ska kallas i controllern? /Viktor
        //Test-data för tillfället.
        conversationItems = new ConversationItem[5];
        ConversationItem item1 = new ConversationItem("Klockan är tio", BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round));
        ConversationItem item2 = new ConversationItem("Klockan är elva", BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round));
        ConversationItem item3 = new ConversationItem("Klockan är tolv", BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round));
        ConversationItem item4 = new ConversationItem("Klockan är tretton", BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round));
        ConversationItem item5 = new ConversationItem("Klockan är fjorton", BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round));
        conversationItems[0] = item1;
        conversationItems[1] = item2;
        conversationItems[2] = item3;
        conversationItems[3] = item4;
        conversationItems[4] = item5;
    }

    /**
     * Opens the activity where a message can be created and sent.
     * Called by the Button in the bottom of the screen.
     * @param view Required parameter for onClick implementation. Recognizes a view
     *             that was clicked.
     */
    public void sendMessageActivityButton(View view) {
        Intent intent = new Intent(this, CreateMessage.class);
        intent.putExtra("conversationUsername", conversationUsername);
        startActivity(intent);
    }

    protected void home(MenuItem item){
        Intent intent = new Intent(this, ConversationList.class);
        startActivity(intent);
    }

    protected void contacts(MenuItem item){
        Intent intent = new Intent(this, ContactList.class);
        startActivity(intent);
    }

    public void search(MenuItem item){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void logout(MenuItem item){
        controller.logout();
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
    }

}
