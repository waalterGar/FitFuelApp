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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    RequestQueue queue;
    String loginUrl = "http://192.168.56.1:4000/athlete/login";
    EditText email;
    EditText password;
    Button loginButton;
    Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.tv_login_mail);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.btn_launchRegister);
        queue = Volley.newRequestQueue(getApplicationContext());
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
                boolean isCorrect = true;

                if (emailText.isEmpty() ) {
                    email.setError("Email is required");
                    isCorrect = false;
                }
                if (passwordText.isEmpty()) {
                    password.setError("Password is required");
                    isCorrect = false;
                }
                if (isCorrect) {
                    JSONObject userParams = new JSONObject();
                    try {
                        userParams.put("email", emailText);
                        userParams.put("password", passwordText);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    requestApi(userParams);
                }
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AthleteRegister.class);
                startActivity(intent);
            }
        });
    }

    private void requestApi(JSONObject userParams){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, loginUrl, userParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("success", response.toString());
                try {
                    String token = response.getString("token");
                    String id = response.getString("id");
                    StoreManager storeManager = new StoreManager(getApplicationContext());
                    storeManager.setToken("user_token", token);
                    storeManager.setToken("user_id_token", id);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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