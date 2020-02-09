package ir.shahabazimi.hairdresser.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.button.MaterialButton;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.Calendar;

import ir.shahabazimi.hairdresser.R;
import ir.shahabazimi.hairdresser.classes.Utils;
import ir.shahabazimi.hairdresser.data.RetrofitClient;
import ir.shahabazimi.hairdresser.models.GeneralResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrideDialog extends Dialog implements DatePickerDialog.OnDateSetListener {

    private Context context;
    private EditText name, code,sPhone,price;
    private TextView cDate, wDate;
    private boolean isWeddingDate =false;
    private MaterialButton register,cancel;
    private ImageView close;

    private String cName="";
    private String cNumber="";
    private String cId="";
    private String cWallet="";


    public BrideDialog(@NonNull Context context) {
        super(context);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_bride);
        init();
    }

    private void init() {
        name = findViewById(R.id.reg_name);
        code = findViewById(R.id.reg_code);
        cDate = findViewById(R.id.reg_contract);
        wDate = findViewById(R.id.reg_wd);
        price = findViewById(R.id.reg_price);
        sPhone = findViewById(R.id.reg_sn);

        close = findViewById(R.id.reg_close);
        cancel = findViewById(R.id.reg_cancel);
        register = findViewById(R.id.reg_reg);


        onClicks();
    }

    private void onClicks() {

        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==4){
                    searchCode(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==4){
                    searchCode(s.toString());
                }
            }
        });


        close.setOnClickListener(x -> dismiss());
        cancel.setOnClickListener(x -> dismiss());

        wDate.setOnClickListener(w->{
            isWeddingDate =true;
            PersianCalendar persianCalendar = new PersianCalendar();
            DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                    this::onDateSet,
                    persianCalendar.getPersianYear(),
                    persianCalendar.getPersianMonth(),
                    persianCalendar.getPersianDay()
            );
            PersianCalendar maxDate = new PersianCalendar();
            maxDate.setPersianDate(1398,12,29);

            PersianCalendar minDate = new PersianCalendar();
            minDate.setPersianDate(1300,1,1);
            datePickerDialog.setMaxDate(maxDate);
            datePickerDialog.setMinDate(minDate);
            datePickerDialog.setFirstDayOfWeek(Calendar.SATURDAY);

            datePickerDialog.show(((FragmentActivity)context).getFragmentManager(), "Datepickerdialog");
        });
        cDate.setOnClickListener(w->{
            isWeddingDate =false;
            PersianCalendar persianCalendar = new PersianCalendar();
            DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                    this::onDateSet,
                    persianCalendar.getPersianYear(),
                    persianCalendar.getPersianMonth(),
                    persianCalendar.getPersianDay()
            );
            PersianCalendar maxDate = new PersianCalendar();
            maxDate.setPersianDate(1410,12,29);

            PersianCalendar minDate = new PersianCalendar();
            minDate.setPersianDate(1360,1,1);
            datePickerDialog.setMaxDate(maxDate);
            datePickerDialog.setMinDate(minDate);
            datePickerDialog.setFirstDayOfWeek(Calendar.SATURDAY);

            datePickerDialog.show(((FragmentActivity)context).getFragmentManager(), "Datepickerdialog");
        });

        register.setOnClickListener(x -> {
            Utils.hideKeyboard(getOwnerActivity());
            String c = cDate.getText().toString();
            String w = wDate.getText().toString();
            String s = sPhone.getText().toString().replace(" ","");
            String p = price.getText().toString();

            if(cName.isEmpty()) {
                Toast.makeText(context, "لطفا کد مشتری را وارد کنید", Toast.LENGTH_SHORT).show();

            }else if (w.isEmpty() ||c.isEmpty() || p.isEmpty()) {
                Toast.makeText(context, "لطفا فرم را بررسی کنید", Toast.LENGTH_SHORT).show();
            } else {
                if (Utils.checkInternet(context))
                    reg(c,w,s,p);
                else
                    Toast.makeText(context, "لطفا دسترسی به اینترنت را چک کنید!", Toast.LENGTH_SHORT).show();

            }

        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year+"/"+(monthOfYear+1)+"/"+dayOfMonth;

        if(isWeddingDate)
            wDate.setText(date);
        else
            cDate.setText(date);
    }

    private void searchCode(String code){
        cName="";
        cNumber="";
        cId="";
        cWallet="";

        RetrofitClient.getInstance().getApi().search(code)
                .enqueue(new Callback<GeneralResponse>() {
                    @Override
                    public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                        if(response.isSuccessful() && response.body()!=null){
                            cName = response.body().getName();
                            cId = response.body().getId();
                            cNumber = response.body().getNumber();
                            cWallet = response.body().getWallet();
                            name.setText(cName);

                        }else if(response.code()==204){
                            Toast.makeText(context, "کاربر وجود ندارد", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GeneralResponse> call, Throwable t) {

                    }
                });
    }

    private void reg(String contractDate, String weddingDate, String sPhone, String price) {
        close.setEnabled(false);
        cancel.setEnabled(false);
        register.setEnabled(false);
        register.setText("...");
        setCanceledOnTouchOutside(false);
        setCancelable(false);

        if(weddingDate.equals("انتخاب کنید")){
            weddingDate="";
        }
        if(contractDate.equals("انتخاب کنید")){
            contractDate="";
        }


        RetrofitClient.getInstance().getApi()
                .bride(cId,weddingDate,contractDate,sPhone,price)
                .enqueue(new Callback<GeneralResponse>() {
                    @Override
                    public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                        setCanceledOnTouchOutside(true);
                        setCancelable(true);
                        close.setEnabled(true);
                        cancel.setEnabled(true);
                        register.setEnabled(true);
                        register.setText("ثبت نام");
                        if(response.isSuccessful()){
                            Toast.makeText(context, "با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                            dismiss();

                        }else if(response.code()==409){
                            Toast.makeText(context, "این شماره قبلا ثبت شده است", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(context, "خطا لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<GeneralResponse> call, Throwable t) {
                        setCanceledOnTouchOutside(true);
                        setCancelable(true);
                        close.setEnabled(true);
                        cancel.setEnabled(true);
                        register.setEnabled(true);
                        register.setText("ثبت نام");
                        Toast.makeText(context, "خطا لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
