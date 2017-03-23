package com.example.android.project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClearActivity extends AppCompatActivity {

    private ShoppingDataSource dataSource;
    private ShoppingItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);

        ArrayList<ShoppingItem> items = new ArrayList<>();
        adapter = new ShoppingItemAdapter(this, items);

        ListView lvItems = (ListView) findViewById(R.id.lvClear);
        lvItems.setAdapter(adapter);

        dataSource = new ShoppingDataSource(this);
        dataSource.open();
        items = dataSource.getAll();
        dataSource.close();


        // Sorting
        Collections.sort(items, new Comparator<ShoppingItem>() {
            @Override
            public int compare(ShoppingItem o1, ShoppingItem o2) {
                return o1.getIsPurchased() > o2.getIsPurchased() ? 1 : -1 ;
            }
        });

        for (ShoppingItem item : items) {
            adapter.add(item);
        }

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShoppingItem item = (ShoppingItem) parent.getItemAtPosition(position);
                dataSource = new ShoppingDataSource(ClearActivity.this);
                dataSource.open();
                dataSource.delete(item);

                adapter.remove(adapter.getItem(position));
                adapter.notifyDataSetChanged();


                String msg = getResources().getString(R.string.has_been_removed_from_the_list);
                Toast.makeText(ClearActivity.this, item.getName() +
                        " " + msg, Toast.LENGTH_SHORT).show();

                ArrayList<ShoppingItem> items;
                items = dataSource.getAll();
                dataSource.close();
                if (items.size() == 0) {
                    Intent intent = new Intent(ClearActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void clearAll(View view) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        dataSource = new ShoppingDataSource(ClearActivity.this);
                        dataSource.open();
                        dataSource.deleteAll();
                        dataSource.close();
                        adapter.clear();
                        adapter.notifyDataSetChanged();

                        Toast.makeText(ClearActivity.this, R.string.The_list_has_been_cleared,
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ClearActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.Are_you_sure_to_clear_all)
                .setPositiveButton(R.string.Clear_confirmation, dialogClickListener)
                .setNegativeButton(R.string.Cancel_confirmation, dialogClickListener).show();
    }
}
