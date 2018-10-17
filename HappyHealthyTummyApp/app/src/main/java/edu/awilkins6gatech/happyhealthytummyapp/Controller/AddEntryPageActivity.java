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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import edu.awilkins6gatech.happyhealthytummyapp.Data.EntryDB;
import edu.awilkins6gatech.happyhealthytummyapp.Model.DiaryEntry;
import edu.awilkins6gatech.happyhealthytummyapp.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Dictionary;
import java.util.HashMap;

public class AddEntryPageActivity extends AppCompatActivity {

    Button postEntryButton;
    ImageView foodImage;
    Uri selectedImage;
    Bitmap bitmap;

    EditText title;
    EditText description;
    EditText calories;
    Switch happy;

    ArrayList<DiaryEntry> diaryEntries;

    EntryDB entriesDB;

    private static final String API_KEY = "NBBlANdKXJcyLoYBmNc1zRBNbDaISebIhU38c153";

    private static final String NUTRITION_DATA_REPO = "https://api.nal.usda.gov/ndb/V2/reports?type=f&format=json&api_key="+ API_KEY + "&ndbno=";
    private static final String NUTRITION_DATA_SEARCH = "https://api.nal.usda.gov/ndb/search/?format=json&sort=n&max=5&offset=0&api_key=" + API_KEY + "&q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        postEntryButton = (Button)(findViewById(R.id.postEntryButton));
        foodImage = (ImageView)(findViewById(R.id.foodImage));

        title = (EditText)(findViewById(R.id.title));
        description = (EditText)(findViewById(R.id.description));
        calories = (EditText)(findViewById(R.id.calories));
        happy = (Switch)(findViewById(R.id.switch1));

        diaryEntries = new ArrayList<DiaryEntry>();
        entriesDB = new EntryDB(this);

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

    public static HashMap<String, String> searchNutritionDB(String searchString) {
        HashMap<String, String> nameNdbnoPairs = new HashMap<>();
        try {
            JSONObject searchResultsJson = makeNutritionDBHttpRequest(NUTRITION_DATA_SEARCH + searchString);
            JSONArray searchResultsList = searchResultsJson.getJSONObject("list").getJSONArray("item");
            int arrayLength = searchResultsList.length();
            for (int i = 0; i < arrayLength; i++) {
                JSONObject item = searchResultsList.getJSONObject(i);
                String name = item.getString("name");
                String ndbno = item.getString("ndbno");
                nameNdbnoPairs.put(name, ndbno);
            }
        } catch (Exception ex) {
            //Handle exception
        }
        return nameNdbnoPairs;
    }

    public static JSONObject retrieveNutritionInfoFromDB(String ndbNumber) {
        JSONObject nutritionInfo = makeNutritionDBHttpRequest(NUTRITION_DATA_REPO + ndbNumber);
        return nutritionInfo;
    }

    private static JSONObject makeNutritionDBHttpRequest(String request){
        JSONObject response = null;
        try {
            URL url = new URL(request);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            String content = "";
            while ((inputLine = in.readLine()) != null) {
                content = content + inputLine;
            }
            in.close();
            con.disconnect();
            response = new JSONObject(content);
        }catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

}
