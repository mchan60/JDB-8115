package edu.awilkins6gatech.happyhealthytummyapp.Controller;

import android.net.Uri;

import org.junit.Assert;

import java.io.File;

import edu.awilkins6gatech.happyhealthytummyapp.Model.DiaryEntry;

import static java.lang.Math.random;
import static org.junit.Assert.*;

public class LandingPageActivityTest {

    private File entry;
    public void createEntryTest() {
        int calories = (int) random();
        String uri = Double.toString(random());
        //entry = new DiaryEntry(uri, calories);
       // entry = AddEntryActivity.createEntry(entry);
       /// File testFile = new File("Happy Healthy Tummy/" + entry.getTimestamp() + ".json");
       // Assert.assertTrue("The file was not created",testFile.exists());
    }
}