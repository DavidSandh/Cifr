package se.mah.ag7406.cifr.client;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import se.mah.ag7406.cifr.R;

/**
 * Adapter for the Conversation activity. It will fill the activity's RecyclerView
 * with the proper data. This data is passed at construction. The data is a collection of
 * ConversationItems, containing one image and a date.
 * Created by Viktor on 2017-04-07.
 * @author Viktor Ekström
 */

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private ConversationItem[] conversationItems;

    /**
     * Instantiates the object with the data to be displayed and the context of the
     * layout used for the inflater.
     * @param context The activity using this adapter.
     * @param array The data to be displayed.
     */
    public ConversationAdapter(Context context, ConversationItem[] array ) {
        this.inflater = LayoutInflater.from(context);
        this.conversationItems = array;
    }

    /**
     * Creates a new ViewHolder object with the proper type of layout item.
     * @param parent The ViewGroup where the new view will be added.
     * @param viewType The view type of the new view.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    public ConversationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = (View) inflater.inflate(R.layout.conversation_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * Called by the RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder that should be updated with the content of the data set
     *               at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(conversationItems[position].getTimeAndDate());
        holder.imageView.setImageBitmap(conversationItems[position].getImage());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    public int getItemCount() {
        return conversationItems.length;
    }

    /**
     * Describes a an item view and metadata about its place within the RecyclerView.
     * Provides clickable ImageViews for reading hidden messages.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private Context context;
        private Controller controller;

        public ViewHolder(View view) {
            super(view);
            controller = SuperClass.getController();
            imageView = (ImageView) view.findViewById(R.id.conversationImageView);
            textView = (TextView) view.findViewById(R.id.conversationTextView);
            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Log.d("Log", "Click");
                    Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                    String message = controller.decodeBitmap(image);
                    Log.d("message", "message: " + message);
                    //Det loggade message är alltså bildens gömda meddelande/ Viktor
                }
            });
        }
    }
}
