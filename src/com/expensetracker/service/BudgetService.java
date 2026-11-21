package com.expensetracker.service;

import com.expensetracker.exception.BudgetExceededException;
import com.expensetracker.exception.BudgetNotFoundException;
import com.expensetracker.model.Budget;
import com.expensetracker.model.Expense;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BudgetService {
    private final Map<String, Budget> budgets = new HashMap<>();
    private int nextId = 201;

    public Budget setBudget(String category, String month, double limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("Budget limit must be greater than zero. Provided: " + limit);
        }
        String key = makeKey(category, month);
        String id = "B" + nextId++;
        Budget budget = new Budget(id, category.trim(), month.trim(), limit);
        budgets.put(key, budget);
        return budget;
    }

    public double checkBudget(String category, String month, ExpenseService es)
            throws BudgetNotFoundException, BudgetExceededException {
        Budget budget = findBudget(category, month);
        double spent = 0;
        for (Expense e : es.getByMonth(month)) {
            if (e.getCategory().equalsIgnoreCase(category.trim())) {
                spent += e.getAmount();
            }
        }
        double remaining = budget.getLimit() - spent;
        if (remaining < 0) {
            throw new BudgetExceededException(String.format(
                    "Budget exceeded for %s (%s)! Spent: Rs. %.2f, Limit: Rs. %.2f (Over by Rs. %.2f)",
                    category, month, spent, budget.getLimit(), Math.abs(remaining)));
        }
        return remaining;
    }

    public Budget findBudget(String category, String month) throws BudgetNotFoundException {
        String key = makeKey(category, month);
        Budget budget = budgets.get(key);
        if (budget == null) {
            throw new BudgetNotFoundException("No budget set for " + category + " in " + month + ".");
        }
        return budget;
    }

    public List<Budget> getAllBudgets() {
        return new ArrayList<>(budgets.values());
    }

    public int getBudgetCount() {
        return budgets.size();
    }

    private String makeKey(String category, String month) {
        return category.trim().toLowerCase() + ":" + month.trim().toLowerCase();
    }
}
