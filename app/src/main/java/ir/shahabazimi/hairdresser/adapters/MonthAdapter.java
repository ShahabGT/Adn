package ir.shahabazimi.hairdresser.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import ir.shahabazimi.hairdresser.R;
import ir.shahabazimi.hairdresser.classes.Utils;
import ir.shahabazimi.hairdresser.dialogs.PersonDialog;
import ir.shahabazimi.hairdresser.models.DataModel2;

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.ViewHolder> {

    private List<DataModel2> data;
    private Context context;
    private String year;
    private String month;

    public MonthAdapter(Context context, List<DataModel2> data, String year, String month) {
        this.data = data;
        this.context = context;
        this.year = year;
        this.month = month;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_month, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        DataModel2 model = data.get(position);

        if (position == 0)
            h.name.setText(model.getName());
        else
            h.name.setText("نام فروشنده: " + model.getName());

        h.count.setText("تعداد خدمات: " + model.getPcount());
        h.price.setText("مبلغ کل خدمات: " + Utils.moneySeparator(model.getPsum()));

        h.itemView.setOnClickListener(w -> {
            if (position != 0) {
                PersonDialog dialog = new PersonDialog(context, model.getName(), year, month);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            }

        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, count, price;


        ViewHolder(@NonNull View v) {
            super(v);
            name = v.findViewById(R.id.month_title);
            count = v.findViewById(R.id.month_count);
            price = v.findViewById(R.id.month_price);
        }
    }

}
