package com.example.lab_4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tutor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);

        EmployeeRepository employeeRepository = new Retrofit.Builder()
                .baseUrl("http://10.100.59.95:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(EmployeeRepository.class);
    }
}