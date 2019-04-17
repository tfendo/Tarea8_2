package com.hugoguillin.standup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

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
                    toast = getString(R.string.activada);
                }else{
                    toast = getString(R.string.desactivada);
                }
                Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_LONG).show();
            }
        });
    }
}
