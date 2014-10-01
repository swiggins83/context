package com.swiggins.context.activities;


import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.swiggins.context.R;

public class CreateNotesActivity extends Activity {

    public CreateNotesActivity() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);

        View saveButton = findViewById(R.id.add_button);
        setUpFab(saveButton);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setUpFab(View addButton) {
        int diameter = getResources().getDimensionPixelSize(R.dimen.diameter);
        Outline outline = new Outline();
        outline.setOval(0, 0, diameter, diameter);
        addButton.setOutline(outline);
        addButton.setClipToOutline(true);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        Toast.makeText(this, ((EditText) findViewById(R.id.note_content)).getText().toString(), Toast.LENGTH_LONG).show();
    }

}
