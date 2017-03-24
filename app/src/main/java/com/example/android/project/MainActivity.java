package com.example.android.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private boolean sound;
    private SharedPreferences sharedPreferences;

    int beepId = -1;
    SoundPool soundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sound
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }

        try {
            AssetManager assetManager = this.getAssets();
            AssetFileDescriptor descriptor;

            descriptor = assetManager.openFd("scanner_beep.mp3");
            beepId = soundPool.load(descriptor, 0);
        } catch (IOException e) {
            Log.e(TAG, "Failed to load sound files");
        }


        // Disables buttons if there is no list
        boolean hasList;
        Button viewButton, clearButton;
        ArrayList<ShoppingItem> items;
        ShoppingDataSource dataSource = new ShoppingDataSource(this);

        dataSource.open();
        items = dataSource.getAll();
        dataSource.close();

        if (items.size() == 0) {
            hasList = false;
        } else {
            hasList = true;
        }

        viewButton = (Button) findViewById(R.id.viewListBtn);
        clearButton = (Button) findViewById(R.id.clearListBtn);

        viewButton.setEnabled(hasList);
        clearButton.setEnabled(hasList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void handleBtnClick(View view) {
        if (sound) {
            soundPool.play(beepId, 1, 1, 0, 0, 1);
        }

        Intent intent;
        switch (view.getId()) {
            case R.id.viewListBtn:
                intent = new Intent(this, ViewActivity.class);
                startActivity(intent);
                break;
            case R.id.addItemBtn:
                intent = new Intent(this, AddActivity.class);
                startActivity(intent);
                break;
            case R.id.clearListBtn:
                intent = new Intent(this, ClearActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        sharedPreferences = getSharedPreferences("Forget-Me-Not", MODE_PRIVATE);
        sound = sharedPreferences.getBoolean("Sound", true);
    }
}
