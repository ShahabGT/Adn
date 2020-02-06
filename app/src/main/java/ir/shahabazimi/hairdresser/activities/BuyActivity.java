package ir.shahabazimi.hairdresser.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import ir.shahabazimi.hairdresser.R;
import ir.shahabazimi.hairdresser.classes.Utils;
import ir.shahabazimi.hairdresser.data.RetrofitClient;
import ir.shahabazimi.hairdresser.dialogs.BrideDialog;
import ir.shahabazimi.hairdresser.dialogs.ConfirmDialog;
import ir.shahabazimi.hairdresser.models.FieldModel;
import ir.shahabazimi.hairdresser.models.GeneralResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyActivity extends AppCompatActivity {

    private EditText search;
    private TextView customerDetails;
    private MaterialCardView reg;

    private String cName="";
    private String cNumber="";
    private String cId="";
    private String cWallet="";
    private String cCode="";

    private LinearLayout layout;

    private ConstraintLayout loading;

    private List<FieldModel> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        init();
    }


    private void init(){

        data = new ArrayList<>();
        search= findViewById(R.id.buy_code_code);
        customerDetails= findViewById(R.id.buy_name);
        layout = findViewById(R.id.buy_info_linear);
        reg = findViewById(R.id.buy_reg);
        loading = findViewById(R.id.buy_progress_card);
        loading.setVisibility(View.GONE);


        onClicks();

    }
    private void onClicks(){

        reg.setOnClickListener(w->{
            data.clear();
            long amount=0;
            for(int i=0;i<layout.getChildCount();i++){
                FieldModel model = getData(layout.getChildAt(i));
                if( model!=null) {
                    data.add(model);
                    amount+=Long.parseLong(model.getPrice());
                }
            }

            if(data.size()==0){

                Toast.makeText(this, "لطفا لیست خدمات را کامل کنید", Toast.LENGTH_SHORT).show();
            }else if(cId.isEmpty()){
                Toast.makeText(this, "لطفا شماره مشتری را وارد کنید", Toast.LENGTH_SHORT).show();

            }else{

                ConfirmDialog dialog = new ConfirmDialog(BuyActivity.this,cCode,cName,cWallet,String.valueOf(amount), this::buy);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);


            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==4){
                    searchCode(s.toString());
                }else{
                    cName="";
                    cNumber="";
                    cId="";
                    cWallet="";
                    cCode="";
                    customerDetails.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==4){
                    searchCode(s.toString());

                }else{
                    cName="";
                    cNumber="";
                    cId="";
                    cWallet="";
                    cCode="";
                    customerDetails.setVisibility(View.GONE);

                }
            }
        });

        findViewById(R.id.buy_back).setOnClickListener(w->onBackPressed());

    }

    private FieldModel getData(View v){
       String title =((EditText) v.findViewById(R.id.field_title)).getText().toString() ;
        String price = ((EditText) v.findViewById(R.id.field_price)).getText().toString() ;
        String person = ((EditText) v.findViewById(R.id.field_person)).getText().toString() ;

       if(!title.isEmpty() && !price.isEmpty() && !person.isEmpty()){
           return new FieldModel(title,price,person);
       }
       return null;
    }

    public void onAddField(View v) {
        Utils.hideKeyboard(BuyActivity.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field, null);
        // Add the new row before the add field button.
        if(layout.getChildCount()==0)
            layout.addView(rowView, 0);
        else
        layout.addView(rowView, layout.getChildCount() );
    }

    public void onDelete(View v) {
        layout.removeView((View) v.getParent());
    }

    private void searchCode(String code){
        customerDetails.setVisibility(View.GONE);
        customerDetails.setText("");
        cName="";
        cNumber="";
        cId="";
        cWallet="";
        cCode="";


        RetrofitClient.getInstance().getApi().search(code)
                .enqueue(new Callback<GeneralResponse>() {
                    @Override
                    public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                        if(response.isSuccessful() && response.body()!=null){
                            customerDetails.setVisibility(View.VISIBLE);
                            cName = response.body().getName();
                            cId = response.body().getId();
                            cNumber = response.body().getNumber();
                            cWallet = response.body().getWallet();
                            cCode=code;
                            customerDetails.setText(cName+" "+cNumber);
                        }else if(response.code()==204){
                            customerDetails.setVisibility(View.VISIBLE);
                            customerDetails.setText("کاربر وجود ندارد");
                        }
                    }

                    @Override
                    public void onFailure(Call<GeneralResponse> call, Throwable t) {

                    }
                });
    }

    private void buy(String amount,String wallet,String pay){
        loading.setVisibility(View.VISIBLE);

        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> price = new ArrayList<>();
        ArrayList<String> person = new ArrayList<>();

        for(FieldModel m : data){
            titles.add(m.getTitle());
            price.add(m.getPrice());
            person.add(m.getPerson());
        }

        RetrofitClient.getInstance().getApi()
                .buy(cId,amount,wallet,pay,titles,price,person)
                .enqueue(new Callback<GeneralResponse>() {
                    @Override
                    public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(BuyActivity.this, "با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }else
                            Toast.makeText(BuyActivity.this, "خطا لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<GeneralResponse> call, Throwable t) {
                        loading.setVisibility(View.GONE);
                        Toast.makeText(BuyActivity.this, "خطا لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                    }
                });


    }

}
