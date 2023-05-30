package com.example.lab_4;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import org.w3c.dom.Comment;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Trabajador extends AppCompatActivity {

    Button descargar,buscar,asignar;
    String texto=null;
    EmployeeRepository employeeRepository = new Retrofit.Builder()
            .baseUrl("http://192.168.9.106:8080/")
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
                employeeRepository.listarEmployees().enqueue(new Callback<List<Employee>>() {
                    @Override
                    public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                        if (response.isSuccessful()) {

                            List<Employee> employeelista=response.body();
                            Log.e("msg","correcto");
                            Gson gson = new Gson();
                            String Json1 = gson.toJson(employeelista);
                            texto= Json1;
                            guardarTexto();
                        }else{
                            Log.e("msg","error");
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Employee>> call, Throwable t) {
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

    ActivityResultLauncher<Intent> activityForResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();

                    if (data != null) {
                        try (ParcelFileDescriptor pfd =
                                     getContentResolver().openFileDescriptor(data.getData(), "w");
                             FileWriter fileWriter = new FileWriter(pfd.getFileDescriptor())) {

                            String textoAescribir = texto;
                            fileWriter.write(textoAescribir);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
    public void guardarTexto() {
        Intent intent1 = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent1.addCategory(Intent.CATEGORY_OPENABLE);
        intent1.setType("text/plain");
        intent1.putExtra(Intent.EXTRA_TITLE, "listaDeTrabajadores.txt");

        activityForResultLauncher.launch(intent1);
    }
}