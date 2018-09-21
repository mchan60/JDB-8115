package edu.awilkins6gatech.happyhealthytummyapp.Controller;

import android.net.Uri;

import edu.awilkins6gatech.happyhealthytummyapp.Model.DiaryEntry;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static java.lang.Math.random;

public class DiaryEntryTest {
    private DiaryEntry entry;
    private DiaryEntry entry2;

    @Test
    public void testJsonSaveAndLoad() {
        createEntryTest();
        readEntryTest();
        entry2 = new DiaryEntry();
        readEntriesTest();
        deleteEntryTest();
        entry2.deleteEntry();
    }

    public void createEntryTest() {
        int calories = (int) random();
        String stringUri = Double.toString(random());
        long timestamp = System.currentTimeMillis();
        System.out.println(timestamp);
        String title = "";
        String description = "";
        boolean happy = true;
        entry = new DiaryEntry(null, calories, Long.toString(timestamp), title, description, happy);
        entry.createEntry(entry);
        File testFile = new File("Entries/" + entry.getTimestamp() + ".json");
        Assert.assertTrue("The file was not created",testFile.exists());
    }

    public void readEntryTest() {
        DiaryEntry entryFromFile = DiaryEntry.readEntry(entry.getTimestamp() + ".json");
        Assert.assertEquals("Uri fields are not the same", entry.getFileUri(), entryFromFile.getFileUri());
        Assert.assertEquals("Calorie fields are not the same", entry.getCalories(), entryFromFile.getCalories());
        Assert.assertEquals("Timestamp fields are not the same", entry.getTimestamp(), entryFromFile.getTimestamp());
    }

    public void readEntriesTest() {
        List<DiaryEntry> entriesList = DiaryEntry.getDiaryEntries();
        Assert.assertTrue("Entry 1 is missing from the list", entriesList.contains(entry));
        Assert.assertTrue("Entry 2 is missing from the list", entriesList.contains(entry2));
    }

    public void deleteEntryTest() {
        DiaryEntry deletedEntry = entry.deleteEntry();
        File testFile = new File("Entries/" + entry.getTimestamp() + ".json");
        Assert.assertFalse("The file was not deleted", testFile.exists());
    }
}