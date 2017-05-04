package se.mah.ag7406.cifr.client;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import se.mah.ag7406.cifr.R;

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
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        controller = SuperClass.getController();

        gridItems = controller.getGridItems();
        //setGridData(); //Via controller annars, detta är för test.
        recyclerView = (RecyclerView) findViewById(R.id.conversationList);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new ConversationListAdapter(this, gridItems);
        recyclerView.setAdapter(recyclerAdapter);
    }
    /**
     * Test method, to be removed.
     */
    public void setGridData() { //Testmetod. Ska ändra att ta emot en parameter med array-datan när test inte behövs.
        gridItems = new GridItem[3];
        GridItem gridItem1 = new GridItem("Sven", BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        GridItem gridItem2 = new GridItem("Klas",BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round));
        GridItem gridItem3 = new GridItem("Olaf",BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        gridItems[0] = gridItem1;
        gridItems[1] = gridItem2;
        gridItems[2] = gridItem3;
    }
    public void onBackPressed(){

    }
    protected void home(MenuItem item){
        Intent intent = new Intent(this, ConversationList.class);
        startActivity(intent);
        finish();
    }

    protected void contacts(MenuItem item){
        Intent intent = new Intent(this, ContactList.class);
        startActivity(intent);
        finish();
    }

    public void search(MenuItem item){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
        finish();
    }

    public void logout(MenuItem item){
        controller.logout();
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
        finish();
    }

}
