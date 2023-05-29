package com.example.lab_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Button ver = findViewById(R.id.button);
        EditText e = findViewById(R.id.editTextNumber);

        ver.setOnClickListener((view)->{
            String num = e.getText().toString();
            Intent intent = new Intent(this, ResultadosActivity.class);
            intent.putExtra("id",Integer.parseInt(num));
            startActivity(intent);

        });
    }
}