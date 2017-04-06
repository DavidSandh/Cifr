package se.mah.ag7406.cifr.cifrClient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import se.mah.ag7406.cifr.R;

public class ConversationList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerManager;
    private GridItem[] gridItems; //null just nu /Viktor

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);

        recyclerView = (RecyclerView) findViewById(R.id.conversationList);
        recyclerManager = new GridLayoutManager(this, 2); //Hoppas att 2 betyder två kolumner. /Viktor
        recyclerView.setLayoutManager(recyclerManager);
        recyclerAdapter = new CustomRecyclerViewAdapter(this, gridItems);
        recyclerView.setAdapter(recyclerAdapter);
    }

    public void setGridData(GridItem[] array) {
        this.gridItems = array;
    }
}
