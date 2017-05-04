package se.mah.ag7406.cifr.client.ConversationPackage;

import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import se.mah.ag7406.cifr.R;

/**
 * Functionality for displaying a fragment view where a message's hidden
 * text is shown.
 * @author Viktor Ekström
 * Created by Viktor on 2017-05-01.
 */

public class DialogFragmentMessage extends DialogFragment {
    private TextView textView;

    /**
     * Empty constructor.
     */
    public DialogFragmentMessage() {

    }

    /**
     * Creates a new instance of this class. Used instead of constructor.
     * @param message The message to be displayed.
     * @return A new fragment of this type.
     */
    public static DialogFragmentMessage newInstance(String message) {
        DialogFragmentMessage fragment = new DialogFragmentMessage();
        Bundle bundle = new Bundle();
        bundle.putString("Message", message);
        fragment.setArguments(bundle);
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
        return inflater.inflate(R.layout.dialogfragment_message, container);
    }

    /**
     * Sets the text of the layouts TextView with the passed message.
     * @param view The created view to be shown.
     * @param savedInstanceState
     */
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (TextView) view.findViewById(R.id.messageFragmentTextView);
        String message = getArguments().getString("Message");
        textView.setText(message);
    }
}
