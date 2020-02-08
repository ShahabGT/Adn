package ir.shahabazimi.hairdresser.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.shahabazimi.hairdresser.R;
import ir.shahabazimi.hairdresser.classes.Utils;
import ir.shahabazimi.hairdresser.models.DataModel;

public class YearAdapter extends RecyclerView.Adapter<YearAdapter.ViewHolder> {

    private List<DataModel> data;

    public YearAdapter(List<DataModel> data){
        this.data=data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_year,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {

        DataModel model = data.get(position);

        h.count.setText("تعداد: "+model.getCount());
        h.price.setText("مبلغ: "+Utils.moneySeparator(model.getSum()));

        switch (model.getId()){
            case 1:
                h.title.setText("فروردین");
                break;
            case 2:
                h.title.setText("اردیبهشت");
                break;
            case 3:
                h.title.setText("خرداد");
                break;
            case 4:
                h.title.setText("تیر");
                break;
            case 5:
                h.title.setText("مرداد");
                break;
            case 6:
                h.title.setText("شهریور");
                break;
            case 7:
                h.title.setText("مهر");
                break;
            case 8:
                h.title.setText("آبان");
                break;
            case 9:
                h.title.setText("آذر");
                break;
            case 10:
                h.title.setText("دی");
                break;
            case 11:
                h.title.setText("بهمن");
                break;
            case 12:
                h.title.setText("اسفند");
                break;
            case 13:
                h.title.setText("امسال");
                break;

            case 14:
                h.title.setText("کل");
                break;
        }



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView count,price,title;


        ViewHolder(@NonNull View v) {
            super(v);
            count = v.findViewById(R.id.year_count);
            price = v.findViewById(R.id.year_price);
            title = v.findViewById(R.id.year_title);
        }
    }
}
