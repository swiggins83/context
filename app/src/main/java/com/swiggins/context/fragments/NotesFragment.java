package com.swiggins.context.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.swiggins.context.R;
import com.swiggins.context.activities.CreateNotesActivity;

public class NotesFragment extends Fragment {

    public NotesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notes, container, false);

        ImageButton fab = (ImageButton) v.findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });

        return v;
    }

    private void addNote() {
        Intent intent = new Intent(getActivity(), CreateNotesActivity.class);
        startActivity(intent);
    }

}
