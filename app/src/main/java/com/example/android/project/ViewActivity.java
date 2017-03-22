package com.example.android.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewActivity extends AppCompatActivity {

    private ShoppingDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        ArrayList<ShoppingItem> items = new ArrayList<>();
        ShoppingItemAdapter adapter = new ShoppingItemAdapter(this, items);

        ListView lvItems = (ListView) findViewById(R.id.lvViewList);
        lvItems.setAdapter(adapter);

        dataSource = new ShoppingDataSource(this);
        dataSource.open();
        items = dataSource.getAll();
        dataSource.close();

        if (items.size() == 0) {
            Toast.makeText(this, "No item stored in the list.", Toast.LENGTH_SHORT).show();
        } else {

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
        }

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShoppingItem item = (ShoppingItem) parent.getItemAtPosition(position);
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.isPurchasedCheckBox);
                checkBox.setChecked(!checkBox.isChecked());
                item.setIsPurchased(checkBox.isChecked() ? ShoppingItem.PURCHASED :
                        ShoppingItem.NOT_PURCHASED);
                dataSource.open();
                dataSource.updateIsPurchased(item);
                dataSource.close();
            }
        });
    }
}
