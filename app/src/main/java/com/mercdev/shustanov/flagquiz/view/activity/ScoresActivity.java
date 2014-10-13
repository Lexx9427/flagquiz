package com.mercdev.shustanov.flagquiz.view.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.mercdev.shustanov.flagquiz.FlagQuizApp;
import com.mercdev.shustanov.flagquiz.R;
import com.mercdev.shustanov.flagquiz.data.ScoresAdapter;
import com.mercdev.shustanov.flagquiz.view.InjectActivity;

import javax.inject.Inject;

import butterknife.InjectView;

public class ScoresActivity extends InjectActivity {

    @InjectView(R.id.scores_list)
    ListView scoresList;

    @Inject
    ScoresAdapter scoresAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.createAndInject(savedInstanceState, R.layout.activity_scores);
        ((FlagQuizApp) getApplicationContext()).inject(this);
        scoresList.setAdapter(scoresAdapter);
        scoresList.invalidate();
    }

}
