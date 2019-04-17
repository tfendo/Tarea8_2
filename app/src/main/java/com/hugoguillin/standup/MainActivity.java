package com.hugoguillin.standup;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    private NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToggleButton alarma = findViewById(R.id.alarmToogle);
        alarma.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String toast;
                if(isChecked){
                    deliverNotification(getApplicationContext());
                    toast = getString(R.string.activada);
                }else{
                    manager.cancelAll();
                    toast = getString(R.string.desactivada);
                }
                Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_LONG).show();
            }
        });

        manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel canal = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Stand Up notification", NotificationManager.IMPORTANCE_HIGH);
            canal.enableLights(true);
            canal.setLightColor(Color.RED);
            canal.enableVibration(true);
            canal.setDescription(getString(R.string.descripcion_canal));
            manager.createNotificationChannel(canal);
        }
    }

    private void deliverNotification(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stand_up)
                .setContentTitle(getString(R.string.titulo_notificacion))
                .setContentText(getString(R.string.texto_notificacion))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        manager.notify(NOTIFICATION_ID, builder.build());
    }
}
