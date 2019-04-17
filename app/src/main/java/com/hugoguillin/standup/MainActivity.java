package com.hugoguillin.standup;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
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

        manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        ToggleButton alarma = findViewById(R.id.alarmToogle);

        Intent intent = new Intent(this, AlarmReceiver.class);

        boolean alarmUp = (PendingIntent.getBroadcast(this, NOTIFICATION_ID,
                intent, PendingIntent.FLAG_NO_CREATE) != null);

        alarma.setChecked(alarmUp);

        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        final AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        alarma.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String toast;

                if(isChecked){
                    long intervalo = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
                    long lanzamiento = SystemClock.elapsedRealtime()+intervalo;
                    if (alarmManager != null) {
                        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                lanzamiento, intervalo, pendingIntent);
                    }
                    toast = getString(R.string.activada);

                }else{
                    if (alarmManager != null){
                        alarmManager.cancel(pendingIntent);
                    }
                    manager.cancelAll();
                    toast = getString(R.string.desactivada);
                }
                Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_LONG).show();
            }
        });

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


}
