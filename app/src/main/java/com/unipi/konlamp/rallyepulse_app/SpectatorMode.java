package com.unipi.konlamp.rallyepulse_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
    @Override
    public void onDialogPositiveClick(String inputText) {
        Log.d("MainActivity", "Received input: " + inputText);
    }
    public void entrylist(View view){

    }

    public void startlist(View view){

    }

    public void overalls(View view){

    }

}