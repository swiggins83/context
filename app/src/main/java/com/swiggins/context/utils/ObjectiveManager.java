package com.swiggins.context.utils;

import com.swiggins.context.models.Objective;

import org.androidannotations.annotations.EBean;

/**
 * Created by Steven on 3/16/2015.
 */

@EBean(scope = EBean.Scope.Singleton)
public class ObjectiveManager {
    private Objective objective;

    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    public Objective getObjective() {
        return this.objective;
    }
}
