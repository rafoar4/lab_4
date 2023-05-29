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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultadosActivity extends AppCompatActivity {
    EmployeeRepository employeeRepository = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(EmployeeRepository.class);
    String texto=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        Intent intent = getIntent();
        int ident = intent.getIntExtra("id",0);
        TextView id = findViewById(R.id.textView13);
        TextView nombre = findViewById(R.id.textView);
        TextView apellido = findViewById(R.id.textView2);
        TextView email = findViewById(R.id.textView3);
        TextView phone = findViewById(R.id.textView4);
        TextView hire = findViewById(R.id.textView5);
        TextView job = findViewById(R.id.textView6);
        TextView salary = findViewById(R.id.textView7);
        TextView comision = findViewById(R.id.textView8);
        TextView manager = findViewById(R.id.textView9);
        TextView deparment = findViewById(R.id.textView10);
        TextView meeting = findViewById(R.id.textView11);
        TextView meeting_date = findViewById(R.id.textView12);

        employeeRepository.empleado(ident).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.isSuccessful()) {

                    Employee employee=response.body();
                    Log.e("msg","correcto");
                    Log.e("msg1",employee.getEmail());
                    id.setText("ID: "+ employee.getId().toString());
                    nombre.setText("First Name: "+employee.getFirstName());
                    apellido.setText("Last Name: "+employee.getLastName());
                    email.setText("Email: "+employee.getEmail());
                    phone.setText("Phone Number: "+employee.getPhoneNumber());
                    if(employee.getHireDate()!=null){
                        hire.setText("Hire Date: "+employee.getHireDate().toString());
                    }else{
                        hire.setText("Hire Date: ------");
                    }
                    job.setText("Job Id: "+employee.getJob().getId().toString());
                    salary.setText("Salary: "+employee.getSalary().toString());
                    if(employee.getCommissionPct()!=null){
                        comision.setText("ComissionPct: "+employee.getCommissionPct().toString());
                    }else{
                        comision.setText("ComissionPct: ------");
                    }
                    if(employee.getManagerId()!=null){
                        manager.setText("Manager ID: "+employee.getManagerId().toString());
                    }else{
                        manager.setText("Manager ID: ------");
                    }
                    deparment.setText("Deparment ID: "+employee.getDepartmentId().toString());
                    meeting.setText("Meeting: "+employee.getMeeting().toString());
                   if(employee.getMeetingDate()!=null){
                       meeting_date.setText("Meeting Date: "+employee.getMeetingDate().toString());
                   }else{
                       meeting_date.setText("Meeting Date: ------");
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
        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener((view)->{
            finish();
        });
        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener((view)->{
            employeeRepository.empleado(ident).enqueue(new Callback<Employee>() {
                @Override
                public void onResponse(Call<Employee> call, Response<Employee> response) {
                    if (response.isSuccessful()) {

                        Employee employee=response.body();
                        Gson gson = new Gson();
                        String Json1 = gson.toJson(employee);
                        texto= Json1;
                        guardarTexto();

                    }else{
                        Log.e("msg","error");
                    }

                }

                @Override
                public void onFailure(Call<Employee> call, Throwable t) {
                    t.printStackTrace();

                }
            });
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
        intent1.putExtra(Intent.EXTRA_TITLE, "MiInformación.txt");

        activityForResultLauncher.launch(intent1);
    }


}