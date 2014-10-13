package com.mercdev.shustanov.flagquiz.view.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.mercdev.shustanov.flagquiz.R;
import com.mercdev.shustanov.flagquiz.view.activity.MainActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class YouCouldBeBetterDialog extends Activity {

    @InjectView(R.id.ok_button)
    Button okButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_could_be_better_dialog);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.ok_button)
    void onOKButtonClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
