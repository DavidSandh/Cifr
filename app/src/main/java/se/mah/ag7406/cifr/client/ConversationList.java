package se.mah.ag7406.cifr.client;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import se.mah.ag7406.cifr.R;

public class ConversationList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private GridItem[] gridItems; //null just nu /Viktor
    private Controller controller;
    private ConversationListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        controller = (Controller)getIntent().getSerializableExtra("Controller");
        gridItems = SuperClass.getController().getGridItems("username"); //Om inte username redan finns i controllern?
        //setGridData(); //Via controller, detta är för test.
        recyclerView = (RecyclerView) findViewById(R.id.conversationList);
        layoutManager = new GridLayoutManager(this, 2); //Hoppas att 2 betyder två kolumner. /Viktor
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ConversationListAdapter(this, gridItems);
//        recyclerAdapter = new ConversationListAdapter(this, gridItems);
        adapter.setController(SuperClass.getController());
        //adapter.setController(controller);
//        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setAdapter(adapter);

    }

    public void setGridData() { //Testmetod. Ska ändra att ta emot en parameter med array-datan när test inte behövs.
        gridItems = new GridItem[3];
        GridItem gridItem1 = new GridItem("Sven", BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        GridItem gridItem2 = new GridItem("Klas",BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round));
        GridItem gridItem3 = new GridItem("Olaf",BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        gridItems[0] = gridItem1;
        gridItems[1] = gridItem2;
        gridItems[2] = gridItem3;
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
