package com.swiggins.context.activities;


import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.swiggins.context.R;

public class CreateNotesActivity extends Activity {

    public CreateNotesActivity() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ImageButton fab = (ImageButton) findViewById(R.id.save_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        Toast.makeText(this, ((EditText) findViewById(R.id.note_content)).getText().toString(), Toast.LENGTH_LONG).show();
    }

}
