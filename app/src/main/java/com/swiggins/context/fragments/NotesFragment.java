package com.swiggins.context.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.swiggins.context.R;
import com.swiggins.context.activities.MainActivity;

public class NotesFragment extends Fragment {

    public NotesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notes, container, false);

        int diameter = getResources().getDimensionPixelSize(R.dimen.diameter);
        Outline outline = new Outline();
        outline.setOval(0, 0, diameter, diameter);
        View addButton = v.findViewById(R.id.add_button);
        addButton.setOutline(outline);
        addButton.setClipToOutline(true);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "yo", Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }

}
