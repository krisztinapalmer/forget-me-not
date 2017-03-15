package com.example.android.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.clearListBtn:
                intent = new Intent(this, ClearActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data.hasExtra("name")) {
            String name = data.getStringExtra("name");
            Toast.makeText(this, name + " is successfully added to database!",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
