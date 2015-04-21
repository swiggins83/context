package com.swiggins.context.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
    private LayoutInflater inflater;
    List<Objective> objectives = null;
    ListView list;

    public ObjectiveListAdapter(Context context, ListView list, int layoutResourceId, List<Objective> objectives) {
        super(context, layoutResourceId, objectives);
        this.context = context;
        this.list = list;
        this.layoutResourceId = layoutResourceId;
        this.objectives = objectives;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View cell = convertView;
        ObjectiveLayout objectiveLayout = null;

        if (cell == null) {
            cell = inflater.inflate(layoutResourceId, parent, false);
            objectiveLayout = new ObjectiveLayout();
            objectiveLayout.mainView = (RelativeLayout) cell.findViewById(R.id.objective_layout);
            objectiveLayout.titleView = (TextView) cell.findViewById(R.id.objective_title);
            objectiveLayout.descriptionView = (TextView) cell.findViewById(R.id.objective_description);
            objectiveLayout.imageView = (ImageView) cell.findViewById(R.id.objective_image);
            objectiveLayout.doneView = (RelativeLayout) cell.findViewById(R.id.done_layout);
            objectiveLayout.snoozeView = (RelativeLayout) cell.findViewById(R.id.snooze_layout);
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

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) objectiveLayout.mainView.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        objectiveLayout.mainView.setLayoutParams(params);
        cell.setOnTouchListener(new SwipeDetector(objectiveLayout, position));

        return cell;
    }

    public void setListView(ListView list) {
        this.list = list;
    }

    public static class ObjectiveLayout {
        public RelativeLayout mainView;
        public TextView titleView;
        public TextView descriptionView;
        public ImageView imageView;
        public RelativeLayout doneView;
        public RelativeLayout snoozeView;
    }

    public class SwipeDetector implements View.OnTouchListener {

        private static final int MIN_DISTANCE = 300;
        private static final int MIN_LOCK_DISTANCE = 50; // disallow motion intercept
        private boolean motionInterceptDisallowed = false;
        private float downX, upX;
        private ObjectiveLayout layout;
        private int position;

        public SwipeDetector(ObjectiveLayout layout, int position) {
            this.layout = layout;
            this.position = position;
        }

        @Override
        public boolean onTouch(final View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    downX = event.getX();
                    return true; // allow other events like Click to be processed
                }

                case MotionEvent.ACTION_MOVE: {

                    upX = event.getX();
                    float deltaX = downX - upX;

                    if (Math.abs(deltaX) > MIN_LOCK_DISTANCE && list != null && !motionInterceptDisallowed) {
                        motionInterceptDisallowed = true;
                    }

                    if (deltaX > 0) {
                        layout.snoozeView.setVisibility(View.GONE);
                    } else {
                        // if first swiped left and then swiped right
                        layout.snoozeView.setVisibility(View.VISIBLE);
                    }

                    swipe(-(int) deltaX);
                    return true;
                }

                case MotionEvent.ACTION_UP:

                    upX = event.getX();
                    float deltaX = upX - downX;
                    if (Math.abs(deltaX) > MIN_DISTANCE) {
                        if (deltaX > 0) {
                            animateMainView(2);
                        } else {
                            animateMainView(-2);
                        }
                    } else {
                        animateMainView(0);
                    }

                    if (list != null) {
                        list.requestDisallowInterceptTouchEvent(false);
                        motionInterceptDisallowed = false;
                    }

                    return true;

                case MotionEvent.ACTION_CANCEL:

                    upX = event.getX();
                    deltaX = upX - downX;
                    if (Math.abs(deltaX) > MIN_DISTANCE) {
                        if (deltaX > 0) {
                            animateMainView(2);
                        } else {
                            animateMainView(-2);
                        }
                    } else {
                        animateMainView(0);
                    }

                    return true;
            }

            return true;
        }

        // -2 : left
        // 0 : neutral
        // 2 : right
        private void animateMainView(final float where) {

            RelativeLayout l = layout.mainView;
            final float layoutWidth = layout.mainView.getWidth();

            Log.d("AKKSJDLA", "" + l.getLeft());

            float changeValue = layoutWidth * where;
            final ValueAnimator animator = ValueAnimator.ofFloat(layout.mainView.getX(), changeValue);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    layout.mainView.setX((Float) valueAnimator.getAnimatedValue());
                }
            });
            animator.setDuration(500);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (where == -2 || where == 2) {
                        swipeRemove();
                    }
                }
            });
            animator.start();
        }

        private void swipe(int distance) {
            layout.mainView.setX(distance);
        }

        private void swipeRemove() {
            remove(getItem(position));
            notifyDataSetChanged();
            swipe(0);
        }
    }

}
