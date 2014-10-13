package com.mercdev.shustanov.flagquiz.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.mercdev.shustanov.flagquiz.R;
import com.mercdev.shustanov.flagquiz.view.InjectActivity;

import butterknife.OnClick;


public class MainActivity extends InjectActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.createAndInject(savedInstanceState, R.layout.activity_main);
    }

    @OnClick(R.id.start_quiz_button)
    void onStartQuizButtonClick() {
        Intent intent = new Intent(this, GameScreenActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.scores_button)
    void onScoreButtonClick() {
        Intent intent = new Intent(this, ScoresActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.settings_button)
    void onSettingsButtonClick() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.exit_button)
    void onExitButtonClick() {
        finish();
    }

}
