package ir.shahabazimi.hairdresser.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ir.shahabazimi.hairdresser.R;
import ir.shahabazimi.hairdresser.classes.Utils;
import ir.shahabazimi.hairdresser.data.RetrofitClient;
import ir.shahabazimi.hairdresser.models.GeneralResponse;
import ir.shahabazimi.hairdresser.models.PointsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {

    private EditText points,points2, userPoints, userNewPoints, code, wallet;
    private ImageView pointsSave, userPointsSave;
    private TextView userName, userPointsTitle, userNewPointsTitle;

    private String cName = "";
    private String cNumber = "";
    private String cId = "";
    private String cPoints = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
    }

    private void init() {
        points = findViewById(R.id.setting_points);
        points2 = findViewById(R.id.setting_points2);
        wallet = findViewById(R.id.setting_wallet);
        pointsSave = findViewById(R.id.points_check);

        code = findViewById(R.id.setting_usercode);
        userPoints = findViewById(R.id.setting_userpoints);
        userNewPoints = findViewById(R.id.setting_userpoints_new);
        userPointsSave = findViewById(R.id.userpoints_check);
        userName = findViewById(R.id.setting_usercode_name);
        userPointsTitle = findViewById(R.id.setting_userpoints_title);
        userNewPointsTitle = findViewById(R.id.setting_userpoints_title2);


        userName.setVisibility(View.GONE);
        userPointsTitle.setVisibility(View.GONE);
        userNewPointsTitle.setVisibility(View.GONE);
        userPointsSave.setVisibility(View.GONE);
        userPoints.setVisibility(View.GONE);
        userNewPoints.setVisibility(View.GONE);

        getPoints();
        onClicks();
    }

    private void onClicks() {

        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 4) {
                    searchCode(s.toString());
                } else {
                    cName = "";
                    cNumber = "";
                    cId = "";
                    cPoints = "";
                    userName.setVisibility(View.GONE);
                    userPointsTitle.setVisibility(View.GONE);
                    userNewPointsTitle.setVisibility(View.GONE);
                    userPointsSave.setVisibility(View.GONE);
                    userPoints.setVisibility(View.GONE);
                    userNewPoints.setVisibility(View.GONE);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 4) {
                    searchCode(s.toString());
                } else {
                    cName = "";
                    cNumber = "";
                    cId = "";
                    cPoints = "";
                    userName.setVisibility(View.GONE);
                    userPointsTitle.setVisibility(View.GONE);
                    userNewPointsTitle.setVisibility(View.GONE);
                    userPointsSave.setVisibility(View.GONE);
                    userPoints.setVisibility(View.GONE);
                    userNewPoints.setVisibility(View.GONE);

                }
            }
        });

        findViewById(R.id.buy_back).setOnClickListener(w -> onBackPressed());

        pointsSave.setOnClickListener(z -> {
            Utils.hideKeyboard(SettingsActivity.this);
            String p = points.getText().toString().trim().replace(" ","");
            String p2 = points2.getText().toString().trim().replace(" ","");
            String w = wallet.getText().toString().trim().replace(" ","");
            if (p.isEmpty() || w.isEmpty() || p2.isEmpty()) {
                Toast.makeText(this, "لطفا مقادیر امتیاز و کیف پول را بررسی کنید", Toast.LENGTH_SHORT).show();
            } else {
                if (Utils.checkInternet(SettingsActivity.this))
                    sendPoints(p, w,p2);
                else
                    Toast.makeText(SettingsActivity.this, "لطفا دسترسی به اینترنت را بررسی کنید!", Toast.LENGTH_SHORT).show();

            }


        });

        userPointsSave.setOnClickListener(z -> {
            Utils.hideKeyboard(SettingsActivity.this);
            String p = userNewPoints.getText().toString().trim().replace(" ","");

            if (p.isEmpty() || cId.isEmpty()) {
                Toast.makeText(this, "لطفا مقدار امتیاز را بررسی کنید", Toast.LENGTH_SHORT).show();
            } else {
                if (Utils.checkInternet(SettingsActivity.this))
                    sendUserPoints(cId, p);
                else
                    Toast.makeText(SettingsActivity.this, "لطفا دسترسی به اینترنت را بررسی کنید!", Toast.LENGTH_SHORT).show();

            }


        });


    }

    private void getPoints() {
        RetrofitClient.getInstance().getApi()
                .getpoints()
                .enqueue(new Callback<PointsResponse>() {
                    @Override
                    public void onResponse(Call<PointsResponse> call, Response<PointsResponse> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().getData().size() > 0) {
                            points.setText(response.body().getData().get(0).getAmount());
                            wallet.setText(response.body().getData().get(1).getAmount());
                            points2.setText(response.body().getData().get(2).getAmount());

                        } else {
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

    private void sendPoints(String point, String wallet,String point2) {
        RetrofitClient.getInstance().getApi()
                .setpoints(point, wallet,point2)
                .enqueue(new Callback<GeneralResponse>() {
                    @Override
                    public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(SettingsActivity.this, "با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();

                        } else
                            Toast.makeText(SettingsActivity.this, "خطا! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<GeneralResponse> call, Throwable t) {
                        Toast.makeText(SettingsActivity.this, "خطا! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void sendUserPoints(String userId, String points) {
        RetrofitClient.getInstance().getApi()
                .setuserpoints(userId, points)
                .enqueue(new Callback<GeneralResponse>() {
                    @Override
                    public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(SettingsActivity.this, "با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();

                        } else
                            Toast.makeText(SettingsActivity.this, "خطا! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<GeneralResponse> call, Throwable t) {
                        Toast.makeText(SettingsActivity.this, "خطا! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();

                    }
                });

    }


    private void searchCode(String code) {
        Utils.hideKeyboard(SettingsActivity.this);

        cName = "";
        cNumber = "";
        cId = "";
        cPoints = "";
        userName.setVisibility(View.GONE);
        userPointsTitle.setVisibility(View.GONE);
        userNewPointsTitle.setVisibility(View.GONE);
        userPointsSave.setVisibility(View.GONE);
        userPoints.setVisibility(View.GONE);
        userNewPoints.setVisibility(View.GONE);


        RetrofitClient.getInstance().getApi().getuserpoints(code)
                .enqueue(new Callback<GeneralResponse>() {
                    @Override
                    public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            userName.setVisibility(View.VISIBLE);
                            userPointsTitle.setVisibility(View.VISIBLE);
                            userNewPointsTitle.setVisibility(View.VISIBLE);
                            userPointsSave.setVisibility(View.VISIBLE);
                            userPoints.setVisibility(View.VISIBLE);
                            userNewPoints.setVisibility(View.VISIBLE);
                            cName = response.body().getName();
                            cId = response.body().getId();
                            cNumber = response.body().getNumber();
                            cPoints = response.body().getPoints();
                            userName.setText(cName+" "+cNumber);
                            userPoints.setText(cPoints);


                        } else if (response.code() == 204) {
                            userName.setVisibility(View.VISIBLE);
                            userName.setText("کاربر وجود ندارد");
                            userPointsTitle.setVisibility(View.GONE);
                            userNewPointsTitle.setVisibility(View.GONE);
                            userPointsSave.setVisibility(View.GONE);
                            userPoints.setVisibility(View.GONE);
                            userNewPoints.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<GeneralResponse> call, Throwable t) {

                    }
                });
    }
}
