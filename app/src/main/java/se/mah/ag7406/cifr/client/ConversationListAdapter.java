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
 * Adapter for the ConversationList activity. It will fill the activity's RecyclerView with
 * the proper views and data. This data is passed at construction. The data is a collection of GridItems,
 * in turn consisting of one image and a username.
 * Created by Viktor on 2017-04-06.
 */
public class ConversationListAdapter extends RecyclerView.Adapter<ConversationListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private GridItem[] gridItems;

    /**
     * Initialises the adapter with the context of the activity and the data to be used.
     * @param context The activity using the adapter.
     * @param array The data to be displayed in the views.
     */
    public ConversationListAdapter(Context context, GridItem[] array) {
        this.inflater = LayoutInflater.from(context);
        this.gridItems = array;
    }

    /**
     * Creates a new ViewHolder object with the proper type of layout item.
     * @param parent The ViewGroup where the new view will be added.
     * @param viewType The ViewType of the new view.
     * @return A new ViewHolder that holds a view of the given view type.
     */
    public ConversationListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = (View) inflater.inflate(R.layout.conversation_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * Called by the RecyclerView to display the data at the given position.
     * @param holder The ViewHolder that should be updated with the content of the data set.
     * @param position The position of the item within the adapter's data set.
     */
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(gridItems[position].getUsername());
        holder.imageView.setImageBitmap(gridItems[position].getImage());

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    public int getItemCount() {
        if(gridItems != null){
            return gridItems.length;

        }
        return 0;
    }

    /**
     * Describes an item view and metadata about its place within the RecyclerView.
     * Provides clickable TextViews for navigation to that particular conversation.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;
        private Context context;
//        private Controller holderController;

        public ViewHolder (View view) {
            super(view);
//            holderController = SuperClass.getController();
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