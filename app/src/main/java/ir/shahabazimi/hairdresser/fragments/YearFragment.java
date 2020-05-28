package ir.shahabazimi.hairdresser.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ir.shahabazimi.hairdresser.R;
import ir.shahabazimi.hairdresser.adapters.YearAdapter;
import ir.shahabazimi.hairdresser.models.DataModel;


public class YearFragment extends Fragment {

    private YearAdapter yearAdapter;
    private RecyclerView yearRecycler;
    private TextView yearTitle;
    private Context context;
    private Activity activity;

    private String yearName;

    public void setYearName(String yearName) {
        this.yearName = yearName;
    }

    private List<DataModel> data;

    public void setData(List<DataModel> data) {
        this.data = data;
    }

    public YearFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_year, container, false);
        context = getContext();
        activity = getActivity();
        yearTitle = v.findViewById(R.id.stats_year_title);
        yearTitle.setText("آمار سال "+yearName);
        yearRecycler = v.findViewById(R.id.stats_year_recycler);
        yearRecycler.setLayoutManager(new LinearLayoutManager(context));
        yearAdapter = new YearAdapter(data);
        yearRecycler.setAdapter(yearAdapter);
        return v;
    }
}
