package com.mercdev.shustanov.flagquiz.view.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.mercdev.shustanov.flagquiz.R;
import com.mercdev.shustanov.flagquiz.view.InjectActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemSelected;

public class SettingsActivity extends InjectActivity {

    private final List<Integer> questionAmountVariants = Arrays.asList(5, 10, 15);
    @InjectView(R.id.questions_amount_spinner)
    Spinner questionAmountSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.createAndInject(savedInstanceState, R.layout.activity_settings);

        questionAmountSpinner.setAdapter(new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, questionAmountVariants));

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_key), MODE_PRIVATE);
        int questionsAmount = sharedPreferences.getInt(getString(R.string.questions_amount_key), 10);

        questionAmountSpinner.setSelection(questionAmountVariants.indexOf(questionsAmount));
    }

    @OnItemSelected(value = R.id.questions_amount_spinner, callback = OnItemSelected.Callback.ITEM_SELECTED)
    void onItemSelected(int position) {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_key), MODE_PRIVATE);
        sharedPreferences.edit().putInt(getString(R.string.questions_amount_key), questionAmountVariants.get(position)).apply();
    }
}
