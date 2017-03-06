package com.example.android.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class ShoppingItemAdapter extends ArrayAdapter<ShoppingItem> {

    public ShoppingItemAdapter(Context context, List<ShoppingItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ShoppingItem item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_layout, parent, false);
        }

        CheckBox isPurchased = (CheckBox) convertView.findViewById(R.id.isPurchasedCheckBox);
        if (item.getIsPurchased() == ShoppingItem.PURCHASED) {
            isPurchased.setChecked(true);
        } else {
            isPurchased.setChecked(false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        tvName.setText(item.getName());

        TextView tvQuantity = (TextView) convertView.findViewById(R.id.tvQuantity);
        String quantity = Integer.toString(item.getQuantity());
        tvQuantity.setText(quantity);

        return convertView;
    }
}
