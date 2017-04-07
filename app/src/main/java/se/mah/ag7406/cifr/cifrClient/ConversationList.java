package se.mah.ag7406.cifr.cifrClient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import se.mah.ag7406.cifr.R;

public class ConversationList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private GridItem[] gridItems; //null just nu /Viktor


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        setGridData();
        Log.d("setGridData","körd");
        recyclerView = (RecyclerView) findViewById(R.id.conversationList);
        Log.d("findviewbyId","körd");
        layoutManager = new GridLayoutManager(this, 2); //Hoppas att 2 betyder två kolumner. /Viktor
        Log.d("new gridlayoutManager","körd");
        recyclerView.setLayoutManager(layoutManager);
        Log.d("setLayoutManager","körd");
        recyclerAdapter = new CustomRecyclerViewAdapter(this, gridItems);
        Log.d("new CustomRecycler","körd");
        recyclerView.setAdapter(recyclerAdapter);
        Log.d("setAdapter","körd");




    }

    public void setGridData() { //Testmetod. Ska ändra att ta emot en parameter med array-datan när test inte behövs.
        gridItems = new GridItem[3];
        GridItem gridItem1 = new GridItem("Sven",R.mipmap.ic_launcher);
        GridItem gridItem2 = new GridItem("Klas",R.mipmap.ic_launcher_round);
        GridItem gridItem3 = new GridItem("Olaf",R.mipmap.ic_launcher);
        gridItems[0] = gridItem1;
        gridItems[1] = gridItem2;
        gridItems[2] = gridItem3;
    }
}
