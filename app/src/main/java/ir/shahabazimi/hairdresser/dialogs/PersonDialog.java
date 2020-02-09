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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.Calendar;

import ir.shahabazimi.hairdresser.R;
import ir.shahabazimi.hairdresser.adapters.PersonAdapter;
import ir.shahabazimi.hairdresser.classes.Utils;
import ir.shahabazimi.hairdresser.data.RetrofitClient;
import ir.shahabazimi.hairdresser.models.GeneralResponse;
import ir.shahabazimi.hairdresser.models.PersonResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PersonDialog extends Dialog{

    private Context context;
    private ImageView close;
    private PersonAdapter adapter;
    private RecyclerView recyclerView;

    private String person;
    private String year;
    private String month;


    public PersonDialog(@NonNull Context context,String person,String year,String month) {
        super(context);
        this.context = context;
        this.person=person;
        this.month=month;
        this.year=year;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_person);
        init();
    }

    private void init() {
        close = findViewById(R.id.reg_close);
        recyclerView = findViewById(R.id.person_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        close.setOnClickListener(x -> dismiss());
        getData();


    }

    private void getData(){
        RetrofitClient.getInstance().getApi()
                .getpersonstats(year,month,person)
                .enqueue(new Callback<PersonResponse>() {
                    @Override
                    public void onResponse(Call<PersonResponse> call, Response<PersonResponse> response) {
                        if(response.isSuccessful() && response.body()!=null && response.body().getData().size()>0){
                            adapter= new PersonAdapter(response.body().getData());
                            recyclerView.setAdapter(adapter);
                        }else{
                            Toast.makeText(context, "خطا لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<PersonResponse> call, Throwable t) {
                        Toast.makeText(context, "خطا لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                });



    }





}
