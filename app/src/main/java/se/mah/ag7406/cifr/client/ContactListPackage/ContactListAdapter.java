package se.mah.ag7406.cifr.client.ContactListPackage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.MenuItem;

import se.mah.ag7406.cifr.R;
import se.mah.ag7406.cifr.client.ConversationPackage.Conversation;


/**
 * Adapter for the ContactList activity. It will fill the activity's RecyclerView
 * with the proper data. This data is passed at construction. The data is a collection
 * String representing user names.
 * @author Viktor Ekström
 * Created by Viktor on 2017-04-10.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder>{
    private String[] usernames;
    private LayoutInflater inflater;

    /**
     * Instantiates the object with the data to be displayed and the context of
     * the layout used by the inflater.
     * @param context The activity using this adapter.
     * @param data The data to be displayed.
     */
    public ContactListAdapter(Context context, String[] data) {
        this.usernames = data;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * Creates a new ViewHolder object with the proper type of layout item.
     * @param parent The ViewGroup where the new view will be added.
     * @param viewType The view type of the new view.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    public ContactListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = (TextView) inflater.inflate(R.layout.contact_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * Called by the RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder that should be updated with the content of the data set.
     * @param position The position of the item within the adapter's data set.
     */
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(usernames[position]);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    public int getItemCount() {
        if(usernames!=null){
            return usernames.length;

        }
        return 0;
    }

    /**
     * Describes a an item view and metadata about its place within the RecyclerView.
     * Provides clickable TextViews for navigation to that ongoing conversation. Each
     * TextView also provides long click for a drop down menu where the user can remove
     * the selected user from the contact list.
     */
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
                    System.out.println("ContactListAdapter Receiver är: " + textView.getText());
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

        /**
         * Displays a PopopMenu.
         * @param context The view that was clicked.
         * @param view Where the menu will be anchored.
         */
        public void showPopup(Context context, View view) {
            PopupMenu popup = new PopupMenu(context, view);
            popup.getMenuInflater().inflate(R.menu.contact_list_popup_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    if(item.getItemId() == 1) {
//                        controller.removeUserFromContactList(textView.getText());
                    }
                    return false;
                }
            });
            popup.show();
        }

    }
}
