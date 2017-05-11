package se.mah.ag7406.cifr.client.StartActivities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import se.mah.ag7406.cifr.R;

/**
 * Created by Viktor on 2017-05-11.
 */

public class DialogFragmentInfo extends DialogFragment {
    private TextView textView;

    public DialogFragmentInfo() {

    }

    public static DialogFragmentInfo newInstance() {
        DialogFragmentInfo fragment = new DialogFragmentInfo();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialogfragmen_info, container);
    }

    public void onCreateView (View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (TextView) view.findViewById(R.id.fragmentInfoTextView);
        textView.setText(R.string.registration_info);
    }
}
