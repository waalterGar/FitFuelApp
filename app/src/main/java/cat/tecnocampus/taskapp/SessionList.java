package cat.tecnocampus.taskapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class SessionList extends AppCompatActivity implements cat.tecnocampus.taskapp.ItemClickListener{
    RequestQueue queue;
    RecyclerView recyclerView;
    String url= "http://192.168.56.1:4000/athletes/";
    ArrayList<Session> dataSet=new ArrayList<>();
    cat.tecnocampus.taskapp.SessionAdapter sessionAdapter;
    RecyclerView.LayoutManager layoutManager;
    StoreManager storeManager;

    ActivityResultLauncher<Intent> activityResultLauncher;

    private void createDummy(){
        dataSet.add(new Session(0, "AAA", "AAA", "AAA", "AAA", 0));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_list);
        storeManager = new StoreManager(getApplicationContext());
        url +=  storeManager.getToken("user_id_token") + "/sessions";
        Log.v("url", url);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Session List");

        recyclerView = findViewById(R.id.sessions_view);

        queue = Volley.newRequestQueue(getApplicationContext());
        sessionAdapter = new cat.tecnocampus.taskapp.SessionAdapter(dataSet);
        recyclerView.setAdapter(sessionAdapter);


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        requestApi();
        sessionAdapter.setClickListener(this);


        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void requestApi(){

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.v("success", response.toString());
                try {
                    if (response.length() > 0) {
                        dataSet.clear();

                        for (int i = 0; i < response.length(); i++) {
                            //Log.v("POKEMON NAME", jsArr.getJSONObject(i).getString("name"));
                            int id_training_session = response.getJSONObject(i).getInt("id_training_session");
                            String name = response.getJSONObject(i).getString("name");
                            String description = response.getJSONObject(i).getString("description");
                            String session_date = response.getJSONObject(i).getString("session_date");
                            String trainer_notes = response.getJSONObject(i).getString("trainer_notes");
                            int athlete_id = response.getJSONObject(i).getInt("athlete_id");
                            dataSet.add(new Session(id_training_session, name, description, session_date, trainer_notes, athlete_id));
                        }
                        sessionAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("error", error.toString());
            }
        });
        jsonArrayRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
            }
        });


        queue.add(jsonArrayRequest);
    }

    public void onClick(View view, int position, int btn_type) {
        final cat.tecnocampus.taskapp.Session Session = dataSet.get(position);
        Log.v("BEFORE", "before");
        Log.v("btn_type", Integer.toString(btn_type));
        if(btn_type == 1) {
            Intent intent = new Intent(this, SessionDetails.class);
            Log.v("Session id:", Integer.toString(Session.getId_training_session()));

            intent.putExtra("id_training_session", Integer.toString(Session.getId_training_session()));
            intent.putExtra("name", Session.getName());
            intent.putExtra("description", Session.getDescription());
            intent.putExtra("session_date", Session.getSession_date());
            intent.putExtra("trainer_notes", Session.getTrainer_notes());
            intent.putExtra("athlete_id", Integer.toString(Session.getAthlete_id()));

            activityResultLauncher.launch(intent);

            Log.v("1", "1");
        }
        if(btn_type == 2){
            Intent intent = new Intent(this, SessionExercises.class);
            intent.putExtra("id_training_session", Integer.toString(Session.getId_training_session()));

            activityResultLauncher.launch(intent);
        }
    }
}