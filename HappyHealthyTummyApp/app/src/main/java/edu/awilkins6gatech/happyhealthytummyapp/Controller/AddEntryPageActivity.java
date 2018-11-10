package edu.awilkins6gatech.happyhealthytummyapp.Controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import edu.awilkins6gatech.happyhealthytummyapp.AsyncTasks.MakeHttpRequestTask;
import edu.awilkins6gatech.happyhealthytummyapp.Data.EntryDB;
import edu.awilkins6gatech.happyhealthytummyapp.Model.DiaryEntry;
import edu.awilkins6gatech.happyhealthytummyapp.R;
import org.json.JSONArray;
import org.json.JSONObject;

public class AddEntryPageActivity extends AppCompatActivity {

    Button postEntryButton;
    ImageView foodImage;
    Uri selectedImage;
    Bitmap bitmap;

    AutoCompleteTextView title;
    EditText description;
    EditText calories;
    Switch happy;

    ArrayList<DiaryEntry> diaryEntries = new ArrayList<>();

    EntryDB entriesDB = new EntryDB(this);

    private static final String API_KEY = "NBBlANdKXJcyLoYBmNc1zRBNbDaISebIhU38c153";

    private static final String NUTRITION_DATA_REPO = "https://api.nal.usda.gov/ndb/V2/reports?type=f&format=json&api_key="+ API_KEY + "&ndbno=";
    private static final String NUTRITION_DATA_SEARCH = "https://api.nal.usda.gov/ndb/search/?format=json&sort=n&max=5&offset=0&api_key=" + API_KEY + "&q=";

    private static final String[] AUTOCOMPLETE_ENTRIES_TEMP = {"Apple", "Pear", "Cheese"};
    private static String[] autocompleteEntries = {};
    private static HashMap<String, String> entriesAndIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        postEntryButton = (Button)(findViewById(R.id.postEntryButton));
        foodImage = (ImageView)(findViewById(R.id.foodImage));


        description = (EditText)(findViewById(R.id.description));
        calories = (EditText)(findViewById(R.id.calories));
        happy = (Switch)(findViewById(R.id.switch1));

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, autocompleteEntries);

        title = findViewById(R.id.title);
        title.setThreshold(3);
        title.setAdapter(adapter);
        title.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                String autocompleteEntryTimestamp = entriesAndIds.get(selectedItem);
                DiaryEntry autocompleteEntry = entriesDB.getEntry(autocompleteEntryTimestamp);

                description.setText(autocompleteEntry.getDescription());
                calories.setText(Integer.toString(autocompleteEntry.getCalories()));
                happy.setChecked(autocompleteEntry.getHappy() == 1);
            }
        });
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                List<DiaryEntry> potentialEntries = entriesDB.getEntriesByName(s.toString());
                updateAutocompleteList(potentialEntries);
                adapter.notifyDataSetChanged();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        selectedImage = Uri.parse( (String) getIntent().getExtras().get("File Uri"));
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("bitmap not made");
        }
        try {
            foodImage.setImageBitmap(BitmapFactory.decodeStream(this.getContentResolver().openInputStream(selectedImage)));
            System.out.println("bitmap printed at addentry");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("couldn't print bitmap at addentry");
        }

        postEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDiaryEntry();
                Intent goToMainPage = new Intent(AddEntryPageActivity.this, MainPageActivity.class);
                goToMainPage.putExtra("File Uri", (String) getIntent().getExtras().get("File Uri"));
                startActivity(goToMainPage);
            }
        });

    }

    String newTitle, newDescription;
    int newCalories, newHappy;
    private void addDiaryEntry() {
        newTitle = title.getText().toString();
        newDescription = description.getText().toString();
        newCalories = Integer.parseInt(calories.getText().toString());

        if (happy.isChecked()) {
            newHappy = 1;
        } else {
            newHappy = 0;
        }
        DiaryEntry newDiaryEntry = new DiaryEntry((String) getIntent().getExtras().get("File Uri"),newCalories,
                (String) getIntent().getExtras().get("Time Stamp"), newTitle, newDescription, newHappy);
        diaryEntries.add(newDiaryEntry);
        entriesDB.addEntry(newDiaryEntry);
    }

    private void updateAutocompleteList(List<DiaryEntry> entries) {
        entriesAndIds = new HashMap<>();
        for (DiaryEntry d : entries) {
            entriesAndIds.put(d.getTitle(), d.getTimestamp());
        }
        Set<String> autocompleteEntriesSet = entriesAndIds.keySet();
        autocompleteEntries = autocompleteEntriesSet.toArray(new String[autocompleteEntriesSet.size()]);
    }

    public static HashMap<String, String> searchNutritionDB(String searchString) {
        HashMap<String, String> nameNdbnoPairs = new HashMap<>();
        try {
            MakeHttpRequestTask asyncTask = new MakeHttpRequestTask();
            JSONObject searchResultsJson = (asyncTask.execute(NUTRITION_DATA_SEARCH + searchString).get())[0];
            JSONArray searchResultsList = searchResultsJson.getJSONObject("list").getJSONArray("item");
            int arrayLength = searchResultsList.length();
            for (int i = 0; i < arrayLength; i++) {
                JSONObject item = searchResultsList.getJSONObject(i);
                String name = item.getString("name");
                String ndbno = item.getString("ndbno");
                nameNdbnoPairs.put(name, ndbno);
            }
        } catch (Exception ex) {
            Exception exception = ex;
            //log exception
        }
        return nameNdbnoPairs;
    }

    public static JSONObject retrieveNutritionInfoFromDB(String ndbNumber) {
        MakeHttpRequestTask asyncTask = new MakeHttpRequestTask();
        JSONObject nutritionInfo = null;
        try {
            nutritionInfo = (asyncTask.execute(NUTRITION_DATA_REPO + ndbNumber).get())[0];
        } catch (Exception ex) {
            Exception exception = ex;
            //log exception
        }
        return nutritionInfo;
    }



}
