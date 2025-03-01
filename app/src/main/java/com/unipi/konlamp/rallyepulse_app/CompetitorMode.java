package com.unipi.konlamp.rallyepulse_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.unipi.konlamp.rallyepulse_app.Settings;
import com.unipi.konlamp.rallyepulse_app.API.Competitor;

public class CompetitorMode extends AppCompatActivity implements PopUp.MyDialogListener {

    private TextView textView6;
    private TextView textView4;
    private Competitor competitor;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_competitor_mode);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textView6 = findViewById(R.id.textView6);
        textView4 = findViewById(R.id.textView4);
        competitor = (Competitor)this.getIntent().getSerializableExtra("competitor");
        textView6.setText("#" + competitor.getCo_number());
        textView4.setText(getString(R.string.hello)+ " " + competitor.getDriver().split(" ")[0] + " " + getString(R.string.and) +" " + competitor.getCodriver().split(" ")[0]);
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
    public void settings(View view){
        Intent myIntent = new Intent(this, Settings.class);
        myIntent.putExtra("competitor",competitor);
        startActivity(myIntent);


    }
    public void racemode(View view){
        Intent myIntent = new Intent(this, MainActivity.class);
        myIntent.putExtra("conumber", competitor.getCo_number());
        startActivity(myIntent);

    }
}