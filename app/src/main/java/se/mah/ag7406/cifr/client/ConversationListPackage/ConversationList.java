package se.mah.ag7406.cifr.client.ConversationListPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import se.mah.ag7406.cifr.R;
import se.mah.ag7406.cifr.client.ContactListPackage.ContactList;
import se.mah.ag7406.cifr.client.ControllerPackage.Controller;
import se.mah.ag7406.cifr.client.StartActivities.LoginScreen;
import se.mah.ag7406.cifr.client.SearchActivityPackage.SearchActivity;
import se.mah.ag7406.cifr.client.ControllerPackage.SuperClass;

/**
 * Activity for displaying a list of ongoing conversations. These are
 * represented by two columns with the latest sent image of each conversation
 * and the username of the conversation partner.
 * @author Viktor Ekström
 */
public class ConversationList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private GridItem[] gridItems;
    private Controller controller;

    /**
     * Called when the activity is first created. Initiates the required view and adapter
     * and collects data from the controller for these.
     * @param savedInstanceState Saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        controller = SuperClass.getController();
        controller.setflag(true, "Convolistisactive", this);
        gridItems = controller.getGridItems();
        if(gridItems==null){
            showInformation();
        }
        recyclerView = (RecyclerView) findViewById(R.id.conversationList);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new ConversationListAdapter(this, gridItems);
        recyclerView.setAdapter(recyclerAdapter);
    }

    protected void onDestroy(){
        controller.setflag(false, null, null);
        super.onDestroy();
    }
    /**
     * Overrides the back-button so that it does nothing.
     */
    public void onBackPressed(){

    }
    public void showInformation() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        WelcomeDialogFragment welcomeFragment = WelcomeDialogFragment.newInstance();
        welcomeFragment.show(fragmentManager, "fragment");
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

}
