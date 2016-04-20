package com.example.s104522061.midterm_c;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sango on 2016/4/20.
 */
public class MyDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mydatabase.db";
    public static final int VERSION = 1;
    private static SQLiteDatabase database;

    public static final String TABLE_NAME = "mytable";
    public static final String KEY_ID = "_id";
    public static final String DAY_COLUMN = "day";
    public static final String SECTION_COLUMN = "section";
    public static final String NAME_COLUMN = "name";

    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = new MyDBHelper(context, DATABASE_NAME,
                    null, VERSION).getWritableDatabase();
        }

        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DAY_COLUMN + " INTEGER NOT NULL, " +
                        SECTION_COLUMN + " INTEGER NOT NULL, " +
                        NAME_COLUMN + " TEXT)";

        db.execSQL(sql);

        for(int i=0;i<5;i++) {
            for(int j=0;j<6;j++) {
                ContentValues cv = new ContentValues();
                cv.put("day", i);
                cv.put("section", j);
                cv.put("name", "");

                db.insert(TABLE_NAME, null, cv);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}
