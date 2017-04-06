package se.mah.ag7406.cifr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Viktor on 2017-04-06.
 */

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder> {
    private LayoutInflater inflater;
    //private Data[] data; Den data som ska fylla rutorna, dvs. bilder och användarnamn.

    public CustomRecyclerViewAdapter(Context context/*, Data[] data*/) {
        this.inflater = LayoutInflater.from(context);
        //this.data = data;
    }

    public CustomRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = (View) inflater.inflate(R.layout.conversation_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ViewHolder (View conversationItem) {
            super(conversationItem);
            view = conversationItem;
        }
    }

}