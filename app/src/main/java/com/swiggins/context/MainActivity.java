package com.swiggins.context;

import android.app.Activity;
import android.widget.ListView;
import android.widget.Toast;

import com.swiggins.context.adapters.ObjectiveListAdapter;
import com.swiggins.context.models.Objective;
import com.swiggins.context.utils.ObjectiveManager;
import com.wdullaer.swipeactionadapter.SwipeActionAdapter;

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
                        .build());

        objectives.add(
                new Objective.Builder()
                        .setTitle("LOREM")
                        .setDescription("IPSUM")
                        .build());

        objectives.add(
                new Objective.Builder()
                        .setTitle("HEYEYEYEYYE")
                        .setDescription("kjlawhklakwhlakj")
                        .build());

        objectives.add(
                new Objective.Builder()
                        .setTitle("stuff")
                        .setDescription("helkhelkehlekjlekhlekheljk")
                        .build());

        ObjectiveListAdapter adapter = new ObjectiveListAdapter(
                this,
                listView,
                R.layout.objective_cell,
                objectives);

        listView.setAdapter(adapter);

    }

}
