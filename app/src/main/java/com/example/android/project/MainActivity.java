package com.example.android.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
