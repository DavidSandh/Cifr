package se.mah.ag7406.cifr.client;

import android.content.Context;
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
 * Created by Viktor on 2017-04-07.
 */

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private ConversationItem[] conversationItems;


    public ConversationAdapter(Context context, ConversationItem[] array ) {
        this.inflater = LayoutInflater.from(context);
        this.conversationItems = array;
    }

    public ConversationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = (View) inflater.inflate(R.layout.conversation_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(conversationItems[position].getTimeAndDate());
        holder.imageView.setImageBitmap(conversationItems[position].getImage());
    }

    public int getItemCount() {
        return conversationItems.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private Context context;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.conversationImageView);
            textView = (TextView) view.findViewById(R.id.conversationTextView);
            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Log.d("Log", "Click");
                    //Gör bilden fullskärm. Ska kanske, kanske inte vara här.
                }
            });
        }
    }
}
