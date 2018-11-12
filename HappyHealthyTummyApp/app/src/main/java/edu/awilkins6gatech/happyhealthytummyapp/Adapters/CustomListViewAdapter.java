package edu.awilkins6gatech.happyhealthytummyapp.Adapters;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import edu.awilkins6gatech.happyhealthytummyapp.Model.DiaryEntry;
import edu.awilkins6gatech.happyhealthytummyapp.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomListViewAdapter extends ArrayAdapter<DiaryEntry> {

    Context context;
    private List<DiaryEntry> items;

    public CustomListViewAdapter(Context context, int resourceId,
                                 List<DiaryEntry> items) {
        super(context, resourceId, items);
        this.context = context;
        this.items = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView timeStamp;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        View view = convertView;
        DiaryEntry rowItem = getItem(position);
        System.out.println("in custom adapter, view was null");
        if (view == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.list_item, parent, false);
            holder.txtTitle = (TextView) view.findViewById(R.id.title);
            holder.timeStamp = (TextView) view.findViewById(R.id.time);
            holder.imageView = (ImageView) view.findViewById(R.id.icon); //REFERENCE TO THE IMAGE BEING DISPLAYED
        }
        holder.txtTitle = (TextView) view.findViewById(R.id.title);
        holder.imageView = (ImageView) view.findViewById(R.id.icon);
        holder.timeStamp = (TextView) view.findViewById(R.id.time);

        view.setTag(holder);
        System.out.println("in custom adapter, view was STILL null");


        //title for the image (optional)
        if (rowItem != null) {
            if (rowItem.getTitle() != null) {
                holder.txtTitle.setText(rowItem.getTitle());
            } else {
                holder.txtTitle.setText("no title");
            }
            if (rowItem.getTimestamp() != null) {
                holder.timeStamp.setText(rowItem.getTimestamp());
            } else {
                holder.timeStamp.setText("no time");
            }
            //this line is what sets the image to be displayed
            try {
                holder.imageView.setImageBitmap(BitmapFactory.decodeStream(context.getContentResolver().openInputStream(Uri.parse(rowItem.getFileUri()))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return view;
    }
}