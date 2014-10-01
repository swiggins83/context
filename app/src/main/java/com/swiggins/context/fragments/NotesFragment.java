package com.swiggins.context.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;
import com.swiggins.context.R;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_notes)
public class NotesFragment extends Fragment {

    private FadingActionBarHelper fadingActionBarHelper;
    private View view;

    @ViewById(R.id.add_button)
    View addButton;

    public NotesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = fadingActionBarHelper.createView(inflater);

        int diameter = getResources().getDimensionPixelSize(R.dimen.diameter);
        Outline outline = new Outline();
        outline.setOval(0, 0, diameter, diameter);
        addButton.setOutline(outline);
        addButton.setClipToOutline(true);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNote();
            }
        });
        return view;
    }

    public void addNote() {
        Toast.makeText(getActivity().getApplicationContext(), "YO", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        fadingActionBarHelper = new FadingActionBarHelper()
                .actionBarBackground(R.drawable.action_bar)
                .headerLayout(R.drawable.oval_ripple)
                .contentLayout(R.layout.activity_main);
        fadingActionBarHelper.initActionBar(activity);
    }

}
