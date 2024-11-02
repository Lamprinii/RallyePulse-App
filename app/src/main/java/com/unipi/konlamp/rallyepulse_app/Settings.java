package com.unipi.konlamp.rallyepulse_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.unipi.konlamp.rallyepulse_app.API.Competitor;
import com.unipi.konlamp.rallyepulse_app.API.RetrofitInstance;
import com.unipi.konlamp.rallyepulse_app.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Settings extends AppCompatActivity {
    private Competitor competitor;

    public TextView textView9;
    public TextView textView10;

    public EditText editTextTextEmailAddress;

    public EditText editTextPhone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        competitor = (Competitor)this.getIntent().getSerializableExtra("competitor");

        textView9 = findViewById(R.id.textView9);
        textView10 = findViewById(R.id.textView10);
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextPhone = findViewById(R.id.editTextPhone);
        textView9.setText(competitor.getDriver());
        textView10.setText(competitor.getCodriver());
        editTextTextEmailAddress.setText(competitor.getEmail());
        editTextPhone.setText(competitor.getTelephone());


    }

    private void showMessage(String title, String message){
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .show();
    }

    public void jumptocompetitor(Competitor competitor){
        Intent myIntent = new Intent(this, CompetitorMode.class);
        myIntent.putExtra("competitor",competitor);
        startActivity(myIntent);
    }

    public void update(View view) {
       competitor.setEmail(editTextTextEmailAddress.getText().toString());
       competitor.setTelephone(editTextPhone.getText().toString());


        RetrofitInstance.rallyePulseAPI().updatecompetitor(competitor).enqueue(new Callback<Competitor>() {
            @Override
            public void onResponse(Call<Competitor> call, Response<Competitor> response) {
                if (response.isSuccessful()) {
                    Competitor createdUser = response.body();
                    Log.d("MainActivity", "Created User: " + createdUser);
                    showMessage("Success.", "The competitor's info has been updated!");
                    jumptocompetitor(createdUser);
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("MainActivity", "Error: " + errorBody);
                        showMessage("Error", getString(R.string.errorauth));
                    } catch (IOException e) {

                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<Competitor> call, Throwable t) {
                Log.e("MainActivity", "Failure: " + t.getMessage());
            }
        });

    }

}