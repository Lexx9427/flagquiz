package com.mercdev.shustanov.flagquiz.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.common.collect.ImmutableMap;
import com.mercdev.shustanov.flagquiz.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;

public class GameView extends LinearLayout {

    private static final Map<Integer, Integer> flagToCountry = ImmutableMap.<Integer, Integer>builder()
            .put(R.drawable.czeh, R.string.czeh)
            .put(R.drawable.dnr, R.string.dnr)
            .put(R.drawable.lnr, R.string.lnr)
            .put(R.drawable.nicaragua, R.string.nicaragua)
            .build();

    private static final List<Integer> flags = Arrays.asList(
            R.drawable.czeh,
            R.drawable.dnr,
            R.drawable.lnr,
            R.drawable.nicaragua);

    private static final List<Integer> countries = Arrays.asList(
            R.string.dnr,
            R.string.lnr,
            R.string.russia,
            R.string.usa,
            R.string.belgium,
            R.string.Chine,
            R.string.czeh,
            R.string.nicaragua
    );

    @InjectView(R.id.current_flag_image_view)
    ImageView currentFlag;

    @InjectViews({R.id.first, R.id.second, R.id.third, R.id.fourth})
    Button[] buttons;

    private GameProcessListener gameProcessListener;
    private final Context context;
    /**
     * Questions count down
     */
    private int todo;
    /**
     * Id of current flag
     */
    private int rightFlagId;
    /**
     * Id of previous flag
     */
    private int lastFlagId = -1;


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.game_view_layout, this, true);
        ButterKnife.inject(this);

    }

    public void startGame(int todo, int width, int height) {
        this.todo = todo;
        currentFlag.setMinimumWidth(width);
        currentFlag.setMinimumHeight(height);
        currentFlag.setMaxWidth(width);
        currentFlag.setMaxHeight(height);

        nextQuestion();
    }

    private void nextQuestion() {
        rightFlagId = getRandomFlag();
        currentFlag.setImageDrawable(getResources().getDrawable(rightFlagId));

        Set<Integer> countriesSet = new HashSet<Integer>();

        countriesSet.add(flagToCountry.get(rightFlagId));

        List<Integer> availableCountries = new ArrayList<Integer>(countries);

        while (countriesSet.size() != 4) {
            int i = nextInt(availableCountries.size());
            countriesSet.add(availableCountries.get(i));
            availableCountries.remove(i);
        }

        configButtons(countriesSet);
    }

    private void configButtons(Set<Integer> countriesSet) {
        List<Integer> countries = new ArrayList<Integer>(countriesSet);
        Collections.shuffle(countries);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText(countries.get(i));
        }
    }

    private int getRandomFlag() {
        return flags.get(nextInt(flags.size()));
    }

    private static int nextInt(int size) {
        if (size != 0) {
            return new Random().nextInt(size);
        } else {
            return 0;
        }
    }


    @OnClick({R.id.first, R.id.second, R.id.third, R.id.fourth})
    void onChooseAnswer(Button button) {
        if (todo!=0) {
            todo--;
            if (gameProcessListener != null) {
                if(button.getText().equals(getResources().getText(flagToCountry.get(rightFlagId)))) {
                   gameProcessListener.onAnswer(true);
                } else {
                    gameProcessListener.onAnswer(false);
                }
            }
            if(todo!=0) {
                nextQuestion();
            } else {
                if(gameProcessListener != null) {
                    gameProcessListener.finishGame();
                }
            }
        } else {
            //do nothing
        }
    }

    public void setGameProcessListener(GameProcessListener gameProcessListener) {
        this.gameProcessListener = gameProcessListener;
    }

    public static interface GameProcessListener {
        void onAnswer(boolean correct);

        void finishGame();
    }


}
