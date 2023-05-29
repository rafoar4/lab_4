package com.example.lab_4;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoActivity extends AppCompatActivity {
    EmployeeRepository employeeRepository = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(EmployeeRepository.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Button ver = findViewById(R.id.button);
        EditText e = findViewById(R.id.editTextNumber);

        ver.setOnClickListener((view)->{
            if(e.getText().length()!=0) {
                String num = e.getText().toString();
                if(Integer.parseInt(num)>99){
                    Intent intent = new Intent(this, ResultadosActivity.class);
                    intent.putExtra("id",Integer.parseInt(num));
                    startActivity(intent);
                }else{
                    Toast.makeText(InfoActivity.this,"Ingrese un numero valido",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(InfoActivity.this,"Ingrese un numero valido",Toast.LENGTH_LONG).show();

            }

        });
        Button descargar = findViewById(R.id.button2);
        descargar.setOnClickListener((view)->{
            if(e.getText().length()!=0) {
                String num = e.getText().toString();
                if(Integer.parseInt(num)>99){
                    employeeRepository.empleado(Integer.parseInt(num)).enqueue(new Callback<Employee>() {
                        @Override
                        public void onResponse(Call<Employee> call, Response<Employee> response) {
                            if (response.isSuccessful()) {

                                Employee employee=response.body();
                                Log.e("msg1",employee.getMeeting().toString());
                                if(employee.getMeeting().toString().equals("1")){
                                    descargarConDownloadManager();
                                    Toast.makeText(InfoActivity.this,"Se ha descargado",Toast.LENGTH_LONG).show();

                                }else{
                                    Toast.makeText(InfoActivity.this,"No cuenta con tutorías pendientes",Toast.LENGTH_LONG).show();

                                }
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
                    Toast.makeText(InfoActivity.this,"Ingrese un numero valido",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(InfoActivity.this,"Ingrese un numero valido",Toast.LENGTH_LONG).show();

            }
        });
    }
    ActivityResultLauncher<String> launcher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {

                if (isGranted) { // permiso concedido
                    descargarConDownloadManager();

                } else {
                    Log.e("msg-test", "Permiso denegado");
                }
            });
    public void descargarConDownloadManager() {

        String permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE; //si no funciona android.Manifest.permission…

        // >29
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ||
                ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            //si tengo permisos
            String fileName = "Horario.jpg";
            String endPoint = "https://i.pinimg.com/564x/4e/8e/a5/4e8ea537c896aa277e6449bdca6c45da.jpg";

            Uri downloadUri = Uri.parse(endPoint);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            request.setAllowedOverRoaming(false);
            request.setTitle(fileName);
            request.setMimeType("image/jpeg");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, File.separator + fileName);

            DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            dm.enqueue(request);
        } else {
            //si no tiene permisos
            launcher.launch(permission);
        }
    }

}