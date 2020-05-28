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
import android.widget.Toast;

import java.util.List;

import ir.shahabazimi.hairdresser.R;
import ir.shahabazimi.hairdresser.activities.StatsActivity;
import ir.shahabazimi.hairdresser.adapters.MonthAdapter;
import ir.shahabazimi.hairdresser.data.RetrofitClient;
import ir.shahabazimi.hairdresser.models.DataModel2;
import ir.shahabazimi.hairdresser.models.StatResponse2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MonthFragment extends Fragment {

    public MonthFragment() {
    }

    private MonthAdapter monthAdapter;
    private TextView monthTitle;
    private RecyclerView monthRecycler;
    private Context context;
    private Activity activity;

    private String monthName;

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    private List<DataModel2> data;
    private String year;
    private String month;

    public void setData(List<DataModel2> data) {
        this.data = data;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_month, container, false);
        context = getContext();
        activity = getActivity();
        monthTitle = v.findViewById(R.id.stats_month_title);
        monthTitle.setText("آمار ماه "+monthName);
        monthRecycler = v.findViewById(R.id.stats_month_recycler);
        monthRecycler.setLayoutManager(new LinearLayoutManager(context));
        monthAdapter = new MonthAdapter(context,data,year,month);

        monthRecycler.setAdapter(monthAdapter);

        return v;
    }


}
