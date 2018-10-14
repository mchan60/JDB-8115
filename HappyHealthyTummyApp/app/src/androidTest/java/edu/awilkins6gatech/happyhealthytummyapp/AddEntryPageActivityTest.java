package edu.awilkins6gatech.happyhealthytummyapp;

import edu.awilkins6gatech.happyhealthytummyapp.Controller.AddEntryPageActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AddEntryPageActivityTest {
    private final String NDB_NUMBER = "01009";
    private final String NDB_NAME = "Cheese";

    @Test
    public void searchNutritionDBTest() {
        JSONObject listOfItems = AddEntryPageActivity.searchNutritionDB(NDB_NAME);
        try {
            JSONArray list = listOfItems.getJSONObject("list").getJSONArray("item");
        } catch (Exception ex){
            System.out.println("An exception occurred");
        }
    }
}
