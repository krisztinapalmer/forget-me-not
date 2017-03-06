package com.example.android.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ShoppingDataSource {
    private static final String TAG = "ShoppingDataSource";

    private SQLiteDatabase database;
    private ShoppingSQLiteOpenHelper dbHelper;
    ContentValues values;

    private static final String[] shoppingColumns = {
            ShoppingSQLiteOpenHelper.COLUMN_ID,
            ShoppingSQLiteOpenHelper.COLUMN_NAME,
            ShoppingSQLiteOpenHelper.COLUMN_QUANTITY,
            ShoppingSQLiteOpenHelper.COLUMN_IS_PURCHASED
    };

    public ShoppingDataSource(Context context) {
        dbHelper = new ShoppingSQLiteOpenHelper(context);
    }

    public void open() {
        try {
            database = dbHelper.getWritableDatabase();
        } catch (SQLException sqle) {
            Log.d(TAG, "Could not open the database! - " + sqle.getMessage());
        }
    }

    public void close() {
        database.close();
    }

    public long insert(String name, int quantity) {
        values = new ContentValues();
        values.put(ShoppingSQLiteOpenHelper.COLUMN_NAME, name);
        values.put(ShoppingSQLiteOpenHelper.COLUMN_QUANTITY, quantity);
        values.put(ShoppingSQLiteOpenHelper.COLUMN_IS_PURCHASED, ShoppingItem.NOT_PURCHASED);

        long id = database.insert(ShoppingSQLiteOpenHelper.TABLE_SHOPPING, null, values);

        Log.d(TAG, "Inserted person " + id + " into the database!");

        return id;
    }

    public void delete(ShoppingItem shoppingItem) {
        long id = shoppingItem.getId();

        database.delete(ShoppingSQLiteOpenHelper.TABLE_SHOPPING,
                ShoppingSQLiteOpenHelper.COLUMN_ID + " = ?", new String[] {Long.toString(id)});

        Log.d(TAG, "Shopping item " + id + " deleted");
    }

    public void deleteAll() {
        database.delete(ShoppingSQLiteOpenHelper.TABLE_SHOPPING, null, null);
    }

    public List<ShoppingItem> getAll() {
        List<ShoppingItem> items = new ArrayList<>();

        Cursor cursor = database.query(ShoppingSQLiteOpenHelper.TABLE_SHOPPING, shoppingColumns,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ShoppingItem item = cursorToShopping(cursor);
            items.add(item);
            cursor.moveToNext();
        }
        cursor.close();
        return items;
    }

    public ShoppingItem cursorToShopping(Cursor cursor) {
        ShoppingItem tempItem = new ShoppingItem(cursor.getString(1), cursor.getInt(2),
                cursor.getInt(3));
        tempItem.setId(cursor.getLong(0));
        return tempItem;
    }

    public void updateIsPurchased(ShoppingItem item) {
        int isPurchased = item.getIsPurchased();
        long id = item.getId();

        values = new ContentValues();
        values.put(ShoppingSQLiteOpenHelper.COLUMN_IS_PURCHASED, isPurchased);

        database.update(ShoppingSQLiteOpenHelper.TABLE_SHOPPING, values,
                ShoppingSQLiteOpenHelper.COLUMN_ID + " = ?", new String[] {Long.toString(id)});

        Log.d(TAG, "Shopping item " + id + " updated");
    }
}
