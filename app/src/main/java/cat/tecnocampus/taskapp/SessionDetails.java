package cat.tecnocampus.taskapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SessionDetails extends AppCompatActivity {
    RequestQueue queue;
    TextView tv_id_training_session;
    TextView tv_name;
    TextView tv_description;
    TextView tv_session_date;
    TextView tv_trainer_notes;
    TextView tv_athlete_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Session Details");

        tv_id_training_session = findViewById(R.id.tv_id_training_session2);
        tv_name= findViewById(R.id.tv_name2);
        tv_description = findViewById(R.id.et_AthletePhone);
        tv_session_date = findViewById(R.id.tv_session_date2);
        tv_trainer_notes = findViewById(R.id.tv_trainer_notes2);
        tv_athlete_id  = findViewById(R.id.tv_athlete_id2);
        queue = Volley.newRequestQueue(getApplicationContext());
        Log.v("Intent id: ", getIntent().getStringExtra("id_training_session"));

        tv_id_training_session.setText(getIntent().getStringExtra("id_training_session"));
        tv_name.setText(getIntent().getStringExtra("name"));
        tv_description.setText(getIntent().getStringExtra("description"));
        tv_session_date.setText(getIntent().getStringExtra("session_date"));
        tv_trainer_notes.setText(getIntent().getStringExtra("trainer_notes"));
        tv_athlete_id.setText(getIntent().getStringExtra("athlete_id"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}