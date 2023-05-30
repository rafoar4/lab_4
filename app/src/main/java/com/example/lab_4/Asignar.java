package com.example.lab_4;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Asignar extends AppCompatActivity {

    EditText codigo_e,id_e;
    Button button;
    String texto1=null;
    String texto2=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignar);

        createNotificationChannel();


        EmployeeRepository employeeRepository = new Retrofit.Builder()
                .baseUrl("http://192.168.9.106:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(EmployeeRepository.class);


        codigo_e=findViewById(R.id.codigo_empleado);
        id_e=findViewById(R.id.id_empleado);

        button=findViewById(R.id.button5);

        button.setOnClickListener(v -> {
            if(codigo_e!=null&&id_e!=null){

                String codigoE=codigo_e.getText().toString();
                String idE=id_e.getText().toString();
                employeeRepository.empleado(Integer.parseInt(codigoE)).enqueue(new Callback<Employee>() {
                    @Override
                    public void onResponse(Call<Employee> call, Response<Employee> response) {

                        Employee employee1=response.body();
                        Gson gson = new Gson();
                        String Json1 = gson.toJson(employee1);
                        texto1= Json1;
                        employeeRepository.empleado(Integer.parseInt(idE)).enqueue(new Callback<Employee>() {
                            @Override
                            public void onResponse(Call<Employee> call, Response<Employee> response) {
                                Employee employee2=response.body();
                                Gson gson = new Gson();
                                String Json2 = gson.toJson(employee2);
                                texto2= Json2;
                                if (employee1.getId()==employee2.getManagerId()){
                                    if(employee1.getMeeting()==1){
                                        lanzarNotificacion1();


                                    }else {
                                        lanzarNotificacion2();
                                    }
                                }else {
                                    Toast.makeText(Asignar.this,"No es manager del empleado",Toast.LENGTH_LONG).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<Employee> call, Throwable t) {
                                t.printStackTrace();

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Employee> call, Throwable t) {
                        t.printStackTrace();

                    }
                });
            }

        });


    }

    String channelId1 = "channelDefaultAsignar";

    public void lanzarNotificacion1() {
        Intent intent = new Intent(this, Asignar.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId1)
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle("El trabajador ya tiene una cita \n" +
                        "asignada. Elija otro trabajador")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, builder.build());
        }
    }
    public void lanzarNotificacion2() {
        Intent intent = new Intent(this, Asignar.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId1)
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle("Asignación del trabajador correcta")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, builder.build());
        }
    }
    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId1,
                    "Canal notificaciones default tutor",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Canal para notificaciones con prioridad default");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            askPermission();

        }

    }
    private void askPermission() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) ==
                        PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(Asignar.this,
                    new String[]{POST_NOTIFICATIONS},
                    101);
        }
    }
}