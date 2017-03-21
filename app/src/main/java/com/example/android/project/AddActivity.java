package com.example.android.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    public static final int INSERT_ERROR = -1;

    private EditText etItemName, etQuantity;
    private String name;
    private long recordId = INSERT_ERROR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etItemName = (EditText) findViewById(R.id.etItemName);
        etQuantity = (EditText) findViewById(R.id.etQuantity);
    }

    public void addItem(View view) {
        ShoppingDataSource dataSource = new ShoppingDataSource(this);

        name = etItemName.getText().toString().trim();
        String stringQuantity = etQuantity.getText().toString().trim();

        if (name.equals("")) {
            Toast.makeText(this, "Please add an item name!", Toast.LENGTH_SHORT).show();
        } else if (!TextUtils.isDigitsOnly(stringQuantity) || stringQuantity.equals("")) {
            Toast.makeText(this, "Please add a number to quantity!", Toast.LENGTH_SHORT).show();
        } else {
            int quantity = Integer.parseInt(stringQuantity);
            dataSource.open();
            this.recordId = dataSource.insert(name, quantity);
            dataSource.close();

            Toast.makeText(this, name + " is successfully added to the list.",
                    Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
