package se.mah.ag7406.cifr.client;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import se.mah.ag7406.cifr.R;

/**
 * Used to fill the conversation list with the right type of views and to fill these with data.
 * Also enables activity change when a certain item is clicked.
 * Created by Viktor on 2017-04-06.
 */

public class ConversationListAdapter extends RecyclerView.Adapter<ConversationListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private GridItem[] gridItems;

    public ConversationListAdapter(Context context, GridItem[] array) {
        this.inflater = LayoutInflater.from(context);
        this.gridItems = array;
    }

    public ConversationListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = (View) inflater.inflate(R.layout.conversation_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(gridItems[position].getUsername());
        holder.imageView.setImageResource(gridItems[position].getImage());

    }

    public int getItemCount() {
        return gridItems.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;
        private Context context;

        public ViewHolder (View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.conversationItemTextView);
            imageView = (ImageView) view.findViewById(R.id.conversationItemImageView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Intent intent;
                    context = view.getContext();
                    intent = new Intent(context, Conversation.class);
                    intent.putExtra("username", textView.getText());
                    context.startActivity(intent);
                }
            });
        }
    }

}