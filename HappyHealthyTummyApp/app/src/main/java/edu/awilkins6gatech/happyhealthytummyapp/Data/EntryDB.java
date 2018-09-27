package edu.awilkins6gatech.happyhealthytummyapp.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import edu.awilkins6gatech.happyhealthytummyapp.Model.DiaryEntry;

public class EntryDB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "entries.db";

    public static final String ENTRIES_TABLE_NAME = "entries_table";
    public static final String COL_1 = "IDL";
    public static final String COL_2 = "FILEURI";
    public static final String COL_3 = "CALORIES";
    public static final String COL_4 = "TIMESTAMP";
    public static final String COL_5 = "TITLE";
    public static final String COL_6 = "DESCRIPTION";
    public static final String COL_7 = "HAPPY";


    public EntryDB(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ENTRIES_TABLE_NAME + " (IDL INTEGER PRIMARY KEY AUTOINCREMENT, FILEURI, CALORIES, TIMESTAMP," +
                " TITLE, DESCRIPTION, HAPPY)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ENTRIES_TABLE_NAME);
        onCreate(db);
    }

    public boolean addEntry(DiaryEntry entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, entry.getFileUri());
        contentValues.put(COL_3, entry.getCalories());
        contentValues.put(COL_4, entry.getTimestamp());
        contentValues.put(COL_5, entry.getTitle());
        contentValues.put(COL_6, entry.getDescription());
        contentValues.put(COL_7, entry.getHappy());

        long result = db.insert(ENTRIES_TABLE_NAME, null, contentValues);

        return (result != -1);
    }

    public List<DiaryEntry> getEntries() {
        List<DiaryEntry> entries = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + ENTRIES_TABLE_NAME, null);
        if (res.moveToFirst()) {
            do {
                DiaryEntry entry = new DiaryEntry();

                entry.setFileUri(res.getString(res.getColumnIndex("FILEURI")));
                entry.setCalories((res.getInt(res.getColumnIndex("CALORIES"))));
                entry.setTimestamp(res.getString(res.getColumnIndex("TIMESTAMP")));
                entry.setTitle(res.getString(res.getColumnIndex("TITLE")));
                entry.setDescription(res.getString(res.getColumnIndex("DESCRIPTION")));
                entry.setHappy(res.getInt(res.getColumnIndex("HAPPY")));
                entries.add(entry);
            } while (res.moveToNext());
        }
        return entries;
    }

//    public Integer deleteEntry(String id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(ENTRIES_TABLE_NAME, "IDL = ?", new String[]{id});
//    }

    public DiaryEntry getEntry(String timestamp) {
        ArrayList<DiaryEntry> entries = new ArrayList<DiaryEntry>();
        entries = (ArrayList<DiaryEntry>) this.getEntries();
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).getTimestamp().equals(timestamp)) {
                return entries.get(i);
            }
        }
        return null;
    }

    public Integer deleteEntry(String timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ENTRIES_TABLE_NAME, "TIMESTAMP = ?", new String[]{timestamp});
    }
}


