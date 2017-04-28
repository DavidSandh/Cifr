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

public class Conversation extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ConversationItem[] conversationItems;
    private String conversationUsername;
    private Controller controller;


    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        conversationUsername = intent.getStringExtra("username");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        controller = (Controller)getIntent().getSerializableExtra("Controller");
        //conversationItems = controller.getConversationData(conversationUsername); //Metod i controller för att få data till conversationen. /Viktor
        setConversationData();
        TextView usernameTextView = (TextView) findViewById(R.id.conversationUser);
        usernameTextView.setText(conversationUsername);
        recyclerView = (RecyclerView) findViewById(R.id.conversationView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ConversationAdapter(this, conversationItems);
        recyclerView.setAdapter(adapter);

    }

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

    public void sendMessageActivityButton(View view) {
        Intent intent = new Intent(this, CreateMessage.class);
        intent.putExtra("username", conversationUsername);
        startActivity(intent);
    }


    protected void contacts(MenuItem item){
        Intent intent = new Intent(this, ContactList.class);
        intent.putExtra("Controller", controller);
        startActivity(intent);
    }
    public void search(MenuItem item){

    }
    public void blocked(MenuItem item){

    }
    public void logout(MenuItem item){

    }

}
