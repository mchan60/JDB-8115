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

    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "entries.db";

    public static final String ENTRIES_TABLE_NAME = "table";
    public static final String ENTRYID = "ENTRYID";
    public static final String FILEURI = "FILEURI";
    public static final String CALORIES = "CALORIES";
    public static final String TIMESTAMP = "TIMESTAMP";
    public static final String TITLE = "TITLE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String HAPPY = "HAPPY";


    public EntryDB(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + ENTRIES_TABLE_NAME + "(" + ENTRYID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FILEURI+ " TEXT, " + CALORIES + " TEXT , " + TIMESTAMP +  " TEXT, " + TITLE + " TEXT , " + DESCRIPTION + " TEXT , " + HAPPY +  " TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ENTRIES_TABLE_NAME);
        if (newVersion > oldVersion) {
            db.execSQL("ALTER TABLE " + ENTRIES_TABLE_NAME + " ADD COLUMN "+ ENTRYID + " INTEGER DEFAULT 0");
        }
        onCreate(db);
    }

    public boolean addEntry(DiaryEntry entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ENTRYID, "10");
        contentValues.put(FILEURI, entry.getFileUri());
        contentValues.put(CALORIES, entry.getCalories());
        contentValues.put(TIMESTAMP, entry.getTimestamp());
        contentValues.put(TITLE, entry.getTitle());
        contentValues.put(DESCRIPTION, entry.getDescription());
        contentValues.put(HAPPY, entry.getHappy());

//        Cursor res = db.rawQuery("SELECT * FROM " + ENTRIES_TABLE_NAME, null);
//
//        System.out.print(res.getInt(res.getColumnIndex("HAPPY")));


        long result = db.insert(ENTRIES_TABLE_NAME, null, contentValues);

        return (result != -1);
    }

    public boolean editEntry(DiaryEntry entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, entry.getTitle());
        contentValues.put(DESCRIPTION, entry.getDescription());
        contentValues.put(CALORIES, entry.getCalories());
        contentValues.put(HAPPY, entry.getHappy());
        long result = db.update(ENTRIES_TABLE_NAME, contentValues, "ENTRYID = ?", new String[]{entry.getEntryID()});
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
//        int returnedValue = db.delete(ENTRIES_TABLE_NAME, "TIMESTAMP = ?", new String[]{timestamp})
//        System.out.print(returnedValue);
        return db.delete(ENTRIES_TABLE_NAME, "TIMESTAMP = ?", new String[]{timestamp});
    }


}


