package com.example.lab_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tutor extends AppCompatActivity {
    Button verinfo, descargarhorarios;
    EmployeeRepository employeeRepository = new Retrofit.Builder()
            .baseUrl("http://192.168.9.106:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(EmployeeRepository.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);
        verinfo = findViewById(R.id.ver_info);

        verinfo.setOnClickListener((view)->{
            startActivity(new Intent(Tutor.this, InfoActivity.class));
        });

    }
}