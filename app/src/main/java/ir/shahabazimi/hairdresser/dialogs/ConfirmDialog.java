package ir.shahabazimi.hairdresser.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;

import ir.shahabazimi.hairdresser.R;
import ir.shahabazimi.hairdresser.classes.ConfirmInterface;
import ir.shahabazimi.hairdresser.classes.Utils;

public class ConfirmDialog extends Dialog {

    private Context context;
    private EditText name, code, amount, wallet, walletUse, total;

    private MaterialButton register, cancel;
    private ImageView close;

    private int myWallet;
    private int myAmount;
    private String myName;
    private String myCode;

    private ConfirmInterface confirmInterface;


    public ConfirmDialog(@NonNull Context context, String myCode, String myName, String myWallet, String myAmount, ConfirmInterface confirmInterface) {
        super(context);
        this.context = context;
        this.myWallet = Integer.parseInt(myWallet);
        this.myAmount = Integer.parseInt(myAmount);
        this.myName = myName;
        this.myCode = myCode;
        this.confirmInterface = confirmInterface;

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
        amount.setText(Utils.moneySeparator(String.valueOf(myAmount)));
        wallet = findViewById(R.id.reg_wallet);
        wallet.setText(Utils.moneySeparator(String.valueOf(myWallet)));
        walletUse = findViewById(R.id.reg_use);
        total = findViewById(R.id.reg_total);
        total.setText(Utils.moneySeparator(String.valueOf(myAmount)));

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
//                    if(s.length()>0){
//                        int m = Integer.parseInt(s.toString());
//                        if(m<=myWallet){
//                            total.setText(String.valueOf(myAmount-m));
//                        }
//
//                    }else{
//                        total.setText(String.valueOf(myAmount));
//                    }
            }

            @Override
            public void afterTextChanged(Editable s) {
                walletUse.removeTextChangedListener(this);

                String value = walletUse.getText().toString();


                if (!value.equals("")) {
                    if (value.startsWith("0") && !value.startsWith("0.")) {
                        walletUse.setText("");
                    }


                    String str = walletUse.getText().toString().replaceAll(",", "");
                    walletUse.setText(Utils.moneySeparator(str));
                    walletUse.setSelection(walletUse.getText().toString().length());

                    int m = Integer.parseInt(value.replace(",",""));
                    if (m <= myWallet) {
                        total.setText(Utils.moneySeparator(String.valueOf(myAmount - m)));
                    }else{
                        total.setText(Utils.moneySeparator(String.valueOf(myAmount)));

                    }


                }
                walletUse.addTextChangedListener(this);

            }
        });

        close.setOnClickListener(x -> dismiss());
        cancel.setOnClickListener(x -> dismiss());


        register.setOnClickListener(x -> {

            if (!walletUse.getText().toString().replace(",","").isEmpty() && myWallet < Integer.parseInt(walletUse.getText().toString().replace(",",""))) {
                Toast.makeText(context, "مبلغ استفاده از کیف پول بیشتر از حد مجاز است", Toast.LENGTH_SHORT).show();
            } else {
                confirmInterface.onClick(String.valueOf(myAmount)
                        , walletUse.getText().toString().replace(",","").isEmpty() ? "0" : walletUse.getText().toString().replace(",","")
                        , total.getText().toString().replace(",",""));
                dismiss();
            }

        });
    }

}
