package com.mercdev.shustanov.flagquiz.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.mercdev.shustanov.flagquiz.FlagQuizApp;
import com.mercdev.shustanov.flagquiz.R;
import com.mercdev.shustanov.flagquiz.db.DAOScores;
import com.mercdev.shustanov.flagquiz.view.GameView;
import com.mercdev.shustanov.flagquiz.view.InjectActivity;
import com.mercdev.shustanov.flagquiz.view.dialog.CongratulationsActivity;
import com.mercdev.shustanov.flagquiz.view.dialog.YouCouldBeBetterDialog;

import javax.inject.Inject;

import butterknife.InjectView;


public class GameScreenActivity extends InjectActivity implements GameView.GameProcessListener {

    private final String QUESTIONS_AMOUNT_KEY = "questionsAmount";
    private final String GOOD_KEY = "good";
    private final String BAD_KEY = "bad";
    private final String FB_NAME_EXTRA_KEY = "fb_name";
    @InjectView(R.id.game_view)
    GameView gameView;

    @InjectView(R.id.good_answer_icon)
    ImageView goodAnswerIcon;

    @InjectView(R.id.bad_answer_icon)
    ImageView badAnswerIcon;

    @InjectView(R.id.good)
    TextView goodText;
    @InjectView(R.id.bad)
    TextView badText;
    @InjectView(R.id.all)
    TextView allText;

    @Inject
    DAOScores daoScores;

    int good,bad, todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.createAndInject(savedInstanceState, R.layout.activity_game_screen);
        ((FlagQuizApp) getApplicationContext()).inject(this);

        int questionsAmount;
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_key), MODE_PRIVATE);
        if(savedInstanceState != null) {
            questionsAmount = savedInstanceState.getInt(QUESTIONS_AMOUNT_KEY, sharedPreferences.getInt(getString(R.string.questions_amount_key), 10));
            good =  savedInstanceState.getInt(GOOD_KEY, 0);
            bad =  savedInstanceState.getInt(BAD_KEY, 0);

        } else {
            questionsAmount = sharedPreferences.getInt(getString(R.string.questions_amount_key), 10);
            good = 0;
            bad = 0;

        }

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        if(getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            gameView.startGame(questionsAmount, metrics.widthPixels * 8 / 10, metrics.widthPixels * 8 / 10);
        } else {
            gameView.startGame(questionsAmount, metrics.heightPixels * 3 / 10, metrics.heightPixels * 3 / 10);

        }
        gameView.setGameProcessListener(this);

        todo = questionsAmount;

        updateStats();
    }

    private void updateStats() {
        allText.setText(Integer.toString(todo));
        goodText.setText(Integer.toString(good));
        badText.setText(Integer.toString(bad));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(QUESTIONS_AMOUNT_KEY, todo);
        outState.putInt(GOOD_KEY, good);
        outState.putInt(BAD_KEY, bad);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        todo = savedInstanceState.getInt(QUESTIONS_AMOUNT_KEY);
        good = savedInstanceState.getInt(GOOD_KEY);
        bad = savedInstanceState.getInt(BAD_KEY);
    }

    @Override
    public void onAnswer(boolean correct) {
        final View animationView;
        todo--;
        if(correct) {
            good++;
            animationView = goodAnswerIcon;
        } else {
            bad++;
            animationView = badAnswerIcon;
        }

        updateStats();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.on_answer_animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                animationView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        animationView.setVisibility(View.VISIBLE);
        animationView.bringToFront();
        animationView.startAnimation(animation);
    }

    @Override
    public void finishGame() {
        Intent intent;
        int score = good - bad;
        if (daoScores.checkScore(score) && score > 0) {
            intent = new Intent(this, CongratulationsActivity.class);
            intent.putExtra("score",score);
            if(getIntent().hasExtra(FB_NAME_EXTRA_KEY)) {
                String fbName = getIntent().getStringExtra(FB_NAME_EXTRA_KEY);
                intent.putExtra(FB_NAME_EXTRA_KEY, fbName);
            }
        } else {
            intent = new Intent(this, YouCouldBeBetterDialog.class);
        }
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(todo == 0) {
            finish();
        }
    }
}
