package com.unipi.konlamp.rallyepulse_app;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;

import com.unipi.konlamp.rallyepulse_app.API.Competitor;
import com.unipi.konlamp.rallyepulse_app.API.RetrofitInstance;
import com.unipi.konlamp.rallyepulse_app.API.SpecialStage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopUp extends DialogFragment {
    public AutoCompleteTextView autoCompleteTextView;
    public ArrayAdapter<String> adapterItem;
    public String item;
    String [] items = {"Lamia", "Athina"};

    private void showMessage(String title, String message){
        new AlertDialog.Builder(this.getContext())
                .setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .show();
    }

    public void adddropdown(){
        adapterItem = new ArrayAdapter<String>(this.getContext(), R.layout.list_item, items);
        autoCompleteTextView.setAdapter(adapterItem);
    }
    public interface MyDialogListener {
        void onDialogPositiveClick(String inputText);
    }

    private MyDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        try {
            listener = (MyDialogListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Η Activity πρέπει να υλοποιεί το MyDialogListener");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_pop_up, null);
        autoCompleteTextView = view.findViewById(R.id.auto_complete_textview);
        RetrofitInstance.rallyePulseAPI().getStartedSpecialStages().enqueue(new Callback<List<SpecialStage>>() {
            @Override
            public void onResponse(Call<List<SpecialStage>> call, Response<List<SpecialStage>> response) {
                if (response.isSuccessful()) {
                    List<SpecialStage> specialStage = response.body();
                    Log.d("MainActivity", "Created User: " + specialStage);
                    ArrayList<String> ss = new ArrayList<>();
                    for (int i=0; i<specialStage.size(); i++) {
                        ss.add(specialStage.get(i).getName());
                    }
                    items = ss.toArray(new String[ss.size()]);
                    adddropdown();
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
            public void onFailure(Call<List<SpecialStage>> call, Throwable t) {
                Log.e("MainActivity", "Failure: " + t.getMessage());
            }
        });
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString();
            }
        });
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //sendResult(Activity.RESULT_OK, item);
                        listener.onDialogPositiveClick(item);
                        //PopUp.this.getDialog().dismiss();
                        dismiss(); // Κλείνει το DialogFragment
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //PopUp.this.getDialog().cancel();
                        dismiss();
                    }
                });
        return builder.create();
    }



}
