package edu.awilkins6gatech.happyhealthytummyapp.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import edu.awilkins6gatech.happyhealthytummyapp.R;

public class AddEntryPageActivity extends AppCompatActivity {

    Button postEntryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        postEntryButton = (Button)(findViewById(R.id.postEntryButton));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        postEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToMainPage = new Intent(AddEntryPageActivity.this, MainPageActivity.class);
                goToMainPage.putExtra("File Uri", (String) getIntent().getExtras().get("File Uri"));
                startActivity(goToMainPage);
            }
        });

    }

}
