package com.unipi.konlamp.rallyepulse_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.unipi.konlamp.rallyepulse_app.Settings;
import com.unipi.konlamp.rallyepulse_app.API.Competitor;

public class CompetitorMode extends AppCompatActivity {

    private TextView textView6;
    private TextView textView4;
    private Competitor competitor;
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


    }

    public void entrylist(View view){


    }

    public void startlist(View view){


    }

    public void overalls(View view){


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