package com.mercdev.shustanov.flagquiz.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import butterknife.ButterKnife;

/**
 * alexander.shustanov on 10/13/2014.
 */
@SuppressLint("Registered")
public class InjectActivity extends Activity {

    protected void createAndInject(Bundle savedInstanceState, int layoutId) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);
        ButterKnife.inject(this);
    }
}
