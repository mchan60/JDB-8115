package edu.gatech.healthyhappytummy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddEntryActivity extends AppCompatActivity {

    private Button addButton;
    private Button cancelButton;
    private EditText food_name;
    private EditText calories;
    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        Intent intent =  getIntent();
        final String fileUri = intent.getStringExtra("File Uri");

        food_name = (EditText) findViewById(R.id.food_name_field);
        calories = (EditText) findViewById(R.id.calories_field);
        description = (EditText) findViewById(R.id.description_field);

        addButton = (Button) findViewById(R.id.add_entry_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFoodEntry(food_name.getText().toString(), calories.getText().toString(), description.getText().toString(), fileUri);
            }
        });
        cancelButton = (Button) findViewById(R.id.cancel_entry_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddEntryActivity.this, MainActivity.class));
            }
        });
    }

    private void createFoodEntry(String name, String cal, String des, String uri) {
        name = name.isEmpty() ? "N/A" : name;
        cal = cal.isEmpty() ? "0" : cal;
        des = des.isEmpty() ? "N/A" : des;
        FoodEntry foodEntry = new FoodEntry(name, Integer.parseInt(cal), des, uri);
        Intent in = new Intent(AddEntryActivity.this, DiaryActivity.class);
        in.putExtra("entry", foodEntry);
        startActivity(in);
    }
}
