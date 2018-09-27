package edu.awilkins6gatech.happyhealthytummyapp.Adapters;

import java.util.List;
import edu.awilkins6gatech.happyhealthytummyapp.Model.DiaryEntry;
import edu.awilkins6gatech.happyhealthytummyapp.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListViewAdapter extends ArrayAdapter<DiaryEntry> {

    Context context;

    public CustomListViewAdapter(Context context, int resourceId,
                                 List<DiaryEntry> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        DiaryEntry rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView); //REFERENCE TO THE IMAGE BEING DISPLAYED
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        //title for the image (optional)
        holder.txtTitle.setText(rowItem.getTitle());
        //this line is what sets the image to be displayed
        holder.imageView.setImageResource(R.drawable.ic_launcher_foreground);

        return convertView;
    }
}