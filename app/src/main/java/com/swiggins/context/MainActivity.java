package com.swiggins.context;

import android.app.Activity;
import android.widget.ListView;
import android.widget.Toast;

import com.swiggins.context.adapters.ObjectiveListAdapter;
import com.swiggins.context.models.Objective;
import com.swiggins.context.utils.ObjectiveManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    @ViewById(R.id.listView)
    ListView listView;

    @Bean
    ObjectiveManager objectiveManager;

    @AfterViews
    void init() {
        //List<Objective> objectives = objectiveManager.getObjectives();
        List<Objective> objectives = new ArrayList<Objective>();

        objectives.add(
                new Objective.Builder()
                        .setTitle("TITLE")
                        .setDescription("PPOOOP")
                        .build());
        objectives.add(
                new Objective.Builder()
                        .setTitle("TITLE2")
                        .setDescription("PPOOOP2")
                        .build());

        objectives.add(
                new Objective.Builder()
                        .setTitle("TITLE")
                        .setDescription("PPOOOP")
                        .setImage(getDrawable(android.R.drawable.ic_menu_report_image))
                        .build());

        listView.setAdapter(new ObjectiveListAdapter(this,
                R.layout.objective_cell, objectives));

    }

}
