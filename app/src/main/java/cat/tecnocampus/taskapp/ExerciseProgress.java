package cat.tecnocampus.taskapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExerciseProgress extends AppCompatActivity {
    RequestQueue queue;
    RecyclerView recyclerView;
    String getExerciseProgressRecordsUrl = "http://192.168.56.1:4000/athletes/";
    ArrayList<ExerciseProgressRecord> dataSet=new ArrayList<>();
    ExerciseProgressRecordAdapter exerciseProgressRecordAdapter;
    RecyclerView.LayoutManager layoutManager;
    StoreManager storeManager;

    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_progress);
        recyclerView = findViewById(R.id.exercise_progression_view);
        storeManager = new StoreManager(getApplicationContext());
        String id = storeManager.getToken("user_id_token");
        getExerciseProgressRecordsUrl +=  id + "/exercises/";

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Exercise Progress");

        queue = Volley.newRequestQueue(getApplicationContext());
        exerciseProgressRecordAdapter = new ExerciseProgressRecordAdapter(dataSet);
        recyclerView.setAdapter(exerciseProgressRecordAdapter);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        requestRecords(getIntent().getStringExtra("id_exercise"));

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
            }
        });

        Log.v("onCreate", "onCreate done");
    }

    private void requestRecords(String exerciseId) {
        String finalUrl = getExerciseProgressRecordsUrl + exerciseId + "/progression";
        Log.v("FINAL URL", finalUrl);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, finalUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("onResponse", response.toString());
                try {
                    //iterate through each date
                    for (int i = 0; i < response.names().length(); i++) {
                        JSONArray jsonArray = response.getJSONArray(response.names().getString(i));
                        //iterate through each exerciseProgressRecord of each date
                        for (int j = 0; j < jsonArray.length(); j++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(j);
                            //add exerciseProgressRecord to dataSet with the following constructor: ExerciseProgressRecord(String date, String exercise_name, String num_set, String repetitions, String weight, String rpe, String rir)
                            dataSet.add(new ExerciseProgressRecord(jsonObject.getString("date"), jsonObject.getString("exercise_name"), jsonObject.getString("num_set"), jsonObject.getString("repetitions"), jsonObject.getString("weight"), jsonObject.getString("rpe"), jsonObject.getString("rir")));
                        }
                    }
                    exerciseProgressRecordAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("onErrorResponse", error.toString());
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
}