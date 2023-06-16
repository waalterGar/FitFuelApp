package cat.tecnocampus.taskapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {
    ArrayList<Session> data;
    private ItemClickListener clickListener;

    public SessionAdapter(ArrayList<Session> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.session_row, parent, false);

        ViewHolder vh = new ViewHolder(V);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Session current = data.get(position);
        holder.id_training_session.setText(String.valueOf(current.getId_training_session()));
        holder.name.setText(String.valueOf(current.getName()));
        holder.session_date.setText(String.valueOf(current.getSession_date()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView id_training_session;
        public TextView name;
        public TextView session_date;
        public Button btn_sessionDetails;
        public Button btn_SessionExercises;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id_training_session = itemView.findViewById(R.id.id_training_session);
            name = itemView.findViewById(R.id.name);
            session_date = itemView.findViewById(R.id.session_date);

            btn_sessionDetails = itemView.findViewById(R.id.btn_sessionDetails);
            btn_SessionExercises = itemView.findViewById(R.id.btn_eaten);

            btn_sessionDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClick(view, getAdapterPosition(), 1);
                }
            });

            btn_SessionExercises.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClick(view, getAdapterPosition(), 2);
                }
            });
        }
    }

}
