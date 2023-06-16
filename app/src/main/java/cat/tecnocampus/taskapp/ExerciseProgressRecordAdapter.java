package cat.tecnocampus.taskapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExerciseProgressRecordAdapter extends RecyclerView.Adapter<ExerciseProgressRecordAdapter.ViewHolder> {
    ArrayList<ExerciseProgressRecord> data;
    private ItemClickListener clickListener;

    public ExerciseProgressRecordAdapter(ArrayList<ExerciseProgressRecord> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_progress_row, parent, false);
        ViewHolder vh = new ViewHolder(V);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExerciseProgressRecord current = data.get(position);
        holder.date.setText(current.getDate());
        holder.exercise_name.setText(current.getExercise_name());
        holder.num_set.setText(current.getNum_set());
        holder.repetitions.setText(current.getRepetitions());
        holder.weight.setText(current.getWeight());
        holder.rpe.setText(current.getRpe());
        holder.rir.setText(current.getRir());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView exercise_name;
        TextView num_set;
        TextView repetitions;
        TextView weight;
        TextView rpe;
        TextView rir;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.tv_dateProgress_value);
            exercise_name = itemView.findViewById(R.id.tv_exercise_name_progress_value);
            num_set = itemView.findViewById(R.id.tv_num_sets_progress_value);
            repetitions = itemView.findViewById(R.id.tv_reps_progress_value);
            weight = itemView.findViewById(R.id.tv_weight_progress_value);
            rpe = itemView.findViewById(R.id.tv_rpe_progress_value);
            rir = itemView.findViewById(R.id.tv_rir_progress_value);

        }
    }

}
