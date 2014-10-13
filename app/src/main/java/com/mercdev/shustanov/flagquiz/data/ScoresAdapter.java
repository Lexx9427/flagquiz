package com.mercdev.shustanov.flagquiz.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mercdev.shustanov.flagquiz.R;
import com.mercdev.shustanov.flagquiz.db.DAOScores;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;

/**
 * alexander.shustanov on 10/13/2014.
 */
public class ScoresAdapter extends BaseAdapter {

    private final DAOScores daoScores;
    private final LayoutInflater inflater;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat();
    private List<Score> scores;

    @Inject
    public ScoresAdapter(DAOScores daoScores, LayoutInflater inflater) {
        this.daoScores = daoScores;
        this.inflater = inflater;
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        scores = daoScores.getHighScores();
    }

    @Override
    public int getCount() {
        return daoScores.getCount();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.scores_list_item, viewGroup, false);
        }
        Score score = scores.get(i);
        ((TextView) view.findViewById(R.id.name)).setText(score.getName());
        ((TextView) view.findViewById(R.id.date)).setText(dateFormat.format(score.getDate()));
        ((TextView) view.findViewById(R.id.score)).setText(String.valueOf(score.getScore()));
        return view;
    }
}
