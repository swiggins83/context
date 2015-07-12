package com.swiggins.context.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.swiggins.context.R;
import com.swiggins.context.models.Objective;

import java.util.HashMap;
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
    boolean mItemPressed;
    boolean mSwiping;
    HashMap<Long, Integer> mItemIdTopMap = new HashMap<Long, Integer>();

    private static final int SWIPE_DURATION = 250;
    private static final int MOVE_DURATION = 150;

    public ObjectiveListAdapter(Context context, ListView list, int layoutResourceId, List<Objective> objectives) {
        super(context, layoutResourceId, objectives);
        this.context = context;
        this.list = list;
        this.layoutResourceId = layoutResourceId;
        this.objectives = objectives;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public boolean hasStableIds() {
        return true;
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

        cell.setOnTouchListener(new SwipeDetector());

        return cell;
    }

    public static class ObjectiveLayout {
        public RelativeLayout mainView;
        public TextView titleView;
        public TextView descriptionView;
        public ImageView imageView;
        public RelativeLayout doneView;
        public RelativeLayout snoozeView;
    }

    /**
     * Handle touch events to fade/move dragged items as they are swiped out
     */
    private class SwipeDetector implements View.OnTouchListener {

        float mDownX;
        private int mSwipeSlop = -1;

        @Override
        public boolean onTouch(final View v, MotionEvent event) {

            final View mainView = v.findViewById(R.id.objective_layout);
            final View snoozeView = v.findViewById(R.id.snooze_layout);
            final View doneView = v.findViewById(R.id.done_layout);

            if (mSwipeSlop < 0) {
                mSwipeSlop = ViewConfiguration.get(getContext()).
                        getScaledTouchSlop();
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (mItemPressed) {
                        // Multi-item swipes not handled
                        return false;
                    }
                    mItemPressed = true;
                    mDownX = event.getX();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    mainView.setTranslationX(0);
                    mainView.setAlpha(1);
                    mItemPressed = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    {
                        float x = event.getX() + v.getTranslationX();
                        float deltaX = x - mDownX;
                        if (deltaX > 0) {
                            snoozeView.setVisibility(View.GONE);
                        } else {
                            snoozeView.setVisibility(View.VISIBLE);
                        }

                        float deltaXAbs = Math.abs(deltaX);
                        if (!mSwiping) {
                            if (deltaXAbs > mSwipeSlop) {
                                mSwiping = true;
                                list.requestDisallowInterceptTouchEvent(true);
                            }
                        }
                        if (mSwiping) {
                            mainView.setTranslationX((x - mDownX));
                            mainView.setAlpha(1 - deltaXAbs / v.getWidth());
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    {
                        // User let go - figure out whether to animate the view out, or back into place
                        if (mSwiping) {
                            float x = event.getX() + v.getTranslationX();
                            float deltaX = x - mDownX;
                            float deltaXAbs = Math.abs(deltaX);
                            float fractionCovered;
                            float endX;
                            float endAlpha;
                            final boolean remove;
                            if (deltaXAbs > v.getWidth() / 4) {
                                // Greater than a quarter of the width - animate it out
                                fractionCovered = deltaXAbs / v.getWidth();
                                endX = deltaX < 0 ? -v.getWidth() : v.getWidth();
                                endAlpha = 0;
                                remove = true;
                            } else {
                                // Not far enough - animate it back
                                fractionCovered = 1 - (deltaXAbs / v.getWidth());
                                endX = 0;
                                endAlpha = 1;
                                remove = false;
                            }
                            // Animate position and alpha of swiped item
                            // NOTE: This is a simplified version of swipe behavior, for the
                            // purposes of this demo about animation. A real version should use
                            // velocity (via the VelocityTracker class) to send the item off or
                            // back at an appropriate speed.
                            long duration = (int) ((1 - fractionCovered) * SWIPE_DURATION);
                            list.setEnabled(false);
                            mainView.animate().setDuration(duration).
                                    alpha(endAlpha).translationX(endX).
                                    withEndAction(new Runnable() {
                                        @Override
                                        public void run() {
                                            // Restore animated values
                                            mainView.setAlpha(1);
                                            if (remove) {
                                                mainView.setTranslationX(0);
                                                animateRemoval(list, v);
                                            } else {
                                                mSwiping = false;
                                                list.setEnabled(true);
                                            }
                                        }
                                    });
                        }
                    }
                    mItemPressed = false;
                    break;
                default:
                    return false;
            }
            return true;
        }
    };

    /**
     * This method animates all other views in the ListView container (not including ignoreView)
     * into their final positions. It is called after ignoreView has been removed from the
     * adapter, but before layout has been run. The approach here is to figure out where
     * everything is now, then allow layout to run, then figure out where everything is after
     * layout, and then to run animations between all of those start/end positions.
     */
    private void animateRemoval(final ListView listview, View viewToRemove) {
        int firstVisiblePosition = listview.getFirstVisiblePosition();
        for (int i = 0; i < listview.getChildCount(); ++i) {
            View child = listview.getChildAt(i);
            if (child != viewToRemove) {
                int position = firstVisiblePosition + i;
                long itemId = getItemId(position);
                mItemIdTopMap.put(itemId, child.getTop());
            }
        }
        // Delete the item from the adapter
        int position = listview.getPositionForView(viewToRemove);
        remove(getItem(position));

        final ViewTreeObserver observer = listview.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                observer.removeOnPreDrawListener(this);
                boolean firstAnimation = true;
                int firstVisiblePosition = listview.getFirstVisiblePosition();
                for (int i = 0; i < listview.getChildCount(); ++i) {
                    final View child = listview.getChildAt(i);
                    int position = firstVisiblePosition + i;
                    long itemId = getItemId(position);
                    Integer startTop = mItemIdTopMap.get(itemId);
                    int top = child.getTop();
                    if (startTop != null) {
                        if (startTop != top) {
                            int delta = startTop - top;
                            child.setTranslationY(delta);
                            child.animate().setDuration(MOVE_DURATION).translationY(0);
                            if (firstAnimation) {
                                child.animate().withEndAction(new Runnable() {
                                    public void run() {
                                        mSwiping = false;
                                        listview.setEnabled(true);
                                    }
                                });
                                firstAnimation = false;
                            }
                        }
                    } else {
                        // Animate new views along with the others. The catch is that they did not
                        // exist in the start state, so we must calculate their starting position
                        // based on neighboring views.
                        int childHeight = child.getHeight() + listview.getDividerHeight();
                        startTop = top + childHeight;
                        int delta = startTop - top;
                        child.setTranslationY(delta);
                        child.animate().setDuration(MOVE_DURATION).translationY(0);
                        if (firstAnimation) {
                            child.animate().withEndAction(new Runnable() {
                                public void run() {
                                    mSwiping = false;
                                    listview.setEnabled(true);
                                }
                            });
                            firstAnimation = false;
                        }
                    }
                }
                mItemIdTopMap.clear();
                return true;
            }
        });
    }


}
