package com.pabrou.widgettest;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private TextView outputView;
    private EditText inputView;
    private Button saveButton;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getActivity().getString(R.string.shared_preference_key), Context.MODE_PRIVATE);

        String readString = sharedPref.getString(getString(R.string.pref_value), "");

        outputView = (TextView) getActivity().findViewById(R.id.text_output);
        inputView = (EditText) getActivity().findViewById(R.id.text_input);
        saveButton = (Button) getActivity().findViewById(R.id.save_button);

        outputView.setText(readString);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getActivity().getSharedPreferences(
                        getActivity().getString(R.string.shared_preference_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.pref_value), inputView.getText().toString());
                editor.commit();
            }
        });
    }
}
