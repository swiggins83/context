package com.swiggins.context.activities;


import android.app.Activity;
import android.os.Bundle;

import com.swiggins.context.R;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_create_notes)
public class CreateNotesActivity extends Activity {

    public CreateNotesActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
