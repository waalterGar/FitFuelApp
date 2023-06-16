package cat.tecnocampus.taskapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExecutionAdapter extends RecyclerView.Adapter<ExecutionAdapter.ViewHolder> {
    ArrayList<Execution> data;
    private ItemClickListener clickListener;

    public ExecutionAdapter(ArrayList<Execution> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.execution_row, parent, false);

        ViewHolder vh = new ViewHolder(V);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Execution current = data.get(position);
        holder.exercise_name.setText(String.valueOf(current.getExercise_name()));
        holder.num_set.setText(String.valueOf(current.getNum_set()));
        holder.repetitions.setText(String.valueOf(current.getRepetitions()));
        holder.weight.setText(String.valueOf(current.getWeight()));
        holder.rpe.setText(String.valueOf(current.getRpe()));
        holder.rir.setText(String.valueOf(current.getRir()));
        holder.done.setText(String.valueOf(current.getDone()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView exercise_name;
        public TextView num_set;
        public TextView repetitions;
        public TextView weight;
        public TextView rpe;
        public TextView rir;
        public TextView done;
        public Button btnCompleteExercise;
        public Button btnExerciseProgress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exercise_name = itemView.findViewById(R.id.tv_exercise_name2);
            num_set = itemView.findViewById(R.id.tv_num_set2);
            repetitions = itemView.findViewById(R.id.tv_repetitions2);
            weight = itemView.findViewById(R.id.tv_weight2);
            rpe = itemView.findViewById(R.id.tv_rpe2);
            rir = itemView.findViewById(R.id.tv_rir2);
            done = itemView.findViewById(R.id.tv_done2);
            btnCompleteExercise = itemView.findViewById(R.id.btn_completeExercise);
            btnExerciseProgress = itemView.findViewById(R.id.btn_exerciseProgression);

            btnCompleteExercise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClick(view, getAdapterPosition(), 3);
                }
            });

            btnExerciseProgress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClick(view, getAdapterPosition(), 6);
                }
            });
        }
    }

}
