package cat.tecnocampus.taskapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Athlete extends AppCompatActivity {
    RequestQueue queue;
    String url = "http://192.168.56.1:4000/athletes/";
    TextView tv_id_athlete;
    TextView tv_name;
    TextView tv_email;
    TextView tv_phone;
    TextView tv_birthdate;
    TextView tv_gender;
    TextView tv_height;
    TextView tv_weight;
    TextView tv_trainer_email;
    TextView tv_dietitian_email;
    StoreManager storeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Athlete Profile");

        tv_id_athlete = findViewById(R.id.tv_athleteId2);
        tv_name= findViewById(R.id.tv_athleteName2);
        tv_email = findViewById(R.id.tv_email2);
        tv_phone = findViewById(R.id.tv_phone_number2);
        tv_birthdate = findViewById(R.id.tv_birth_date2);
        tv_gender = findViewById(R.id.tv_gender2);
        tv_height = findViewById(R.id.tv_height2);
        tv_weight = findViewById(R.id.tv_weight2);
        tv_trainer_email = findViewById(R.id.tv_trainer_email2);
        tv_dietitian_email = findViewById(R.id.tv_dietitian_email2);
        storeManager = new StoreManager(getApplicationContext());

        queue = Volley.newRequestQueue(getApplicationContext());


        url += storeManager.getToken("user_id_token");
        requestApi();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void requestApi(){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("success", response.toString());
                try {
                    if (response.length() > 0) {
                        for (int i = 0; i < response.length(); i++) {
                            tv_id_athlete.setText(Integer.toString(response.getInt("id")));
                            tv_name.setText(response.getString("name"));
                            tv_email.setText(response.getString("email"));
                            tv_phone.setText(response.getString("phone_num"));
                            tv_birthdate.setText(response.getString("birth_date"));
                            tv_gender.setText(response.getString("gender"));
                            tv_height.setText(response.getString("height"));
                            tv_weight.setText(response.getString("weight"));
                            tv_trainer_email.setText(response.getString("trainer_email"));
                            tv_dietitian_email.setText(response.getString("dietitian_email"));
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
        queue.add(jsonObjectRequest);
    }
}