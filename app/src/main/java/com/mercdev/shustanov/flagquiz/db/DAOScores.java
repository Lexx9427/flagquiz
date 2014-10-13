package com.mercdev.shustanov.flagquiz.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.mercdev.shustanov.flagquiz.data.Score;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
                    "date DATE ," +
                    "name TEXT " +
                    ");";

    private final DBHelper dbHelper;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat();

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
        Cursor cursor = dbHelper.getReadableDatabase().query(false, TABLE_NAME, new String[]{"score", "name", "date"}, null, null, null, null, null, "10");
        List<Score> scores = new ArrayList<Score>();
        int scoreIdx = cursor.getColumnIndex("score");
        int nameIdx = cursor.getColumnIndex("name");
        int dateIdx = cursor.getColumnIndex("date");

        while (cursor.moveToNext()) {
            try {
                scores.add(new Score(cursor.getString(nameIdx), cursor.getInt(scoreIdx), dateFormat.parse(cursor.getString(dateIdx))));
            } catch (ParseException e) {
                scores.add(new Score(cursor.getString(nameIdx), cursor.getInt(scoreIdx), new Date()));
            }
        }

        Collections.sort(scores);

        return scores;
    }

    public void insert(Score score) {
        ContentValues values = new ContentValues();
        values.put("name", score.getName());
        values.put("score", score.getScore());
        values.put("date", dateFormat.format(score.getDate()));
        dbHelper.getWritableDatabase().insert(TABLE_NAME, null, values);
    }
}
