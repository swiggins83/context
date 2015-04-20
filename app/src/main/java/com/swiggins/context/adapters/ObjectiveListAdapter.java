package com.swiggins.context.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.swiggins.context.R;
import com.swiggins.context.models.Objective;

import java.util.List;

/**
 * Created by Steven on 3/16/2015.
 */
public class ObjectiveListAdapter extends ArrayAdapter<Objective> {
    private Context context;
    private int layoutResourceId;
    List<Objective> objectives = null;

    public ObjectiveListAdapter(Context context, int layoutResourceId, List<Objective> objectives) {
        super(context, layoutResourceId, objectives);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.objectives = objectives;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View cell = convertView;
        ObjectiveLayout objectiveLayout = null;

        if (cell == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            cell = inflater.inflate(layoutResourceId, parent, false);
            objectiveLayout = new ObjectiveLayout();
            objectiveLayout.titleView = (TextView) cell.findViewById(R.id.objective_title);
            objectiveLayout.descriptionView = (TextView) cell.findViewById(R.id.objective_description);
            objectiveLayout.imageView = (ImageView) cell.findViewById(R.id.objective_image);
            cell.setTag(objectiveLayout);
        } else {
            objectiveLayout = (ObjectiveLayout) cell.getTag();
        }

        Objective objective = objectives.get(position);
        objectiveLayout.titleView.setText(objective.getTitle());
        objectiveLayout.descriptionView.setText(objective.getDescription());

        if (objective.getImage() != null) {
            objectiveLayout.imageView.setImageDrawable(objective.getImage());
        } else {
            objectiveLayout.imageView.setVisibility(ImageView.GONE);
        }

        return cell;
    }

    static class ObjectiveLayout {
        TextView titleView;
        TextView descriptionView;
        ImageView imageView;
    }
}
