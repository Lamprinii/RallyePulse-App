package com.unipi.konlamp.rallyepulse_app;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.unipi.konlamp.rallyepulse_app.API.Competitor;
import com.unipi.konlamp.rallyepulse_app.API.RetrofitInstance;
import com.unipi.konlamp.rallyepulse_app.API.TimeKeeping;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntryList extends AppCompatActivity {

    List<Competitor> competitors;
    Long id;

    public TextView titleTextView;
    private TableLayout tableLayout;

    private Competitor competitor;

    private void fillTableWithData() {
        TableRow tableRow1 = new TableRow(this);
        tableRow1.setPadding(20,20,20,20);
        tableRow1.setBackgroundColor(Color.parseColor("#00D9FF"));
        TextView textView1 = new TextView(this);
        textView1.setText("Competitor No");
        textView1.setTypeface(null, Typeface.BOLD);
        textView1.setPadding(5, 16, 5, 16);
        textView1.setGravity(Gravity.CENTER);
        textView1.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        tableRow1.addView(textView1);
        TextView textView2 = new TextView(this);
        textView2.setText("Driver");
        textView2.setTypeface(null, Typeface.BOLD);
        textView2.setPadding(5, 16, 5, 16);
        textView2.setGravity(Gravity.CENTER);
        textView2.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        tableRow1.addView(textView2);

        TextView textView3 = new TextView(this);
        textView3.setText("Co Driver");
        textView3.setTypeface(null, Typeface.BOLD);
        textView3.setPadding(5, 16, 5, 16);
        textView3.setGravity(Gravity.CENTER);
        textView3.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        tableRow1.addView(textView3);

        TextView textView4 = new TextView(this);
        textView4.setText("Vehicle");
        textView4.setTypeface(null, Typeface.BOLD);
        textView4.setPadding(5, 16, 5, 16);
        textView4.setGravity(Gravity.CENTER);
        textView4.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        tableRow1.addView(textView4);

        TextView textView5 = new TextView(this);
        textView5.setText("Cat/Class");
        textView5.setTypeface(null, Typeface.BOLD);
        textView5.setPadding(5, 16, 5, 16);
        textView5.setGravity(Gravity.CENTER);
        textView5.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        tableRow1.addView(textView5);
        tableLayout.addView(tableRow1);
        String[][] data = new String[competitors.size()][2];
        for (int i=0; i < competitors.size(); i++) {
            String [] temp = {
                    competitors.get(i).getCo_number().toString(), competitors.get(i).getDriver() ,competitors.get(i).getCodriver(), competitors.get(i).getVehicle(), competitors.get(i).getCategory() + "/" + competitors.get(i).getCar_class()
            };
            data[i] = temp;
        }
        for (int j=0; j < data.length; j++) {
            TableRow tableRow = new TableRow(this);
            String[] row = data[j];
            if ((j+1)%2 == 0) {
                tableRow.setBackgroundColor(Color.GRAY);
            }
            for (int i = 0; i < row.length; i++) {
                TextView textView = new TextView(this);
                textView.setText(row[i]);
                textView.setPadding(16, 16, 16, 16);
                textView.setGravity(Gravity.CENTER);
                textView.setLayoutParams(new TableRow.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));

                tableRow.addView(textView);
            }

            tableLayout.addView(tableRow);
        }
    }
    private void showMessage(String title, String message){
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stage_times);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        titleTextView = findViewById(R.id.titleTextView);
        titleTextView.setText(getString(R.string.entrylist));
        tableLayout = findViewById(R.id.tableLayout);

        RetrofitInstance.rallyePulseAPI().getCompetitors().enqueue(new Callback<List<Competitor>>() {
            @Override
            public void onResponse(Call<List<Competitor>> call, Response<List<Competitor>> response) {
                if (response.isSuccessful()) {
                    competitors = response.body();
                    Log.d("MainActivity", "Received times: " + competitors);
                    fillTableWithData();
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
            public void onFailure(Call<List<Competitor>> call, Throwable t) {
                Log.e("MainActivity", "Failure: " + t.getMessage());
            }
        });
    }
}