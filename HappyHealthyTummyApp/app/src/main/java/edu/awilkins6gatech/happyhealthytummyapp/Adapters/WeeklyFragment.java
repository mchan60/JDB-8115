package edu.awilkins6gatech.happyhealthytummyapp.Adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.awilkins6gatech.happyhealthytummyapp.Controller.MainPageActivity;
import edu.awilkins6gatech.happyhealthytummyapp.Controller.ViewEntryActivity;
import edu.awilkins6gatech.happyhealthytummyapp.Data.EntryDB;
import edu.awilkins6gatech.happyhealthytummyapp.Model.DiaryEntry;
import edu.awilkins6gatech.happyhealthytummyapp.R;

public class WeeklyFragment extends Fragment implements AdapterView.OnItemClickListener {

    int position;
    List<DiaryEntry> diaryEntries;
    EntryDB entryDB;
    List<DiaryEntry> entriesList;
    ListView listView;
    ArrayList<String> dates;

    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        WeeklyFragment tabFragment = new WeeklyFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        position = getArguments().getInt("pos");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.fragment_daily, container, false);

        entryDB = new EntryDB(getActivity());
        entriesList = entryDB.getEntries();

        int i = 10;
        int j = 21;
        int k = 0;
        int c = 0;
        dates = new ArrayList(Arrays.asList("Sunday " + String.valueOf(i) + "/ " + String.valueOf(j)));
        j++;
        k++;
        while (c < 28){
            if (k == 0) {
                    dates.add("Sunday " + String.valueOf(i) + "/" + String.valueOf(j) );
                } else if (k == 1) {
                    dates.add("Monday " + String.valueOf(i) + "/" + String.valueOf(j) );
                } else if (k == 2) {
                    dates.add("Tuesday " + String.valueOf(i) + "/" + String.valueOf(j) );
                } else if (k == 3) {
                    dates.add("Wednesday " + String.valueOf(i) + "/" + String.valueOf(j) );
                } else if (k == 4) {
                    dates.add("Thursday " + String.valueOf(i) + "/" + String.valueOf(j) );
                } else if (k == 5) {
                    dates.add("Friday " + String.valueOf(i) + "/" + String.valueOf(j) );
                } else if (k == 6) {
                    dates.add("Saturday " + String.valueOf(i) + "/" + String.valueOf(j) );
                } else {
                    dates.add("Sunday " + String.valueOf(i) + "/" + String.valueOf(j) );
                }
                c++;
                k++;
                k = k%7;
                j++;
                if (j == 32) {
                    j = 1;
                    i++;
                }
                if (i == 13) {
                    i = 1;
                }
        }

        listView = (ListView) view.findViewById(R.id.diaryEntries);
        if (listView == null) System.out.println("list view is null in main FIX IT !!!!!!!");

        //Adapter to populate the page with image list view
        if (entriesList != null) {
            ArrayAdapter adapter = new ArrayAdapter(getActivity(),
                    R.layout.list_item2, R.id.title2, dates);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                    Intent goToDailyView = new Intent(getActivity(), MainPageActivity.class);
                                                    startActivity(goToDailyView);
                                                }

                                            }
            );

        }
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_daily);

        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        ((MainPageActivity) getActivity()).refreshNow();
                        Toast.makeText(getContext(), "Refresh Layout working", Toast.LENGTH_LONG).show();
                    }
                }
        );

        return view;
    }







    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (position ==1 ) {

        } else if (position==2) {

        } else if (position ==3) {

        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        android.widget.Toast toast = android.widget.Toast.makeText(getActivity().getApplicationContext(),
                "Item " + (position + 1) + ": " + diaryEntries.get(position),
                android.widget.Toast.LENGTH_SHORT);
        toast.setGravity(android.view.Gravity.BOTTOM|android.view.Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
}