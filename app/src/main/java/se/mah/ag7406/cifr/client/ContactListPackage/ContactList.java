package se.mah.ag7406.cifr.client.ContactListPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import se.mah.ag7406.cifr.R;
import se.mah.ag7406.cifr.client.ControllerPackage.Controller;
import se.mah.ag7406.cifr.client.ControllerPackage.SuperClass;
import se.mah.ag7406.cifr.client.ConversationListPackage.ConversationList;
import se.mah.ag7406.cifr.client.SearchActivityPackage.SearchActivity;
import se.mah.ag7406.cifr.client.StartActivities.LoginScreen;

/**
 * Activity for displaying the list of contacts available for the user.
 * This is displayed in a scrollable list with usernames representing
 * each contact.
 * @author Viktor Ekström
 */
public class ContactList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private String[] usernames;
    private Controller controller;

    /**
     * Called when the activity is first created. Initialises the RecyclerView
     * and its adapter with the contact list data collected from the controller.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Ubertest!!!" + "COntactlist startad");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cifr_contact_list);
        controller = SuperClass.getController();
        usernames = controller.recieveUserList(); //controllern fyller listan med data.
        System.out.println("skriver");
        System.out.println(usernames);
        for(int i = 0; i < usernames.length; i++) {
            System.out.println(usernames[i]);
        }
//        setContacts();
        recyclerView = (RecyclerView) findViewById(R.id.contactListView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ContactListAdapter(this, usernames);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Test method, to be removed.
     */
    public void setContacts() {
        usernames = new String[3];
        usernames[0] = new String("Sven");
        usernames[1] = new String("Klas");
        usernames[2] = new String("Olaf");
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
}
