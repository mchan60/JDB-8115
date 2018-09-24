package edu.awilkins6gatech.happyhealthytummyapp.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import edu.awilkins6gatech.happyhealthytummyapp.Model.DiaryEntry;
import edu.awilkins6gatech.happyhealthytummyapp.R;
/*
* Custom adapter class to display diary images on main page
*
*
* author: jperry45*/
public class EntriesAdapter<String> extends ArrayAdapter<String> {

    private ArrayList<String> items;

    public EntriesAdapter(Context context, int textViewResourceId, ArrayList<String> items) {
        super(context, textViewResourceId, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.entry_list_page, null);
        }

        ImageView iv = (ImageView) v.findViewById(R.id.entry_image_template);

        return v;
    }
}
