package com.expensetracker;

import com.expensetracker.exception.BudgetExceededException;
import com.expensetracker.exception.BudgetNotFoundException;
import com.expensetracker.exception.ExpenseNotFoundException;
import com.expensetracker.exception.InvalidExpenseException;
import com.expensetracker.model.Budget;
import com.expensetracker.model.Expense;
import com.expensetracker.service.BudgetService;
import com.expensetracker.service.ExpenseService;
import com.expensetracker.service.SummaryService;
import com.expensetracker.util.ExpenseSorter;

import java.util.List;
import java.util.Scanner;

/**
 * Entry point. Shows the console menu and delegates actions to services.
 * Main only handles user input and output formatting.
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final ExpenseService expenseService = new ExpenseService();
    private static final BudgetService budgetService = new BudgetService();
    private static final SummaryService summaryService = new SummaryService();

    public static void main(String[] args) {
        System.out.println("=== Expense Tracker & Budget Planner ===");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    viewAllExpenses();
                    break;
                case 3:
                    deleteExpense();
                    break;
                case 4:
                    setBudget();
                    break;
                case 5:
                    viewAllBudgets();
                    break;
                case 6:
                    checkBudget();
                    break;
                case 7:
                    showMonthlySummary();
                    break;
                case 8:
                    summaryService.printSpendingTrend(expenseService);
                    break;
                case 9:
                    summaryService.printCategorySummary(expenseService);
                    break;
                case 10:
                    showTopExpenses();
                    break;
                case 0:
                    running = false;
                    System.out.println("Thank you. Exiting.");
                    break;
                default:
                    System.out.println("Please enter a number from 0 to 10.");
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1.  Add expense");
        System.out.println("2.  View all expenses");
        System.out.println("3.  Delete expense");
        System.out.println("4.  Set monthly budget");
        System.out.println("5.  View all budgets");
        System.out.println("6.  Check budget vs spending");
        System.out.println("7.  Monthly category summary");
        System.out.println("8.  Spending trend across months");
        System.out.println("9.  Category-wise all-time total");
        System.out.println("10. Top 5 highest expenses");
        System.out.println("0.  Exit");
    }

    private static void addExpense() {
        String category = readText("Category (Food/Transport/Bills/Shopping/Other): ");
        String description = readText("Description: ");
        double amount = readDouble("Amount (Rs.): ");
        String month = readText("Month (YYYY-MM, e.g. 2024-01): ");

        try {
            Expense e = expenseService.addExpense(category, description, amount, month);
            System.out.println("Expense added successfully: " + e.getDetails());
        } catch (InvalidExpenseException e) {
            System.out.println("Could not add expense: " + e.getMessage());
        }
    }

    private static void viewAllExpenses() {
        List<Expense> list = expenseService.getAll();
        if (list.isEmpty()) {
            System.out.println("No expenses recorded yet.");
            return;
        }
        System.out.println("--- All Expenses ---");
        for (Expense e : list) {
            System.out.println(e.getDetails());
        }
    }

    private static void deleteExpense() {
        viewAllExpenses();
        if (expenseService.getExpenseCount() == 0) {
            return;
        }
        String id = readText("Enter ID of expense to delete (e.g. E101): ");
        try {
            Expense removed = expenseService.deleteExpense(id);
            System.out.println("Expense deleted successfully: " + removed.getDetails());
        } catch (ExpenseNotFoundException e) {
            System.out.println("Delete failed: " + e.getMessage());
        }
    }

    private static void setBudget() {
        String category = readText("Category: ");
        String month = readText("Month (YYYY-MM): ");
        double limit = readDouble("Monthly Budget Limit (Rs.): ");

        try {
            Budget b = budgetService.setBudget(category, month, limit);
            System.out.println("Budget set successfully: " + b.getDetails());
        } catch (IllegalArgumentException e) {
            System.out.println("Could not set budget: " + e.getMessage());
        }
    }

    private static void viewAllBudgets() {
        List<Budget> list = budgetService.getAllBudgets();
        if (list.isEmpty()) {
            System.out.println("No budgets set yet.");
            return;
        }
        System.out.println("--- All Budgets ---");
        for (Budget b : list) {
            System.out.println(b.getDetails());
        }
    }

    private static void checkBudget() {
        String category = readText("Category: ");
        String month = readText("Month (YYYY-MM): ");

        try {
            double remaining = budgetService.checkBudget(category, month, expenseService);
            Budget b = budgetService.findBudget(category, month);
            System.out.println("--- Budget Status ---");
            System.out.printf("  Category: %s | Month: %s%n", category, month);
            System.out.printf("  Budget Limit: Rs. %.2f%n", b.getLimit());
            System.out.printf("  Remaining   : Rs. %.2f [Within Budget]%n", remaining);
        } catch (BudgetNotFoundException e) {
            System.out.println("Budget check failed: " + e.getMessage());
        } catch (BudgetExceededException e) {
            System.out.println("WARNING: " + e.getMessage());
        }
    }

    private static void showMonthlySummary() {
        String month = readText("Month (YYYY-MM): ");
        summaryService.printMonthlySummary(month, expenseService);
    }

    private static void showTopExpenses() {
        List<Expense> all = expenseService.getAll();
        if (all.isEmpty()) {
            System.out.println("No expenses recorded yet.");
            return;
        }
        ExpenseSorter.sortByAmountDescending(all);
        System.out.println("--- Top Expenses (Highest Spending First) ---");
        int count = Math.min(5, all.size());
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + ". " + all.get(i).getDetails());
        }
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("That is not a valid integer, please try again.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("That is not a valid number, please try again.");
            }
        }
    }
}
