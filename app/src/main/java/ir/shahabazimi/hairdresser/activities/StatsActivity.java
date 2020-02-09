package ir.shahabazimi.hairdresser.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

import ir.shahabazimi.hairdresser.R;
import ir.shahabazimi.hairdresser.adapters.MonthAdapter;
import ir.shahabazimi.hairdresser.adapters.YearAdapter;
import ir.shahabazimi.hairdresser.classes.Utils;
import ir.shahabazimi.hairdresser.data.RetrofitClient;
import ir.shahabazimi.hairdresser.models.StatResponse;
import ir.shahabazimi.hairdresser.models.StatResponse2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatsActivity extends AppCompatActivity {

    private RecyclerView yearRecycler,monthRecycler;
    private YearAdapter yearAdapter;
    private MonthAdapter monthAdapter;
    private EditText year;
    private ImageView check;
    private Spinner month;
    private int selectedMonth=1;
    private MaterialCardView monthCard,yearCard;
    private TextView monthTitle,yearTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        init();



    }


    private void init(){
        monthTitle = findViewById(R.id.stats_month_title);
        yearTitle = findViewById(R.id.stats_year_title);

        monthCard = findViewById(R.id.stats_month_card);
        yearCard = findViewById(R.id.stats_year_card);

        year = findViewById(R.id.stats_search_year);
        check = findViewById(R.id.stats_check);

        month = findViewById(R.id.stats_search_month);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.months, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month.setAdapter(adapter);

        yearRecycler = findViewById(R.id.stats_year_recycler);
        yearRecycler.setLayoutManager(new LinearLayoutManager(this));

        monthRecycler = findViewById(R.id.stats_month_recycler);
        monthRecycler.setLayoutManager(new LinearLayoutManager(this));
        onClicks();

    }

    private void onClicks(){

        findViewById(R.id.stat_back).setOnClickListener(w->onBackPressed());
        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMonth=position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        check.setOnClickListener(w->{
            if(year.getText().toString().isEmpty() || year.getText().toString().length()<4 ||
            !year.getText().toString().startsWith("139") || year.getText().toString().startsWith("140")){
                Toast.makeText(this, "لطفا مقدار سال را وارد کنید", Toast.LENGTH_SHORT).show();
                return;
            }
            if(Utils.checkInternet(this)) {
                getYearStats(year.getText().toString());
                getMonthStats(year.getText().toString(),selectedMonth);

            }else{
                Toast.makeText(this, "لطفا اتصال به اینترنت را بررسی کنید", Toast.LENGTH_SHORT).show();
            }







        });


    }

    private void getMonthStats(String year,int selectedMonth) {
        String eYear = String.valueOf(Integer.valueOf(year)+621);
        RetrofitClient.getInstance().getApi()
                .getstats2(eYear,String.valueOf(selectedMonth))
                .enqueue(new Callback<StatResponse2>() {
                    @Override
                    public void onResponse(Call<StatResponse2> call, Response<StatResponse2> response) {
                        if(response.isSuccessful()){
                            if(response.body()!=null && response.body().getData().size()>0){
                                String month="";
                                switch (selectedMonth){
                                    case 1:
                                       month="فروردین";
                                        break;
                                    case 2:
                                       month="اردیبهشت";
                                        break;
                                    case 3:
                                       month="خرداد";
                                        break;
                                    case 4:
                                       month="تیر";
                                        break;
                                    case 5:
                                       month="مرداد";
                                        break;
                                    case 6:
                                       month="شهریور";
                                        break;
                                    case 7:
                                       month="مهر";
                                        break;
                                    case 8:
                                       month="آبان";
                                        break;
                                    case 9:
                                       month="آذر";
                                        break;
                                    case 10:
                                       month="دی";
                                        break;
                                    case 11:
                                       month="بهمن";
                                        break;
                                    case 12:
                                       month="اسفند";
                                        break;
                                    case 13:
                                       month="امسال";
                                        break;

                                    case 14:
                                       month="کل";
                                        break;
                                }
                                monthTitle.setText("آمار ماه "+month);
                                monthCard.setVisibility(View.VISIBLE);
                                monthAdapter = new MonthAdapter(StatsActivity.this,response.body().getData(),eYear,selectedMonth+"");
                                monthRecycler.setAdapter(monthAdapter);
                            }
                            else{
                                monthCard.setVisibility(View.GONE);
                            }

                        }else {
                            yearCard.setVisibility(View.GONE);
                            monthCard.setVisibility(View.GONE);
                            Toast.makeText(StatsActivity.this, "خط! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StatResponse2> call, Throwable t) {
                        yearCard.setVisibility(View.GONE);
                        monthCard.setVisibility(View.GONE);
                        Toast.makeText(StatsActivity.this, "خط! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();

                    }
                });
    }
    private void getYearStats(String year){
        String eYear = String.valueOf(Integer.valueOf(year)+621);
        RetrofitClient.getInstance().getApi()
                .getstats(eYear)
                .enqueue(new Callback<StatResponse>() {
                    @Override
                    public void onResponse(Call<StatResponse> call, Response<StatResponse> response) {
                        if(response.isSuccessful()){
                            yearTitle.setText("آمار سال "+year);
                            yearCard.setVisibility(View.VISIBLE);
                            yearAdapter = new YearAdapter(response.body().getData());
                            yearRecycler.setAdapter(yearAdapter);
                        }else {
                            yearCard.setVisibility(View.GONE);
                            monthCard.setVisibility(View.GONE);
                            Toast.makeText(StatsActivity.this, "خط! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StatResponse> call, Throwable t) {
                        yearCard.setVisibility(View.GONE);
                        monthCard.setVisibility(View.GONE);
                        Toast.makeText(StatsActivity.this, "خط! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
