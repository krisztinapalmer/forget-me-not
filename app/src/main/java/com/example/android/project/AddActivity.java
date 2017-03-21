package com.example.android.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    private EditText etItemName, etQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etItemName = (EditText) findViewById(R.id.etItemName);
        etQuantity = (EditText) findViewById(R.id.etQuantity);
    }

    public void addItem(View view) {
        ShoppingDataSource dataSource = new ShoppingDataSource(this);

        String name = etItemName.getText().toString().trim();
        String stringQuantity = etQuantity.getText().toString().trim();

        if (name.equals("")) {
            Toast.makeText(this, "Please add an item name!", Toast.LENGTH_SHORT).show();
        } else {
            int quantity = 1;

            if (!stringQuantity.equals("") && TextUtils.isDigitsOnly(stringQuantity) ) {
                quantity = Integer.parseInt(stringQuantity);
            }

            dataSource.open();
            dataSource.insert(name, quantity);
            dataSource.close();

            Toast.makeText(this, name + " is successfully added to the list.",
                    Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
