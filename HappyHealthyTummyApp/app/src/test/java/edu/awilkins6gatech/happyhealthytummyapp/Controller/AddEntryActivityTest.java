package edu.awilkins6gatech.happyhealthytummyapp.Controller;

import edu.awilkins6gatech.happyhealthytummyapp.Model.DiaryEntry;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import static java.lang.Math.random;

public class AddEntryActivityTest {
    private DiaryEntry entry;

    @Test
    public void testJsonSaveAndLoad() {
        createEntryTest();
        readEntryTest();
        deleteEntryTest();
    }

    public void createEntryTest() {
        int calories = (int) random();
        String uri = Double.toString(random());
        entry = new DiaryEntry(uri, calories);
        entry = AddEntryActivity.createEntry(entry);
        File testFile = new File("Entries/" + entry.Timestamp + ".json");
        Assert.assertTrue("The file was not created",testFile.exists());
    }

    public void readEntryTest() {
        DiaryEntry entryFromFile = AddEntryActivity.readEntry(Long.toString(entry.Timestamp));
        Assert.assertEquals("Uri fields are not the same", entry.FileUri, entryFromFile.FileUri);
        Assert.assertEquals("Calorie fields are not the same", entry.Calories, entryFromFile.Calories);
        Assert.assertEquals("Timestamp fields are not the same", entry.Timestamp, entryFromFile.Timestamp);
    }

    public void deleteEntryTest() {
        DiaryEntry deletedEntry = AddEntryActivity.deleteEntry(Long.toString(entry.Timestamp));
        File testFile = new File("Entries/" + entry.Timestamp + ".json");
        Assert.assertFalse("The file was not deleted", testFile.exists());
    }
}