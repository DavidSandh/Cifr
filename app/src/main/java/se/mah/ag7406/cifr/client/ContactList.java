package se.mah.ag7406.cifr.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import se.mah.ag7406.cifr.R;
import se.mah.ag7406.cifr.client.ContactListAdapter;

public class ContactList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private String[] usernames;
    private Controller controller;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cifr_contact_list);
        controller = (Controller)getIntent().getSerializableExtra("Controller");
//        usernames = controller.recieveUserList(); //controllern fyller listan med data.
        setContacts();
        recyclerView = (RecyclerView) findViewById(R.id.contactListView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ContactListAdapter(this, usernames);
        recyclerView.setAdapter(adapter);
    }

    public void setContacts() {
        usernames = new String[3];
        usernames[0] = new String("Sven");
        usernames[1] = new String("Klas");
        usernames[2] = new String("Olaf");
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
