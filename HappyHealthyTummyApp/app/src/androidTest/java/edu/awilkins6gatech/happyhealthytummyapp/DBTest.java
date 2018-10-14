package edu.awilkins6gatech.happyhealthytummyapp;

import edu.awilkins6gatech.happyhealthytummyapp.Data.EntryDB;
import edu.awilkins6gatech.happyhealthytummyapp.Model.DiaryEntry;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static java.lang.Math.random;

@RunWith(JUnit4.class)
public class DBTest extends BaseTestClass {
    private static EntryDB dataBase;
    private static DiaryEntry entry1;
    private static DiaryEntry entry2;
    private static DiaryEntry entry3;

    @BeforeClass
    public static void setup() {
        dataBase = new EntryDB(appContext);
        entry1 = createRandomEntry();
        entry2 = createRandomEntry();
        entry3 = createRandomEntry();
        dataBase.addEntry(entry1);
        dataBase.addEntry(entry2);
        dataBase.addEntry(entry3);
    }

    @Test
    public void testCreateOperation() {
        DiaryEntry entry = createRandomEntry();
        boolean entryAdded = dataBase.addEntry(entry);
        Assert.assertTrue("Entry was not added successfully", entryAdded);
    }

    @Test
    public void testReadOperation() {
        DiaryEntry retrievedEntry1 = dataBase.getEntry(entry1.getTimestamp());
        Assert.assertEquals("The entry1 from the database is not correct", entry1, retrievedEntry1);
    }

    @Test
    public void testReadAllOperation() {
        List<DiaryEntry> retrievedEntries = dataBase.getEntries();
        boolean listContainsEntry1 = retrievedEntries.contains(entry1);
        Assert.assertTrue("List does not contain Entry1", listContainsEntry1);
        boolean listContainsEntry2 = retrievedEntries.contains(entry2);
        Assert.assertTrue("List does not contain Entry2", listContainsEntry2);
        boolean listContainsEntry3 = retrievedEntries.contains(entry3);
        Assert.assertTrue("List does not contain Entry3", listContainsEntry3);
    }

    @Test
    public void testUpdateOperation() {
        DiaryEntry updatedEntry1 = entry1;
        updatedEntry1.setCalories((int) random());
        while (updatedEntry1.getCalories() == entry1.getCalories()) {
            updatedEntry1.setCalories((int) random());
        }
        boolean editedSuccessfully = dataBase.editEntry(updatedEntry1);
        Assert.assertTrue("Entry was not edited successfully", editedSuccessfully);
    }

    @Test
    public void testDeleteOperation() {
        boolean deletedSuccessfully = dataBase.deleteEntry(entry1.getTimestamp());
        Assert.assertTrue("The entry was not deleted successfully", deletedSuccessfully);
    }

    private static DiaryEntry createRandomEntry() {
        int calories = (int) random();
        String stringUri = Double.toString(random());
        long timestamp = System.currentTimeMillis();
        System.out.println(timestamp);
        String title = "";
        String description = "";
        int happy = 1;
        DiaryEntry randomEntry = new DiaryEntry(stringUri, calories, Long.toString(timestamp), title, description, happy);
        return randomEntry;
    }
}
