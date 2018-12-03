package edu.awilkins6gatech.happyhealthytummyapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.awilkins6gatech.happyhealthytummyapp.Controller.CalendarActivity;
import edu.awilkins6gatech.happyhealthytummyapp.Controller.MainPageActivity;
import edu.awilkins6gatech.happyhealthytummyapp.Controller.ViewEntryActivity;
import edu.awilkins6gatech.happyhealthytummyapp.Data.EntryDB;
import edu.awilkins6gatech.happyhealthytummyapp.Model.DiaryEntry;
import edu.awilkins6gatech.happyhealthytummyapp.R;

public class MonthlyFragment extends Fragment implements AdapterView.OnItemClickListener {
    CalendarView mCalendarView;
    EntryDB entryDB;
    List<DiaryEntry> diaryEntryList;
    DiaryEntry selectedEntry;
    List<DiaryEntry> selectedEntries;
    ArrayList<Integer> selectedIndices;
    TextView testTextView;
    FloatingActionButton searchButton;
    String currentDate;
    ListView listView;
    int currentDay;
    int currentMonth;
    int currentYear;
    String currentMonthName;

    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        DailyFragment tabFragment = new DailyFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_calendar);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        View view  = inflater.inflate(R.layout.fragment_monthly, container, false);

        entryDB = new EntryDB(getActivity());
        diaryEntryList = entryDB.getEntries();
        selectedEntries = new ArrayList<>();
        selectedIndices = new ArrayList<>();

        if (diaryEntryList.size() > 0) {
            selectedEntry = diaryEntryList.get(0);
        }

        listView = (ListView) view.findViewById(R.id.listView);
        mCalendarView = (CalendarView) view.findViewById(R.id.calendarView);
        testTextView = (TextView) view.findViewById(R.id.title);
        searchButton = (FloatingActionButton) view.findViewById(R.id.searchButton);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent goToViewEntryPage = new Intent(getActivity(), ViewEntryActivity.class);
                goToViewEntryPage.putExtra("DIARY_ENTRY", ((int) l));
                goToViewEntryPage.putExtra("TIMESTAMP", selectedEntries.get((int) l).getTimestamp());
                startActivity(goToViewEntryPage);
            }
        });


        //selecting the image by date
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                currentDate = new SimpleDateFormat("yyyyMMMddEEEE_HHmmss", Locale.getDefault()).format(new Date(mCalendarView.getDate()));
                currentDay = dayOfMonth;
                currentMonth = month;
                currentYear = year;
                switch(currentMonth) {
                    case 01:
                        currentMonthName = "Jan";
                    case 02:
                        currentMonthName = "Feb";
                    case 03:
                        currentMonthName = "Mar";
                    case 04:
                        currentMonthName = "Apr";
                    case 05:
                        currentMonthName = "May";
                    case 06:
                        currentMonthName = "Jun";
                    case 07:
                        currentMonthName = "Jul";
                    case 8:
                        currentMonthName = "Aug";
                    case 9:
                        currentMonthName = "Sep";
                    case 10:
                        currentMonthName = "Oct";
                    case 11:
                        currentMonthName = "Nov";
                    case 12:
                        currentMonthName = "Dec";
                }
                //System.out.print(year + " " + month + " " + dayOfMonth);
            }

        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedEntries = getSelectedEntries();
                if (selectedEntries.size() > 0) {
                    testTextView.setText(selectedEntries.size() + " results found");
                } else {
                    testTextView.setText("No results found");
                    System.out.println("not selecting ENTRIES !!!!!");
                }
                CustomListViewAdapter adapter = new CustomListViewAdapter(getActivity(),
                        R.id.diaryEntries, selectedEntries);
                listView.setAdapter(adapter);

                System.out.println(selectedIndices.size());
            }
        });

        if (selectedEntries.size() > 0) {
            testTextView.setText(selectedEntries.get(selectedEntries.size() - 1).getTitle());
        } else {
            System.out.println("not selecting ENTRIES !!!!!");
        }


       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return view;
    }

    public List<DiaryEntry> getSelectedEntries() {
        selectedEntries = new ArrayList<>();
        selectedIndices = new ArrayList<>();

        for (int i = 0; i < selectedIndices.size(); i++) {
            entryDB.deleteSelection(String.valueOf(selectedIndices.get(i)));
        }
        selectedIndices.clear();

        if (selectedEntries.size() > 0) {
            selectedEntries.clear();
        }


        //in on date listener, 0 represents current day
        if (currentDay == 0) {
            currentDate = new SimpleDateFormat("yyyyMMddEEEE_HHmmss", Locale.getDefault()).format(new Date(mCalendarView.getDate()));
            System.out.println(currentDate);
            currentDay = Integer.parseInt(currentDate.substring(6, 8));
            currentMonth = Integer.parseInt(currentDate.substring(4, 6));
            currentYear = Integer.parseInt(currentDate.substring(0, 4));
        }

        System.out.println(currentDay);
        System.out.println(currentYear);
        System.out.println(currentMonth);
        for (int i = 0; i < diaryEntryList.size(); i++) {
            if (Integer.parseInt(diaryEntryList.get(i).getYear()) == currentYear

                    && Integer.parseInt(diaryEntryList.get(i).getDayNumber()) == currentDay) {
                selectedEntries.add(diaryEntryList.get(i));
                selectedIndices.add(i);
            }
        }
        if (selectedIndices.size() > 0) {
            System.out.println(selectedIndices.get(0));
        } else {
            System.out.println("no selection");
        }

        for (int i = 0; i < selectedIndices.size(); i++) {
            entryDB.addSelection(selectedIndices.get(i));
        }
        return selectedEntries;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}


class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private EntryDB entryDB;
    private List<DiaryEntry> diaryEntryList;
    private List<Integer> selectedEntriesIndices;

    public ImageAdapter(Context c) {
        mContext = c;
        entryDB = new EntryDB(mContext);
        diaryEntryList = entryDB.getEntries();
        selectedEntriesIndices = entryDB.getSelectedEntriesIndices();
    }

    public int getCount() {

        return selectedEntriesIndices.size();
    }

    public Object getItem(int position) {
        return diaryEntryList.get((int) getItemId(position));
    }

    public long getItemId(int position) {
        return selectedEntriesIndices.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
        } else {
            imageView = (ImageView) convertView;
        }

        if (selectedEntriesIndices.size() < 0) {
            imageView = null;
        }
        try {
            if (position < selectedEntriesIndices.size() && selectedEntriesIndices.size() > 0) {
                imageView.setImageBitmap(BitmapFactory.decodeStream(
                        mContext.getContentResolver().openInputStream(Uri.parse(diaryEntryList.get(selectedEntriesIndices.get(position)).getFileUri()))));
            } else {
                imageView.setImageDrawable(null);
                System.out.println("every position is somehow greater than indices");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return imageView;
    }

}