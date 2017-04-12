package se.mah.ag7406.cifr.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import se.mah.ag7406.cifr.R;
import se.mah.ag7406.cifr.client.ContactListAdapter;

public class ContactList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private String[] usernames;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cifr_contact_list);
        //controller fyller datan setContacts.
        recyclerView = (RecyclerView) findViewById(R.id.contactListView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ContactListAdapter(this, usernames);
        recyclerView.setAdapter(adapter);
    }

    public void setContacts(String[] data) {
        this.usernames = data;
    }
}
