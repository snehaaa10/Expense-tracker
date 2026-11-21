package com.expensetracker.model;

public class Expense extends Transaction {
    private final String description;
    private final double amount;

    public Expense(String id, String category, String description, double amount, String month) {
        super(id, category, month);
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() { return description; }
    public double getAmount() { return amount; }

    @Override
    public String getType() { return "Expense"; }

    @Override
    public String getDetails() {
        return super.getDetails() + String.format(" | %-20s | Rs. %.2f", description, amount);
    }

    @Override
    public String toString() { return getDetails(); }
}
