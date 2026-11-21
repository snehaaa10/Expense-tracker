package com.expensetracker.model;

public abstract class Transaction {
    private final String id;
    private final String category;
    private final String month;

    public Transaction(String id, String category, String month) {
        this.id = id;
        this.category = category;
        this.month = month;
    }

    public String getId() { return id; }
    public String getCategory() { return category; }
    public String getMonth() { return month; }

    public abstract String getType();

    public String getDetails() {
        return String.format("[%s] ID: %s | %-12s", month, id, category);
    }
}
