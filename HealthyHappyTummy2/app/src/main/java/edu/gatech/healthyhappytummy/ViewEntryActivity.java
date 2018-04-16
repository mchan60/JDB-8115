package edu.gatech.healthyhappytummy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Matt on 4/16/2018.
 */

public class ViewEntryActivity extends AppCompatActivity{

    private ImageView entry_image;
    private TextView entry_name;
    private TextView entry_calories;
    private TextView entry_description;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entry);

        Intent in = getIntent();

        FoodEntry entry = (FoodEntry) in.getSerializableExtra("DIARY_ENTRY");

        entry_image = (ImageView) findViewById(R.id.entry_image);
        entry_image.setImageURI(Uri.parse(entry.getFileURI()));
        System.out.println(entry.getFileURI());

        entry_name = (TextView) findViewById(R.id.entry_name);
        entry_name.setText(entry.getFoodName());

        entry_calories = (TextView) findViewById(R.id.entry_calories);
        entry_calories.setText(Integer.toString(entry.getCalories()));

        entry_description = (TextView) findViewById(R.id.entry_description);
        entry_description.setText(entry.getDescription());
    }
}
