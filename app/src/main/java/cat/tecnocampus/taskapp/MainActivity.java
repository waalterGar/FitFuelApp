package cat.tecnocampus.taskapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    RecyclerView.LayoutManager layoutManager;
    ActivityResultLauncher<Intent> activityResultLauncher;
    StoreManager storeManager;

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.v("restore","restore done" );
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        storeManager = new StoreManager(getApplicationContext());
        storeManager.setToken("user_id_token", "4");

        layoutManager = new LinearLayoutManager(this);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        if(item.getItemId()== R.id.optnMyProfile){
            Intent intent = new Intent(this, Athlete.class);
            activityResultLauncher.launch(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void sessionListClicked(View view) {
        Intent intent = new Intent(this, SessionList.class);
        activityResultLauncher.launch(intent);
    }

    public void mealListClicked(View view) {
        Intent intent = new Intent(this, MealList.class);
        activityResultLauncher.launch(intent);
    }
}