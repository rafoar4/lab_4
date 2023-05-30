package com.example.lab_4;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Buscar extends AppCompatActivity {
    EmployeeRepository employeeRepository = new Retrofit.Builder()
            .baseUrl("http://192.168.9.106:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(EmployeeRepository.class);
    String texto=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        EditText e = findViewById(R.id.editTextNumber2);
        Button regresar = findViewById(R.id.button4);
        regresar.setOnClickListener((view)->{
            finish();
        });
        Button descargar = findViewById(R.id.button3);
        descargar.setOnClickListener((view)->{
            if(e.getText().length()!=0) {
                String num = e.getText().toString();
                if(Integer.parseInt(num)>99){
                    employeeRepository.empleado(Integer.parseInt(num)).enqueue(new Callback<Employee>() {
                        @Override
                        public void onResponse(Call<Employee> call, Response<Employee> response) {
                            if (response.isSuccessful()) {

                                Employee employee=response.body();
                                Gson gson = new Gson();
                                String Json1 = gson.toJson(employee);
                                texto= Json1;
                                guardarTexto(Integer.parseInt(num));
                            }else{
                                Log.e("msg","error");
                            }

                        }

                        @Override
                        public void onFailure(Call<Employee> call, Throwable t) {
                            t.printStackTrace();

                        }
                    });
                }else{
                    Toast.makeText(Buscar.this,"Ingrese un numero valido",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(Buscar.this,"Ingrese un numero valido",Toast.LENGTH_LONG).show();

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
    public void guardarTexto(int id) {
        Intent intent1 = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent1.addCategory(Intent.CATEGORY_OPENABLE);
        intent1.setType("text/plain");
        intent1.putExtra(Intent.EXTRA_TITLE, "informacionDe_"+id+".txt");

        activityForResultLauncher.launch(intent1);
    }
}