package cat.tecnocampus.taskapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MealDetails extends AppCompatActivity {
    RequestQueue queue;
    String url = "http://192.168.56.1:4000/meals/";
    TextView tv_meal_id;
    TextView tv_meal_name;
    TextView tv_meal_description;
    TextView tv_meal_recipe;
    TextView tv_meal_calories;
    TextView tv_meal_proteins;
    TextView tv_meal_carbs;
    TextView tv_meal_fats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Meal Details");

        //assign textviews from meal_details.xml to params
        tv_meal_id = findViewById(R.id.tv_mealId_value);
        tv_meal_name = findViewById(R.id.tv_mealName_value);
        tv_meal_description = findViewById(R.id.tv_mealDescription_value);
        tv_meal_recipe = findViewById(R.id.tv_mealRecipe_value);
        tv_meal_calories = findViewById(R.id.tv_mealCalories_value);
        tv_meal_proteins = findViewById(R.id.tv_mealProtein_value);
        tv_meal_carbs = findViewById(R.id.tv_mealCarbs_value);
        tv_meal_fats = findViewById(R.id.tv_mealFat_value);

        queue = Volley.newRequestQueue(getApplicationContext());

        Log.v("Intent id: ", getIntent().getStringExtra("id_meal"));

        requestApi(getIntent().getStringExtra("id_meal"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void requestApi(String id_meal){
        String finalUrl = url + id_meal;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, finalUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("success", response.toString());
                try {
                    if (response.length() > 0) {
                        for (int i = 0; i < response.length(); i++) {
                            tv_meal_id.setText(response.getString("id_meal"));
                            tv_meal_name.setText(response.getString("name"));
                            tv_meal_description.setText(response.getString("description"));
                            tv_meal_recipe.setText(response.getString("recipe"));
                            tv_meal_calories.setText(response.getString("calories"));
                            tv_meal_proteins.setText(response.getString("protein"));
                            tv_meal_carbs.setText(response.getString("carbohydrates"));
                            tv_meal_fats.setText(response.getString("fat"));
                        }
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
}