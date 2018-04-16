package edu.gatech.healthyhappytummy;

import java.io.Serializable;

/**
 * Created by Matt on 4/15/2018.
 */

public class FoodEntry implements Serializable{
    private String food_name;
    private int calories;
    private String description;
    private String file_uri;

    public FoodEntry(String food_name, int calories, String description, String file_uri) {
        this.food_name = food_name;
        this.calories = calories;
        this.description = description;
        this.file_uri = file_uri;
    }

    public String getFoodName() {
        return food_name;
    }

    public int getCalories() {
        return calories;
    }

    public String getDescription(){ return description; }

    public String getFileURI() { return file_uri; }

    @Override
    public String toString() {
        return food_name;
    }
}
