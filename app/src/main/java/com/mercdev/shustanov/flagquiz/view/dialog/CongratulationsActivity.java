package com.mercdev.shustanov.flagquiz.view.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;

import com.mercdev.shustanov.flagquiz.FlagQuizApp;
import com.mercdev.shustanov.flagquiz.R;
import com.mercdev.shustanov.flagquiz.data.Score;
import com.mercdev.shustanov.flagquiz.db.DAOScores;
import com.mercdev.shustanov.flagquiz.view.InjectActivity;
import com.mercdev.shustanov.flagquiz.view.activity.MainActivity;
import com.mercdev.shustanov.flagquiz.view.activity.ScoresActivity;

import java.util.Date;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class CongratulationsActivity extends InjectActivity {

    @InjectView(R.id.ok_button)
    Button okButton;

    @InjectView(R.id.name_edit)
    EditText nameEdit;

    @Inject
    DAOScores daoScores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.createAndInject(savedInstanceState, R.layout.activity_congratulations);
        ((FlagQuizApp) getApplicationContext()).inject(this);
    }

    @OnClick(R.id.ok_button)
    void onOKButtonClick() {
        int score = getIntent().getIntExtra("score", 0);
        daoScores.insert(new Score(nameEdit.getText().toString(), score, new Date()));
        Intent intent = new Intent(this, ScoresActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @OnEditorAction(R.id.name_edit)
    boolean onEditNameKeyPressed(KeyEvent key) {
        if(key.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            onOKButtonClick();
            return false;
        }
        return true;
    }
}
