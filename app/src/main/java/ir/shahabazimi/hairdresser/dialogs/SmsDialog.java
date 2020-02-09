package ir.shahabazimi.hairdresser.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class SmsDialog extends Dialog{

    private Context context;

    private ImageView close;

    private MaterialButton send;
    private EditText text;


    public SmsDialog(@NonNull Context context) {
        super(context);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_sms);
        init();
    }

    private void init() {

        text = findViewById(R.id.sms_text);
        send = findViewById(R.id.sms_send);
        close = findViewById(R.id.reg_close);



        onClicks();
    }

    private void onClicks() {

        close.setOnClickListener(x -> dismiss());

        send.setOnClickListener(w->{
            String t= text.getText().toString().trim();

            if(t.isEmpty()){
                Toast.makeText(context, "لطفا متن پیام را وارد کنید", Toast.LENGTH_SHORT).show();
            }else{
                if(Utils.checkInternet(context))
                    sendSms(t);
                else
                    Toast.makeText(context, "لطفا دسترسی به اینترنت را بررسی کنید!", Toast.LENGTH_SHORT).show();

            }


        });

    }

    private void sendSms(String text){
        RetrofitClient.getInstance().getApi()
                .sendsms(text)
                .enqueue(new Callback<GeneralResponse>() {
                    @Override
                    public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(context, "با موفقیت ارسال شد", Toast.LENGTH_SHORT).show();
                            dismiss();

                        }else{
                            Toast.makeText(context, "خطا! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<GeneralResponse> call, Throwable t) {
                        Toast.makeText(context, "خطا! لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
