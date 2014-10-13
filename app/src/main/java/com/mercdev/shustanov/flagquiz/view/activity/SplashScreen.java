package com.mercdev.shustanov.flagquiz.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.mercdev.shustanov.flagquiz.R;
import com.mercdev.shustanov.flagquiz.view.InjectActivity;

import butterknife.OnClick;

/**
 * SplashScreen
 * This activity is on the screen for one second or until user touch the screen.
 */
public class SplashScreen extends InjectActivity {

    private static final int FINISH_SPLASH_WHAT = 0;
    private static final int SECOND = 1000;

    private final Handler splashHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            finishSplashScreen();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.createAndInject(savedInstanceState, R.layout.activity_splash_screen);

        splashHandler.sendEmptyMessageDelayed(0, SECOND);
    }

    @OnClick(R.id.splash_screen_root_view)
    void onRootViewClick() {
        splashHandler.removeMessages(FINISH_SPLASH_WHAT);
        finishSplashScreen();
    }

    private void finishSplashScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
