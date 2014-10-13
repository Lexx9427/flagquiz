package com.mercdev.shustanov.flagquiz.data;

import java.util.Date;

/**
 * alexander.shustanov on 10/13/2014.
 */
public class Score implements Comparable<Score>{
    private final String name;
    private final int score;
    private final Date date;

    public Score(String name, int scores, Date date) {
        this.name = name;
        this.score = scores;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int compareTo(Score score) {
        return score.score - this.score;
    }
}
