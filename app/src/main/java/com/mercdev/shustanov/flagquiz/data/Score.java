package com.mercdev.shustanov.flagquiz.data;

/**
 * alexander.shustanov on 10/13/2014.
 */
public class Score implements Comparable<Score>{
    private final String name;
    private final int score;

    public Score(String name, int scores) {
        this.name = name;
        this.score = scores;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(Score score) {
        return score.score - this.score;
    }
}
