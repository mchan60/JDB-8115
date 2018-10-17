package edu.awilkins6gatech.happyhealthytummyapp;

import edu.awilkins6gatech.happyhealthytummyapp.Controller.AddEntryPageActivity;
import junit.framework.Assert;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;

@RunWith(JUnit4.class)
public class AddEntryPageActivityTest {
    private final String NDB_NUMBER = "01009";
    private final String NDB_NAME = "Cheese";

    @Test
    public void searchNutritionDBTest() {
        HashMap<String, String> listOfItems = AddEntryPageActivity.searchNutritionDB(NDB_NAME);
        try {
            boolean listOfResultsIsNotEmpty = !listOfItems.isEmpty();
            Assert.assertTrue("The known value \"Cheese\" returned no results.", listOfResultsIsNotEmpty);
        } catch (Exception ex){
            Assert.fail("An exception occurred.\n" + ex.getMessage());
        }
    }

    @Test
    public void retrieveNutritionInfoFromDBTest() {
        JSONObject nutritionInfo = AddEntryPageActivity.retrieveNutritionInfoFromDB(NDB_NUMBER);
        try {
            String retrievedNdbNumber = nutritionInfo.getJSONArray("foods").getJSONObject(0)
                    .getJSONObject("food").getJSONObject("desc").getString("ndbno");
            Assert.assertEquals("The wrong entry was pulled from the database", NDB_NUMBER, retrievedNdbNumber);
        } catch (Exception ex){
            Assert.fail("An exception occurred.\n" + ex.getMessage());
        }
    }
}
