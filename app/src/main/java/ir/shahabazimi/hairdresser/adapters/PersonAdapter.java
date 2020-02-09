package ir.shahabazimi.hairdresser.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.shahabazimi.hairdresser.R;
import ir.shahabazimi.hairdresser.classes.DateConverter;
import ir.shahabazimi.hairdresser.classes.Utils;
import ir.shahabazimi.hairdresser.models.PersonModel;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    private List<PersonModel> data;

    public PersonAdapter(List<PersonModel> data){
        this.data=data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        PersonModel model = data.get(position);

        h.id.setText("ردیف "+String.valueOf(position+1));
        h.title.setText("عنوان خدمت: "+model.getTitle());
        h.price.setText("مبلغ خدمت: "+Utils.moneySeparator(model.getPrice()));
        String date = model.getDate();
        DateConverter dateConverter = new DateConverter();

        dateConverter.gregorianToPersian(Integer.parseInt(date.substring(0,4)),Integer.parseInt(date.substring(5,7)),Integer.parseInt(date.substring(8,10)));
        h.date.setText(dateConverter.getYear()+"/"+dateConverter.getMonth()+"/"+dateConverter.getDay()+" "+date.substring(11,16));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView id,title,price,date;


        ViewHolder(@NonNull View v) {
            super(v);
            id = v.findViewById(R.id.person_id);
            title = v.findViewById(R.id.person_title);
            price = v.findViewById(R.id.person_price);
            date = v.findViewById(R.id.person_date);
        }
    }
}
