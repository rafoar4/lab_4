package com.example.lab_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Trabajador extends AppCompatActivity {

    Button descargar,buscar,asignar;

    EmployeeRepository employeeRepository = new Retrofit.Builder()
            .baseUrl("http://localhost:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(EmployeeRepository.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajador);

        descargar=findViewById(R.id.descargar_lista);
        buscar=findViewById(R.id.ir_buscar);
        asignar=findViewById(R.id.ir_asignar);

        descargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employeeRepository.listarEmployees().enqueue(new Callback<EmployeeDto>() {
                    @Override
                    public void onResponse(Call<EmployeeDto> call, Response<EmployeeDto> response) {
                        if (response.isSuccessful()) {

                            EmployeeDto employeeDto=response.body();
                            Log.e("msg","correcto");
                            Employee[] employees=employeeDto.get_embedded().getEmployees();
                            for (Employee e: employees) {
                                Log.e("TAG", "onResponse: "+ e.getFirst_name());

                            }
                            /*guardarComoObjeto(employees);*/
                        }else{
                            Log.e("msg","error");
                        }

                    }

                    @Override
                    public void onFailure(Call<EmployeeDto> call, Throwable t) {
                        t.printStackTrace();

                    }
                });


            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Trabajador.this,Buscar.class));

            }
        });

        asignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Trabajador.this,Asignar.class));

            }
        });

    }

    public void guardarComoObjeto(Employee[] listaEmployees) {

        String fileName = "listaDeTrabajadores.txt";

        try (FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(listaEmployees);
            Log.d("TAG", "Guardado exitoso");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}