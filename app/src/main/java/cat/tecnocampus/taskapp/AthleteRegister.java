package cat.tecnocampus.taskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class AthleteRegister extends AppCompatActivity {
    RequestQueue queue;
    String url = "http://192.168.56.1:4000/athletes";
    EditText name;
    EditText email;
    EditText password;
    EditText phone;
    EditText birthdate;
    EditText gender;
    EditText weight;
    EditText height;

    Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete_register);
        name = findViewById(R.id.et_AthleteName);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        phone = findViewById(R.id.et_phone);
        birthdate = findViewById(R.id.et_birth_date);
        gender = findViewById(R.id.et_gender);
        weight = findViewById(R.id.et_weight);
        height = findViewById(R.id.et_height);
        btn_register = findViewById(R.id.btn_Register);
        queue = Volley.newRequestQueue(getApplicationContext());

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //put et values into json object after parse birthdate  to date and weight and height to double
                    JSONObject userParams = new JSONObject();
                    userParams.put("name", name.getText().toString());
                    userParams.put("email", email.getText().toString());
                    userParams.put("password", password.getText().toString());
                    userParams.put("phone", phone.getText().toString());
                    userParams.put("birth-dated", birthdate.getText().toString());
                    userParams.put("height", Double.parseDouble(height.getText().toString()));
                    userParams.put("weight", Double.parseDouble(height.getText().toString()));


                    requestApi(userParams);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }

    private void requestApi(JSONObject userParams){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, userParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("success", response.toString());
                try {
                    Toast.makeText(getApplicationContext(), "User created", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("error", error.toString());
                if(error.networkResponse.statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
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