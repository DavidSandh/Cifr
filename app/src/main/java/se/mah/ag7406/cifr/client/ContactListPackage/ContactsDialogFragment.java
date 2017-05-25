package se.mah.ag7406.cifr.client.ContactListPackage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import se.mah.ag7406.cifr.R;

/**
 * Functionality for creating a fragment view where information is displayed
 * about the rules regarding username and passwords.
 * @author David Sandh
 * Created by David on 2017-05-19.
 */

public class ContactsDialogFragment extends DialogFragment {
    private TextView textView;

    /**
     * Empty constructor
     */
    public ContactsDialogFragment() {

    }
    /**
     * Creates a new instance of this class. Used instead of constructor.
     * @return A new fragment of this type.
     */
    public static ContactsDialogFragment newInstance() {
        ContactsDialogFragment fragment = new ContactsDialogFragment();
        return fragment;
    }

    /**
     * Creates a new view with the proper layout type.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return A new view filled with the proper layout.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialogfragment_getsomefriends, container);
    }

    /**
     * Sets the text of the layouts TextView with the passed message.
     * @param view The created view to be shown.
     * @param savedInstanceState
     */
    public void onCreateView (View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (TextView) view.findViewById(R.id.contactsMessage);

    }
}