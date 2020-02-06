package ir.shahabazimi.hairdresser.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import ir.shahabazimi.hairdresser.classes.ConfirmInterface;
import ir.shahabazimi.hairdresser.classes.Utils;
import ir.shahabazimi.hairdresser.data.RetrofitClient;
import ir.shahabazimi.hairdresser.models.GeneralResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmDialog extends Dialog{

    private Context context;
    private EditText name, code,amount,wallet,walletUse,total;

    private MaterialButton register,cancel;
    private ImageView close;

    private int myWallet;
    private int myAmount;
    private String myName;
    private String myCode;

    private ConfirmInterface confirmInterface;



    public ConfirmDialog(@NonNull Context context, String myCode, String myName, String myWallet, String myAmount, ConfirmInterface confirmInterface) {
        super(context);
        this.context = context;
        this.myWallet=Integer.parseInt(myWallet);
        this.myAmount=Integer.parseInt(myAmount);
        this.myName=myName;
        this.myCode=myCode;
        this.confirmInterface=confirmInterface;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_confirm);
        init();
    }

    private void init() {
        name = findViewById(R.id.reg_name);
        name.setText(myName);
        code = findViewById(R.id.reg_code);
        code.setText(myCode);
        amount = findViewById(R.id.reg_amount);
        amount.setText(String.valueOf(myAmount));
        wallet = findViewById(R.id.reg_wallet);
        wallet.setText(String.valueOf(myWallet));
        walletUse = findViewById(R.id.reg_use);
        total = findViewById(R.id.reg_total);
        total.setText(String.valueOf(myAmount));

        close = findViewById(R.id.reg_close);
        cancel = findViewById(R.id.reg_cancel);
        register = findViewById(R.id.reg_reg);


        onClicks();
    }

    private void onClicks() {

        walletUse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length()>0){
                        int m = Integer.parseInt(s.toString());
                        if(m<=myWallet){
                            total.setText(String.valueOf(myAmount-m));
                        }

                    }else{
                        total.setText(String.valueOf(myAmount));
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    int m = Integer.parseInt(s.toString());
                    if(m<=myWallet){
                        total.setText(String.valueOf(myAmount-m));
                    }

                }else{
                    total.setText(String.valueOf(myAmount));
                }
            }
        });

        close.setOnClickListener(x -> dismiss());
        cancel.setOnClickListener(x -> dismiss());



        register.setOnClickListener(x -> {

           if(!walletUse.getText().toString().isEmpty() &&myWallet<Integer.parseInt(walletUse.getText().toString())){
               Toast.makeText(context, "مبلغ استفاده از کیف پول بیشتر از حد مجاز است", Toast.LENGTH_SHORT).show();
           }else {
                confirmInterface.onClick(String.valueOf(myAmount)
                                        ,walletUse.getText().toString().isEmpty() ? "0" :walletUse.getText().toString()
                                        ,total.getText().toString());
                dismiss();
           }

        });
    }

}
