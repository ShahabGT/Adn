package ir.shahabazimi.hairdresser.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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

public class RegisterDialog extends Dialog implements DatePickerDialog.OnDateSetListener {

    private Context context;
    private EditText name, code, phone;
    private TextView bDate, wDate;
    private boolean isBirthday =false;
    private MaterialButton register,cancel;
    private ImageView close;


    public RegisterDialog(@NonNull Context context) {
        super(context);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_register);
        init();
    }

    private void init() {
        name = findViewById(R.id.reg_name);
        code = findViewById(R.id.reg_code);
        bDate = findViewById(R.id.reg_bd);
        wDate = findViewById(R.id.reg_wd);
        phone = findViewById(R.id.reg_number);

        close = findViewById(R.id.reg_close);
        cancel = findViewById(R.id.reg_cancel);
        register = findViewById(R.id.reg_reg);


        onClicks();
    }

    private void onClicks() {
        close.setOnClickListener(x -> dismiss());
        cancel.setOnClickListener(x -> dismiss());

        bDate.setOnClickListener(w->{
            isBirthday =true;
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
        wDate.setOnClickListener(w->{
            isBirthday =false;
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
            String n = name.getText().toString();
            String c = code.getText().toString();
            String b = bDate.getText().toString();
            String w = wDate.getText().toString();
            String p = phone.getText().toString();

            if (n.isEmpty() || p.isEmpty() || p.length() < 11) {
                Toast.makeText(context, "لطفا نام و نام خانوادگی/ شماره تلفن را بررسی کنید", Toast.LENGTH_SHORT).show();
            } else {
                if (Utils.checkInternet(context))
                    reg(n, c, b, w, p);
                else
                    Toast.makeText(context, "لطفا دسترسی به اینترنت را چک کنید!", Toast.LENGTH_SHORT).show();

            }

        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year+"/"+(monthOfYear+1)+"/"+dayOfMonth;

        if(isBirthday)
            bDate.setText(date);
        else
            wDate.setText(date);
    }

    private void reg(String name, String code, String birthday, String weddingDate, String phone) {
        close.setEnabled(false);
        cancel.setEnabled(false);
        register.setEnabled(false);
        register.setText("...");
        setCanceledOnTouchOutside(false);
        setCancelable(false);

        if(birthday.equals("انتخاب کنید")){
            birthday="";
        }
        if(weddingDate.equals("انتخاب کنید")){
            weddingDate="";
        }

        RetrofitClient.getInstance().getApi()
                .register(name,phone,birthday,weddingDate,code)
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
