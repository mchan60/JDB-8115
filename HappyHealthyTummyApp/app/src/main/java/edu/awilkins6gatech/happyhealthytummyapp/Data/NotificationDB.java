package edu.awilkins6gatech.happyhealthytummyapp.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import edu.awilkins6gatech.happyhealthytummyapp.Model.NotificationEntry;

/**
 * Created by Matt on 11/3/2018.
 */

public class NotificationDB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "notifications.db";

    public static final String NOTIFICATION_TABLE_NAME = "notification_table";
    public static final String NOTIF_ID = "NOTIF_ID";
    public static final String TIMESTAMP = "TIMESTAMP";
    public static final String MEAL = "MEAL";

    public NotificationDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + NOTIFICATION_TABLE_NAME +
                " (NOTIF_ID INTEGER PRIMARY KEY AUTOINCREMENT, TIMESTAMP, MEAL) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NOTIFICATION_TABLE_NAME);
        onCreate(db);
    }

    public void upgrade() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + NOTIFICATION_TABLE_NAME);
        onCreate(db);
    }

    public boolean addEntry(NotificationEntry notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIMESTAMP, notification.getTime());
        contentValues.put(MEAL, notification.getMeal());

        return db.insert(NOTIFICATION_TABLE_NAME, null, contentValues) != -1;
    }

    public boolean editEntry(NotificationEntry notification, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIMESTAMP, notification.getTime());
        contentValues.put(MEAL, notification.getMeal());
        long result = db.update(NOTIFICATION_TABLE_NAME, contentValues, NOTIF_ID + "=?",
                new String[]{Integer.toString(id)});

        return result != -1;
    }

    public boolean deleteEntry(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(NOTIFICATION_TABLE_NAME, NOTIF_ID + "=?",
                new String[]{Integer.toString(id)});

        return result != -1;
    }

    public List<NotificationEntry> getEntries() {
        List<NotificationEntry> entries = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + NOTIFICATION_TABLE_NAME, null);
        if (res.moveToFirst()) {
            do {
                NotificationEntry entry = new NotificationEntry();

                entry.setId(Integer.parseInt(res.getString(res.getColumnIndex(NOTIF_ID))));
                entry.setTime(res.getString(res.getColumnIndex(TIMESTAMP)));
                entry.setMeal(res.getString(res.getColumnIndex(MEAL)));

                entries.add(entry);

            } while (res.moveToNext());
        }
        return entries;
    }

    public int getMaxID() {
        return this.getReadableDatabase().rawQuery("SELECT MAX(NOTIF_ID) FROM " +
                NOTIFICATION_TABLE_NAME, null).getInt(1);
    }
}
