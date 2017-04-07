package se.mah.ag7406.cifr.cifrClient;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import se.mah.ag7406.cifr.R;

/**
 * Created by Viktor on 2017-04-06.
 */

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private GridItem[] gridItems;

    public CustomRecyclerViewAdapter(Context context, GridItem[] array) {
        this.inflater = LayoutInflater.from(context);
        this.gridItems = array;
    }

    public CustomRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = (View) inflater.inflate(R.layout.conversation_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(gridItems[position].getUsername());
        holder.imageView.setImageResource(gridItems[position].getImage());

    }

    @Override
    public int getItemCount() {
        return gridItems.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;

        public ViewHolder (View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.conversationItemTextView);
            imageView = (ImageView) view.findViewById(R.id.conversationItemImageView);
        }
    }

}