package com.mercdev.shustanov.flagquiz;

import android.app.Application;

import dagger.ObjectGraph;

/**
 * alexander.shustanov on 10/13/2014.
 */
public class FlagQuizApp extends Application {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(new CommonModule(this));
    }

    public void inject(Object o) {
        objectGraph.inject(o);
    }
}
