package edu.gatech.healthyhappytummy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        Intent intent =  getIntent();
        String fileUri = intent.getStringExtra("File Uri");
    }
}
