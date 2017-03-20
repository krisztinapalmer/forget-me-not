package com.example.android.project;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private boolean sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = getSharedPreferences("Forget-Me-Not", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        sound = sharedPreferences.getBoolean("Sound", true);

        CheckBox checkBoxSound = (CheckBox) findViewById(R.id.checkBoxSound);
        if (sound) {
            checkBoxSound.setChecked(true);
        } else {
            checkBoxSound.setChecked(false);
        }

        checkBoxSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // If sound is true make it false, if false make it true
                sound = !sound;
                editor.putBoolean("Sound", sound);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Save the settings
        editor.commit();
    }
}
