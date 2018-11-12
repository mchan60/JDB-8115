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
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import edu.awilkins6gatech.happyhealthytummyapp.Controller.MainPageActivity;
import edu.awilkins6gatech.happyhealthytummyapp.Controller.ViewEntryActivity;
import edu.awilkins6gatech.happyhealthytummyapp.Data.EntryDB;
import edu.awilkins6gatech.happyhealthytummyapp.Model.DiaryEntry;
import edu.awilkins6gatech.happyhealthytummyapp.R;

public class DailyFragment extends Fragment implements AdapterView.OnItemClickListener {

    int position;
    List<DiaryEntry> diaryEntries;
    EntryDB entryDB;
    List<DiaryEntry> entriesList;
    ListView listView;

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
//        position = getArguments().getInt("pos");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.fragment_daily, container, false);

        entryDB = new EntryDB(getActivity());
        entriesList = entryDB.getEntries();

        listView = (ListView) view.findViewById(R.id.diaryEntries);
        if (listView == null) System.out.println("list view is null in main FIX IT !!!!!!!");

        //Adapter to populate the page with image list view
        if (entriesList != null) {
            CustomListViewAdapter adapter = new CustomListViewAdapter(getActivity(),
                    R.id.diaryEntries, entriesList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent goToViewEntryPage = new Intent(getActivity(), ViewEntryActivity.class);
                    int index = entriesList.size() - 1 - (int) l;
                    goToViewEntryPage.putExtra("DIARY_ENTRY", ((int) index));

                    goToViewEntryPage.putExtra("TIMESTAMP", entriesList.get(index).getTimestamp());
                    startActivity(goToViewEntryPage);
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
                        Toast.makeText(getContext(), "Done!", Toast.LENGTH_LONG).show();
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