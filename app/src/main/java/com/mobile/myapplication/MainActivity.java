package com.mobile.myapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button btn_add_compte;
    Spinner spinner_request_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.lst_comptes);
        btn_add_compte = findViewById(R.id.btn_add_compte);
        spinner_request_type=findViewById(R.id.spinner_request);

        String[] choix_request = {"json", "xml"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, choix_request);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_request_type.setAdapter(adapter);

        String type_req=spinner_request_type.getSelectedItem().toString();

        ApiInterface apiService = ApiClient.getRetrofitInstance(type_req).create(ApiInterface.class);

        Log.i("Amina debug", "OnCreate: ");



        spinner_request_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
               // remplir_list(selectedItem);
                //Toast.makeText(MainActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        remplir_list("json");

        btn_add_compte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });



    }

private void remplir_list(String type_req){
    ApiInterface apiService = ApiClient.getRetrofitInstance(type_req).create(ApiInterface.class);
    apiService.getComptes().enqueue(new Callback<List<Compte>>() {

        @Override
        public void onResponse(Call<List<Compte>> call, Response<List<Compte>> response) {
            Log.i("Amina debug", "onResponse: ");
            if (response.isSuccessful() && response.body() != null) {
                Log.i("Amina debug", "onResponse:success ");
                List<Compte> comptes = response.body();
                CompteAdapter adapter = new CompteAdapter(MainActivity.this, comptes);

                listView.setAdapter(adapter);
            }
        }

        @Override
        public void onFailure(Call<List<Compte>> call, Throwable t) {
            Log.i("Amina", "onFailure: "+ t.getMessage());
            Toast.makeText(MainActivity.this, "Amina Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
        }
    });
}

    private void showDialog() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.layout_ajout);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(800, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        Spinner spinner = dialog.findViewById(R.id.spinnerType);
        Button submitButton = dialog.findViewById(R.id.btn_ajout);
        EditText soldecompte = dialog.findViewById(R.id.editTextSolde);

        String[] choix_type = {"COURANT", "EPARGNE"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, choix_type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);




        submitButton.setOnClickListener(v -> {
            String selectedType = spinner.getSelectedItem().toString();
            double solde = Double.parseDouble(soldecompte.getText().toString());

            String type_req=spinner_request_type.getSelectedItem().toString();
            ApiInterface apiService = ApiClient.getRetrofitInstance(type_req).create(ApiInterface.class);
            Compte compte = new Compte();
            compte.setSolde(solde);
            compte.setType(selectedType);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
            String formattedDate = dateFormat.format(new Date());
            compte.setDateCreation(formattedDate);

            Call<Compte> call = apiService.createCompte(compte);
            call.enqueue(new retrofit2.Callback<Compte>() {
                @Override
                public void onResponse(Call<Compte> call, retrofit2.Response<Compte> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Compte ajouté!", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            Log.i("mydev", "onResponse: "+response.errorBody().string());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Toast.makeText(MainActivity.this, "error ajout", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Compte> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error==== " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


            dialog.dismiss();
        });

        dialog.show();
    }
}
