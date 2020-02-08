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
import ir.shahabazimi.hairdresser.models.DataModel2;

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.ViewHolder> {

    private List<DataModel2> data;

    public MonthAdapter(List<DataModel2> data){
        this.data=data;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_month,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
            DataModel2 model = data.get(position);

            h.name.setText("نام فروشنده: "+model.getName());
            h.count.setText("تعداد خدمات: "+model.getPcount());
            h.price.setText("مبلغ کل خدمات: "+Utils.moneySeparator(model.getPsum()));


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name,count,price;


        ViewHolder(@NonNull View v) {
            super(v);
            name = v.findViewById(R.id.month_title);
            count = v.findViewById(R.id.month_count);
            price = v.findViewById(R.id.month_price);
        }
    }

}
