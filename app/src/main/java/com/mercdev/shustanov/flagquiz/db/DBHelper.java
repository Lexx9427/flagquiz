package com.mercdev.shustanov.flagquiz.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * alexander.shustanov on 10/13/2014.
 */
@Singleton
public class DBHelper extends SQLiteOpenHelper{




    @Inject
    public DBHelper(Context context, @Named("db_name") String name,  @Named("db_version") int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DAOScores.CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        if(i2 > 1) {
            throw new UnsupportedOperationException("Npt implemented yet");
        }
    }
}
