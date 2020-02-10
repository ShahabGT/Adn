package ir.shahabazimi.hairdresser.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import ir.shahabazimi.hairdresser.R;
import ir.shahabazimi.hairdresser.classes.Utils;
import ir.shahabazimi.hairdresser.data.RetrofitClient;
import ir.shahabazimi.hairdresser.models.GeneralResponse;
import ir.shahabazimi.hairdresser.models.PointsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {

    private EditText points,wallet;
    private ImageView pointsSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
    }

    private void init(){
        points = findViewById(R.id.setting_points);
        wallet = findViewById(R.id.setting_wallet);
        pointsSave = findViewById(R.id.points_check);

        getPoints();
        onClicks();
    }

    private void onClicks(){
        findViewById(R.id.buy_back).setOnClickListener(w->onBackPressed());

        pointsSave.setOnClickListener(z->{
            Utils.hideKeyboard(SettingsActivity.this);
            String p = points.getText().toString();
            String w = wallet.getText().toString();
            if(p.isEmpty()||w.isEmpty()){
                Toast.makeText(this, "لطفا مقادیر امتیاز و کیف پول را بررسی کنید", Toast.LENGTH_SHORT).show();
            }else{
                if(Utils.checkInternet(SettingsActivity.this))
                    sendPoints(p,w);
                else
                    Toast.makeText(SettingsActivity.this, "لطفا دسترسی به اینترنت را بررسی کنید!", Toast.LENGTH_SHORT).show();

            }


        });


    }

    private void getPoints(){
        RetrofitClient.getInstance().getApi()
                .getpoints()
                .enqueue(new Callback<PointsResponse>() {
                    @Override
                    public void onResponse(Call<PointsResponse> call, Response<PointsResponse> response) {
                        if(response.isSuccessful() && response.body()!=null && response.body().getData().size()>0){
                            points.setText(response.body().getData().get(0).getAmount());
                            wallet.setText(response.body().getData().get(1).getAmount());

                        }else{
                            Toast.makeText(SettingsActivity.this, "خطا! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }

                    @Override
                    public void onFailure(Call<PointsResponse> call, Throwable t) {
                        Toast.makeText(SettingsActivity.this, "خطا! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                });
    }

    private void sendPoints(String point,String wallet){
        RetrofitClient.getInstance().getApi()
                .setpoints(point,wallet)
                .enqueue(new Callback<GeneralResponse>() {
                    @Override
                    public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(SettingsActivity.this, "با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();

                        }else
                            Toast.makeText(SettingsActivity.this, "خطا! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<GeneralResponse> call, Throwable t) {
                        Toast.makeText(SettingsActivity.this, "خطا! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();

                    }
                });

    }

}
