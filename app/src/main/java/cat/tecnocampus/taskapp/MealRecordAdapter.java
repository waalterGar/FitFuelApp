package cat.tecnocampus.taskapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MealRecordAdapter extends RecyclerView.Adapter<MealRecordAdapter.ViewHolder> {
    ArrayList<MealRecord> data;
    private ItemClickListener clickListener;

    public MealRecordAdapter(ArrayList<MealRecord> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_record_row, parent, false);
        ViewHolder vh = new ViewHolder(V);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealRecord current = data.get(position);
        holder.id_meal.setText(String.valueOf(current.getId_meal_record()));
        holder.meal_name.setText(String.valueOf(current.getMeal_name()));
        holder.quantity.setText(String.valueOf(current.getQuantity()));
        holder.date.setText(String.valueOf(current.getDate()));
        holder.eaten.setText(String.valueOf(current.getEaten()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView id_meal;
        public TextView meal_name;
        public TextView quantity;
        public TextView date;
        public TextView eaten;
        public Button btn_eaten;
        public Button btn_mealDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id_meal = itemView.findViewById(R.id.tv_id_mealRecord_value);
            meal_name = itemView.findViewById(R.id.tv_mealRecordName_value);
            quantity = itemView.findViewById(R.id.tv_quantity_value);
            date = itemView.findViewById(R.id.tv_mealRecordDate_value);
            eaten = itemView.findViewById(R.id.tv_eaten_value);
            btn_eaten = itemView.findViewById(R.id.btn_eaten);
            btn_mealDetails = itemView.findViewById(R.id.btn_mealDetails);

            btn_eaten.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClick(view, getAdapterPosition(), 4);
                }
            });
            btn_mealDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClick(view, getAdapterPosition(), 5);
                }
            });

        }
    }

}
