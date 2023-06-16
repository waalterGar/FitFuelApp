package cat.tecnocampus.taskapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SessionExercises extends AppCompatActivity implements cat.tecnocampus.taskapp.ItemClickListener {
    RequestQueue queue;
    String sessionId;
    RecyclerView recyclerView;
    String getSessionsUrl = "http://192.168.56.1:4000/sessions/";
    String executionIncompleteUrl = "http://192.168.56.1:4000/executions/";
    ArrayList<Execution> dataSet=new ArrayList<>();
    cat.tecnocampus.taskapp.ExecutionAdapter executionAdapter;
    RecyclerView.LayoutManager layoutManager;

    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        dataSet= savedInstanceState.getParcelableArrayList("dataSet");
        getSessionsUrl = savedInstanceState.getString("url");

        executionAdapter = new ExecutionAdapter(dataSet);
        recyclerView.setAdapter(executionAdapter);
        Log.v("restore","restore done" );
        requestExecutions(sessionId);

        executionAdapter.setClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("dataSet", dataSet);
        outState.putString("url", getSessionsUrl);
        super.onSaveInstanceState(outState);
    }

    protected void onResume() {
        super.onResume();
        requestExecutions(sessionId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_exercises);

        recyclerView = findViewById(R.id.execution_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Session Exercises");

        queue = Volley.newRequestQueue(getApplicationContext());
        executionAdapter  = new cat.tecnocampus.taskapp.ExecutionAdapter(dataSet);
        recyclerView.setAdapter(executionAdapter);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        sessionId = getIntent().getStringExtra("id_training_session");

        requestExecutions(sessionId);
        executionAdapter.setClickListener(this);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
            }
        });
    }

    private void requestExecutions(String sessionId){
        getSessionsUrl = getSessionsUrl + sessionId +"/executions";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, getSessionsUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("success", response.toString());
                // add executions to dataSet from response with the structure {"Bench Press":[{"id_execution":4,"num_set":3,"repetitions":12,"weight":150,"rpe":8,"rir":2,"done":1,"training_session_id":2,"exercise_id":2,"id_exercise":2,"exercise_name":"Bench Press"},{"id_execution":6,"num_set":4,"repetitions":12,"weight":150,"rpe":9,"rir":1,"done":0,"training_session_id":2,"exercise_id":2,"id_exercise":2,"exercise_name":"Bench Press"},{"id_execution":106,"num_set":11,"repetitions":11,"weight":11,"rpe":11,"rir":11,"done":null,"training_session_id":2,"exercise_id":2,"id_exercise":2,"exercise_name":"Bench Press"}],"Deadlift ":[{"id_execution":104,"num_set":922,"repetitions":922,"weight":922,"rpe":922,"rir":922,"done":null,"training_session_id":2,"exercise_id":3,"id_exercise":3,"exercise_name":"Deadlift "}]}:
                dataSet.clear();
                for(int i=0; i<response.names().length();i++){
                    String exerciseName = response.names().optString(i);
                    JSONArray executions = response.optJSONArray(exerciseName);
                    for(int j=0; j<executions.length();j++){
                        JSONObject execution = executions.optJSONObject(j);
                        dataSet.add(new Execution(Integer.toString(execution.optInt("id_execution")), Integer.toString(execution.optInt("exercise_id")), execution.optString("exercise_name"), Integer.toString(execution.optInt("num_set")), Integer.toString(execution.optInt("repetitions")), Integer.toString(execution.optInt("weight")), Integer.toString(execution.optInt("rpe")), Integer.toString(execution.optInt("rir")), Integer.toString(execution.optInt("done"))));
                    }
                }
                executionAdapter.notifyDataSetChanged();

                Log.v("dataSet", dataSet.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("error", error.toString());
            }
        });

        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
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


        queue.add(jsonObjectRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void requestApi(String id){
        Log.v("PUT REQUEST", id);
        String finalUrl = executionIncompleteUrl + id + "/setCompleted";
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PUT, finalUrl,null , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("PUT RESPONSE", response.toString());
                requestExecutions(sessionId);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("PUT ERROR", error.toString());
                if (error.networkResponse != null) {
                    Log.v("PUT ERROR", error.networkResponse.statusCode + " " + error.networkResponse.data);
                }
            }
        });

        jsonRequest.setRetryPolicy(new RetryPolicy() {
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

        queue.add(jsonRequest);
    }

    @Override
    public void onClick(View view, int position, int btn_type) {
        if (btn_type == 3) {
            String id = dataSet.get(position).getId_execution();
            Log.v("completeClicked id:", id);
            requestApi(id);
            finish();
        }
        if(btn_type == 6){ // ExerciseProgress
            String id = dataSet.get(position).getId_exercise();
            Log.v("exerciseProgress id:", id);
            Intent intent = new Intent(this, ExerciseProgress.class);
            intent.putExtra("id_exercise", id);
            activityResultLauncher.launch(intent);
        }
    }
}