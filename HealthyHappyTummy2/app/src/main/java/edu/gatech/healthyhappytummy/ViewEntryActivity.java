package edu.gatech.healthyhappytummy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
        Uri image_uri = Uri.parse(entry.getFileURI());
        entry_image = (ImageView) findViewById(R.id.entry_image);
        Bitmap myImg = BitmapFactory.decodeFile(image_uri.getPath());
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotated = Bitmap.createBitmap(myImg, 0, 0, myImg.getWidth(), myImg.getHeight(),
                matrix, true);
        entry_image.setImageBitmap(rotated);
//        entry_image.setImageURI(Uri.parse(entry.getFileURI()));
        System.out.println(entry.getFileURI());

        entry_name = (TextView) findViewById(R.id.entry_name);
        entry_name.setText(entry.getFoodName());

        entry_calories = (TextView) findViewById(R.id.entry_calories);
        entry_calories.setText(Integer.toString(entry.getCalories()));

        entry_description = (TextView) findViewById(R.id.entry_description);
        entry_description.setText(entry.getDescription());
    }
}
