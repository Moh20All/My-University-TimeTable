package com.example.timetable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MyContract.MyEntry.TABLE_NAME + " (" +
                    MyContract.MyEntry._ID + " INTEGER PRIMARY KEY," +
                    MyContract.MyEntry.COLUMN_NAME_SEC_ID + " INTEGER," +
                    MyContract.MyEntry.COLUMN_NAME_GROUP_ID + " INTEGER," +
                    MyContract.MyEntry.COLUMN_NAME_LVL_ID + " INTEGER," +
                    MyContract.MyEntry.COLUMN_NAME_spec_ID + " INTEGER," +
                    MyContract.MyEntry.COLUMN_NAME_NAME + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MyContract.MyEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MyDatabase.db";

    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public boolean storedata(String name, int section, int group, int lvlId,int spec) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyContract.MyEntry.COLUMN_NAME_NAME, name);
        contentValues.put(MyContract.MyEntry.COLUMN_NAME_SEC_ID, section);
        contentValues.put(MyContract.MyEntry.COLUMN_NAME_GROUP_ID, group);
        contentValues.put(MyContract.MyEntry.COLUMN_NAME_LVL_ID, lvlId);
        contentValues.put(MyContract.MyEntry.COLUMN_NAME_spec_ID, spec);
        long result = db.insert(MyContract.MyEntry.TABLE_NAME, null, contentValues);
        db.close(); // Close the database after operation

        if (result == -1) {
            Log.e("StoreData", "Error inserting data into database");
            return false;
        } else {
            Log.d("StoreData", "Data inserted successfully");
            return true;
        }
    }


    public Cursor getdata() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + MyContract.MyEntry.TABLE_NAME, null);
        } catch (SQLiteException e) {
            Log.e("GetData", "Error fetching data from database: " + e.getMessage());
        }
        return cursor;
    }

    public boolean searchByGroupId(int groupId) {
        SQLiteDatabase db = this.getReadableDatabase(); // Access the database

        // Define a projection that specifies which columns from the database you will actually use after this query.
        String[] projection = { "groupId" };

        // Filter results WHERE "groupId" = 'groupId'
        String selection = "groupId" + " = ?";
        String[] selectionArgs = { String.valueOf(groupId) };

        Cursor cursor = db.query(
                "TABLE_NAME",   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null                    // The sort order
        );

        boolean found = cursor.getCount() > 0;
        cursor.close();
        return found;
    }
}
