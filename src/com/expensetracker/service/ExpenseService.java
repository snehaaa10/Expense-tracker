package com.expensetracker.service;

import com.expensetracker.exception.ExpenseNotFoundException;
import com.expensetracker.exception.InvalidExpenseException;
import com.expensetracker.model.Expense;
import java.util.ArrayList;
import java.util.List;

public class ExpenseService {
    private final List<Expense> expenses = new ArrayList<>();
    private int nextId = 101;

    public Expense addExpense(String category, String description, double amount, String month)
            throws InvalidExpenseException {
        if (category == null || category.trim().isEmpty()) {
            throw new InvalidExpenseException("Category cannot be empty.");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new InvalidExpenseException("Description cannot be empty.");
        }
        if (amount <= 0) {
            throw new InvalidExpenseException("Amount must be greater than zero. Provided: " + amount);
        }
        if (month == null || month.trim().isEmpty()) {
            throw new InvalidExpenseException("Month cannot be empty.");
        }
        String id = "E" + nextId++;
        Expense expense = new Expense(id, category.trim(), description.trim(), amount, month.trim());
        expenses.add(expense);
        return expense;
    }

    public Expense deleteExpense(String id) throws ExpenseNotFoundException {
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getId().equalsIgnoreCase(id.trim())) {
                return expenses.remove(i);
            }
        }
        throw new ExpenseNotFoundException("No expense found with ID " + id + ".");
    }

    public List<Expense> getByMonth(String month) {
        List<Expense> result = new ArrayList<>();
        for (Expense e : expenses) {
            if (e.getMonth().equalsIgnoreCase(month.trim())) {
                result.add(e);
            }
        }
        return result;
    }

    public List<Expense> getByCategory(String category) {
        List<Expense> result = new ArrayList<>();
        for (Expense e : expenses) {
            if (e.getCategory().equalsIgnoreCase(category.trim())) {
                result.add(e);
            }
        }
        return result;
    }

    public double calculateTotal(List<Expense> list) {
        double total = 0;
        for (Expense e : list) {
            total += e.getAmount();
        }
        return total;
    }

    public List<Expense> getAll() {
        return new ArrayList<>(expenses);
    }

    public int getExpenseCount() {
        return expenses.size();
    }
}
