package com.example.android.project;

public class ShoppingItem {
    private long id;
    private String name;
    private int quantity;
    private int isPurchased;

    public static final int NOT_PURCHASED = 0;
    public static final int PURCHASED = 1;

    public ShoppingItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
        this.isPurchased = NOT_PURCHASED;
    }

    public ShoppingItem(String name, int quantity, int isPurchased) {
        this(name, quantity);
        this.isPurchased = isPurchased;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIsPurchased() {
        return this.isPurchased;
    }

    public void setIsPurchased(int isPurchased) {
        this.isPurchased = isPurchased;
    }
}
