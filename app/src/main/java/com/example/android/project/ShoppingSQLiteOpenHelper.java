package com.example.android.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ShoppingSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "ShoppingHelper";

    private static final String DATABASE_NAME = "shopping.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_SHOPPING = "shopping";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_IS_PURCHASED = "isPurchased";

    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_SHOPPING + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_QUANTITY + " TEXT NOT NULL, "
            + COLUMN_IS_PURCHASED + " INTEGER NOT NULL);";

    public ShoppingSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING);
        onCreate(db);
        Log.d(TAG, "Database upgraded!");
    }
}
