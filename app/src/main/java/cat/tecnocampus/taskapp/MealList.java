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
import android.view.View;

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

public class MealList extends AppCompatActivity implements cat.tecnocampus.taskapp.ItemClickListener{
    RequestQueue queue;
    RecyclerView recyclerView;
    String getMealsUrl = "http://192.168.56.1:4000/athletes/";
    String mealRecordIncompleteUrl = "http://192.168.56.1:4000/mealRecords/";
    ArrayList<MealRecord> dataSet=new ArrayList<>();
    MealRecordAdapter mealAdapter;
    RecyclerView.LayoutManager layoutManager;
    StoreManager storeManager;

    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);
        recyclerView = findViewById(R.id.meals_view);

        queue = Volley.newRequestQueue(getApplicationContext());
        mealAdapter = new MealRecordAdapter(dataSet);
        recyclerView.setAdapter(mealAdapter);
        storeManager = new StoreManager(getApplicationContext());
        String id = storeManager.getToken("user_id_token");
        getMealsUrl +=  id + "/mealRecords";

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Meal Records");

        Log.v("getMealsUrl", getMealsUrl);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        requestMeals();
        mealAdapter.setClickListener(this);


        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
            }
        });

    }

    private void requestMeals(){

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, getMealsUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.v("success", response.toString());
                try {
                    if (response.length() > 0) {
                        dataSet.clear();

                        for (int i = 0; i < response.length(); i++) {
                            //Log.v("POKEMON NAME", jsArr.getJSONObject(i).getString("name"));
                            int id_meal_record= response.getJSONObject(i).getInt("id_meal_record");
                            int id_meal = response.getJSONObject(i).getInt("meal_id");
                            String meal_name = response.getJSONObject(i).getString("meal_name");
                            int quantity = response.getJSONObject(i).getInt("quantity");
                            String date = response.getJSONObject(i).getString("date");
                            String eaten = response.getJSONObject(i).getString("eaten");
                            dataSet.add(new MealRecord(id_meal_record, id_meal, meal_name, quantity, date, eaten));
                        }
                        mealAdapter.notifyDataSetChanged();
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

    private void requestApi(String id){
        Log.v("PUT REQUEST", id);
        String finalUrl = mealRecordIncompleteUrl + id + "/setCompleted";
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PUT, finalUrl,null , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("PUT RESPONSE", response.toString());
                try {
                    if (response.getString("status").equals("success")) {
                        Log.v("PUT RESPONSE", "success");
                        finish();
                        startActivity(getIntent());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    public void onClick(View view, int position, int btn_type) {
        if(btn_type == 4) {
            String id = Integer.toString(dataSet.get(position).getId_meal_record());
            Log.v("completeClicked id:", id);
            requestApi(id);
            finish();
        }
        if(btn_type == 5){
            String id = Integer.toString(dataSet.get(position).getId_meal());
            Intent intent = new Intent(this, MealDetails.class);
            intent.putExtra("id_meal", id);
            activityResultLauncher.launch(intent);
        }
    }
}