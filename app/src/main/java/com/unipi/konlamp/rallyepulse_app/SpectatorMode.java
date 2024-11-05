package com.unipi.konlamp.rallyepulse_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.unipi.konlamp.rallyepulse_app.API.Competitor;
import com.unipi.konlamp.rallyepulse_app.API.RetrofitInstance;
import com.unipi.konlamp.rallyepulse_app.API.SpecialStage;
import com.unipi.konlamp.rallyepulse_app.API.StartListOb;
import com.unipi.konlamp.rallyepulse_app.API.TimeKeeping;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpectatorMode extends AppCompatActivity implements PopUp.MyDialogListener {
    private static final int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_spectator_mode);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void stageresults(View view){
        PopUp dialog = new PopUp();
        dialog.setTargetFragment(null, REQUEST_CODE);
        dialog.show(getSupportFragmentManager(), "PopUp");

    }


    public void stagetimesclick(Long id) {
        Intent myintent = new Intent(this, StageTimes.class);
        myintent.putExtra("timekeeping", id);
        startActivity(myintent);
    }
    @Override
    public void onDialogPositiveClick(String inputText) {
        Log.d("MainActivity", "Received input: " + inputText);
        stagetimesclick(Long.parseLong(inputText));
    }
    public void entrylist(View view){
        Intent myintent = new Intent(this, EntryList.class);
        startActivity(myintent);
    }

    public void startlist(View view){
        Intent myintent = new Intent(this, StartList.class);
        startActivity(myintent);
    }

    public void overalls(View view){
        Intent myintent = new Intent(this, Overalls.class);
        startActivity(myintent);
    }

}