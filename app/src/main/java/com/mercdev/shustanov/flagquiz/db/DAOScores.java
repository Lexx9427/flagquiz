package com.mercdev.shustanov.flagquiz.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.mercdev.shustanov.flagquiz.data.Score;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * alexander.shustanov on 10/13/2014.
 */
@Singleton
public class DAOScores {

    public static final String TABLE_NAME = "scores";
    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "score INTEGER ," +
                    "name TEXT " +
                    ");";

    private final DBHelper dbHelper;

    @Inject
    public DAOScores(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public boolean checkScore(int score) {
        Cursor cursor = dbHelper.getReadableDatabase().query(false, TABLE_NAME, new String[]{"score"}, "score > ?", new String[]{String.valueOf(score)}, null, null, null, "10");
        if (cursor == null || cursor.getCount() < 10) {
            return true;
        }
        return false;
    }

    public int getCount() {
        int count = dbHelper.getReadableDatabase().query(false, TABLE_NAME, new String[]{"score"}, null, null, null, null, "score DESC", "10").getCount();
        return count > 10 ? 10 : count;
    }

    public List<Score> getHighScores() {
        Cursor cursor = dbHelper.getReadableDatabase().query(false, TABLE_NAME, new String[]{"score", "name"}, null, null, null, null, null, "10");
        List<Score> scores = new ArrayList<Score>();
        int scoreIdx = cursor.getColumnIndex("score");
        int nameIdx = cursor.getColumnIndex("name");

        while (cursor.moveToNext()) {
            scores.add(new Score(cursor.getString(nameIdx), cursor.getInt(scoreIdx)));
        }

        Collections.sort(scores);

        return scores;
    }

    public void insert(Score score) {
        ContentValues values = new ContentValues();
        values.put("name", score.getName());
        values.put("score", score.getScore());
        dbHelper.getWritableDatabase().insert(TABLE_NAME, null, values);
    }
}
