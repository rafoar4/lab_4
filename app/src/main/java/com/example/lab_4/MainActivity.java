package com.example.lab_4;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button ir_trabajador,ir_tutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ir_trabajador=findViewById(R.id.ir_trabajador);
        ir_tutor=findViewById(R.id.ir_Tutor);

        createNotificationChannelTutor();
        createNotificationChannelTrabajor();

        ir_trabajador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarNotificacion2();




                startActivity(new Intent(MainActivity.this,Tutor.class));
            }
        });

        ir_tutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarNotificacion1();
                startActivity(new Intent(MainActivity.this,Trabajador.class));

            }
        });
    }

    String channelId1 = "channelDefaultTutor";

    private void createNotificationChannelTutor() {
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

    String channelId2 = "channelDefaultTrabajador";

    private void createNotificationChannelTrabajor() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId2,
                    "Canal notificaciones default trabajador",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Canal para notificaciones con prioridad default");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            askPermission();

        }

    }

    public void lanzarNotificacion2() {
        Intent intent = new Intent(this, Tutor.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId2)
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle(" Está entrando en modo Empleado")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, builder.build());
        }
    }
    public void lanzarNotificacion1() {
        Intent intent = new Intent(this, Trabajador.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId1)
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle("Está entrando en modo Tutor")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, builder.build());
        }
    }



    private void askPermission() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) ==
                        PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{POST_NOTIFICATIONS},
                    101);
        }
    }

}