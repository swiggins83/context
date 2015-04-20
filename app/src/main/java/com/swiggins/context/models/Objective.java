package com.swiggins.context.models;

import android.graphics.drawable.Drawable;

/**
 * Created by Steven on 3/16/2015.
 */
public class Objective {
    private String title;
    private String description;
    private Drawable image;

    public static class Builder {
        private String title;
        private String description;
        private Drawable image;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setImage(Drawable image) {
            this.image = image;
            return this;
        }

        public Objective build() {
            return new Objective(this);
        }
    }

    private Objective(Builder builder) {
        title = builder.title;
        description = builder.description;
        image = builder.image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Drawable getImage() {
        return image;
    }

}
