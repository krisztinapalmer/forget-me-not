package com.example.android.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ClearActivity extends AppCompatActivity {

    ShoppingDataSource dataSource;
    ShoppingItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);

        List<ShoppingItem> items = new ArrayList<>();
        adapter = new ShoppingItemAdapter(this, items);

        ListView lvItems = (ListView) findViewById(R.id.lvClear);
        lvItems.setAdapter(adapter);

        dataSource = new ShoppingDataSource(this);
        dataSource.open();
        items = dataSource.getAll();
        dataSource.close();

        if (items.size() == 0) {
            Toast.makeText(this, "No item stored in database!", Toast.LENGTH_SHORT).show();
        } else {
            for (ShoppingItem item : items) {
                adapter.add(item);
            }
        }

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShoppingItem item = (ShoppingItem) parent.getItemAtPosition(position);
                dataSource = new ShoppingDataSource(ClearActivity.this);
                dataSource.open();
                dataSource.delete(item);
                dataSource.close();
                adapter.remove(adapter.getItem(position));
                adapter.notifyDataSetChanged();

                Toast.makeText(ClearActivity.this, item.getName() +
                        " has been removed from the list!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void clearAll(View view) {
        dataSource = new ShoppingDataSource(this);
        dataSource.open();
        dataSource.deleteAll();
        dataSource.close();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
