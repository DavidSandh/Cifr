package se.mah.ag7406.cifr.client;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.MenuItem;

import se.mah.ag7406.cifr.R;


/**
 * Created by Viktor on 2017-04-10.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder>{
    private String[] usernames;
    private LayoutInflater inflater;

    public ContactListAdapter(Context context, String[] data) {
        this.usernames = data;
        this.inflater = LayoutInflater.from(context);
    }

    public ContactListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = (TextView) inflater.inflate(R.layout.contact_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(usernames[position]);
    }

    public int getItemCount() {
        return usernames.length;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private Context context;
        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.contactItemTextView);
            textView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent;
                    context = view.getContext();
                    intent = new Intent(context, Conversation.class);
                    intent.putExtra("username", textView.getText());
                    context.startActivity(intent);
                }
            });
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View view) {
                    showPopup(view.getContext(), view);
                    return true;

                }
            });
        }
        public void showPopup(Context context, View view) {
            PopupMenu popup = new PopupMenu(context, view);
            popup.getMenuInflater().inflate(R.menu.contact_list_popup_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    if(item.getItemId() == 1) {
//                        controller.removeUserFromContactList(textView.getText());
                    } else if (item.getItemId() == 2) {
//                        controller.BlockUserFromContactList(textView.getText());
                    }
                    return false;
                }
            });
            popup.show();
        }

    }
}
