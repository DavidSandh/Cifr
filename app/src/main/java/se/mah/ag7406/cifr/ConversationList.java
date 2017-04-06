package se.mah.ag7406.cifr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class ConversationList extends AppCompatActivity {
    private CustomRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);

        adapter = (RecyclerView) findViewById(R.layout.activity_conversation_list);



    }
}
