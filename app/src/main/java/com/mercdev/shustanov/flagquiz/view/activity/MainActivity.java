package com.mercdev.shustanov.flagquiz.view.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.mercdev.shustanov.flagquiz.R;
import com.mercdev.shustanov.flagquiz.view.InjectActivity;

import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends InjectActivity {

    private final String FB_NAME_EXTRA_KEY = "fb_name";
    @InjectView(R.id.facebook_login)
    LoginButton loginButton;

    String fbName = null;
    private final Session.StatusCallback callback = new Session.StatusCallback() {

        @Override
        public void call(Session session, SessionState state, Exception exception) {
            if(session.isOpened()) {
                Request.newMeRequest(session, new Request.GraphUserCallback() {

                    @Override
                    public void onCompleted(GraphUser user, Response response) {
                        if (user != null) {
                            fbName = user.getFirstName() + " " + user.getLastName();
                        }
                    }
                }).executeAsync();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.createAndInject(savedInstanceState, R.layout.activity_main);

        Session.openActiveSession(this, false, callback);
        loginButton.setSessionStatusCallback(callback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }

    @OnClick(R.id.start_quiz_button)
    void onStartQuizButtonClick() {
        Intent intent = new Intent(this, GameScreenActivity.class);
        if (fbName != null) {
            intent.putExtra(FB_NAME_EXTRA_KEY, fbName);
        }
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
