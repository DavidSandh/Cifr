package se.mah.ag7406.cifr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ConversationList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);

        recyclerView = (RecyclerView) findViewById(R.id.conversationList);
        recyclerManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(recyclerManager);
        //recyclerAdapter = new CustomRecyclerViewAdapter(); //Ska ha en parameter för att skicka vidare datan?/Viktor
        recyclerView.setAdapter(recyclerAdapter);





    }
}
