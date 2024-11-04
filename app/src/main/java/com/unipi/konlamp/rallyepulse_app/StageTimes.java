package com.unipi.konlamp.rallyepulse_app;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.Gravity;
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

public class StageTimes extends AppCompatActivity {
    List<TimeKeeping> times;
    Long id;

    public TextView titleTextView;
    private TableLayout tableLayout;

    private Competitor competitor;
    public void getCompetitor(Long id){
        RetrofitInstance.rallyePulseAPI().getByid(id).enqueue(new Callback<Competitor>() {
            @Override
            public void onResponse(Call<Competitor> call, Response<Competitor> response) {
                if (response.isSuccessful()) {
                    competitor = response.body();
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

    private void fillTableWithData() {
        TableRow tableRow1 = new TableRow(this);
        TextView textView1 = new TextView(this);
        textView1.setText("Competitor No");
        textView1.setPadding(16, 16, 16, 16);
        textView1.setGravity(Gravity.CENTER);
        textView1.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        tableRow1.addView(textView1);
        TextView textView2 = new TextView(this);
        textView2.setText("Driver");
        textView2.setPadding(16, 16, 16, 16);
        textView2.setGravity(Gravity.CENTER);
        textView2.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        tableRow1.addView(textView2);

        TextView textView3 = new TextView(this);
        textView3.setText("Total Time");
        textView3.setPadding(16, 16, 16, 16);
        textView3.setGravity(Gravity.CENTER);
        textView3.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        tableRow1.addView(textView3);
        tableLayout.addView(tableRow1);
        String[][] data = new String[times.size()][2];
        for (int i=0; i < times.size(); i++) {
            String [] temp = {
            times.get(i).getId().getCompetitorid().toString(), times.get(i).getName() ,times.get(i).getTotal_time().toString()
    };
    data[i] = temp;
    }
        for (String[] row : data) {
            TableRow tableRow = new TableRow(this);

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
        id = this.getIntent().getLongExtra("timekeeping",0);
        titleTextView = findViewById(R.id.titleTextView);
        titleTextView.setText(getString(R.string.stagetimes) + " " + id);
        tableLayout = findViewById(R.id.tableLayout);

        RetrofitInstance.rallyePulseAPI().getStartedSpecialStages(id).enqueue(new Callback<List<TimeKeeping>>() {
            @Override
            public void onResponse(Call<List<TimeKeeping>> call, Response<List<TimeKeeping>> response) {
                if (response.isSuccessful()) {
                    times = response.body();
                    Log.d("MainActivity", "Received times: " + times);
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
            public void onFailure(Call<List<TimeKeeping>> call, Throwable t) {
                Log.e("MainActivity", "Failure: " + t.getMessage());
            }
        });
    }
}