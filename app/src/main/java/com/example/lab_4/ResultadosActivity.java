package com.example.lab_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultadosActivity extends AppCompatActivity {
    EmployeeRepository employeeRepository = new Retrofit.Builder()
            .baseUrl("http://192.168.9.106:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(EmployeeRepository.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        Intent intent = getIntent();
        int ident = intent.getIntExtra("id",0);
        TextView id = findViewById(R.id.textView);
        TextView nombre = findViewById(R.id.textView2);
        TextView apellido = findViewById(R.id.textView3);
        TextView email = findViewById(R.id.textView4);
        employeeRepository.empleado(ident).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.isSuccessful()) {

                    Employee employee=response.body();
                    Log.e("msg","correcto");
                    Log.e("msg1",employee.getEmail());
                    nombre.setText(employee.getFirstName());
                    apellido.setText(employee.getLastName());
                    email.setText(employee.getEmail());

                }else{
                    Log.e("msg","error");
                }

            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }
}