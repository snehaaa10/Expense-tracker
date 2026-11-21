# Expense Tracker & Budget Planner

A console based Java project for personal financial tracking and budget planning.
Users record category-wise expenses, define monthly category budgets, check for over-budget limits, and view spending summaries and trends.

## Project structure

```
ExpenseTracker/
├── README.md
├── .gitignore
└── src/
    └── com/
        └── expensetracker/
            ├── Main.java                         menu and user input
            ├── model/
            │   ├── Transaction.java              abstract parent class
            │   ├── Expense.java                  extends Transaction
            │   └── Budget.java                   extends Transaction
            ├── exception/
            │   ├── InvalidExpenseException.java
            │   ├── ExpenseNotFoundException.java
            │   ├── BudgetNotFoundException.java
            │   └── BudgetExceededException.java
            ├── service/
            │   ├── ExpenseService.java           expense management logic
            │   ├── BudgetService.java            budget configuration and checking
            │   └── SummaryService.java           summaries and trend calculations
            └── util/
                └── ExpenseSorter.java            selection sort (DSA)
```

Each package has one job: `model` holds the data classes, `exception` holds our own exceptions, `service` holds the logic, and `util` holds the helper algorithm.

## How to compile and run

Open a terminal in the `ExpenseTracker` folder.

Linux / Mac:

```
javac -d out $(find src -name "*.java")
java -cp out com.expensetracker.Main
```

Windows (Command Prompt):

```
dir /s /b src\*.java > sources.txt
javac -d out @sources.txt
java -cp out com.expensetracker.Main
```

If you use IntelliJ or Eclipse, mark `src` as the source folder and run `Main`.

## Where each requirement is used

| Requirement | Where |
|---|---|
| Encapsulation | Private fields with getters in `Transaction`, `Expense`, `Budget` |
| Inheritance | `Expense` and `Budget` extend `Transaction` |
| Polymorphism | `getType()` and `getDetails()` are overridden in the child classes |
| Abstraction | `Transaction` is an abstract parent class with an abstract method |
| Custom exceptions | Four classes in the `exception` package, thrown in services, caught in `Main` |
| DSA | `ExpenseSorter` - selection sort written by hand, used for the top 5 highest expenses |
| Collections | `HashMap` for budgets in `BudgetService`, `ArrayList` for expenses in `ExpenseService`, `LinkedHashMap` for summaries |

## Sample run

```
=== Expense Tracker & Budget Planner ===

1.  Add expense
2.  View all expenses
3.  Delete expense
4.  Set monthly budget
5.  View all budgets
6.  Check budget vs spending
7.  Monthly category summary
8.  Spending trend across months
9.  Category-wise all-time total
10. Top 5 highest expenses
0.  Exit
Enter your choice: 1
Category (Food/Transport/Bills/Shopping/Other): Food
Description: Grocery Shopping
Amount (Rs.): 1250.00
Month (YYYY-MM, e.g. 2024-01): 2024-01
Expense added successfully: [2024-01] ID: E101 | Food         | Grocery Shopping     | Rs. 1250.00

Enter your choice: 4
Category: Food
Month (YYYY-MM): 2024-01
Monthly Budget Limit (Rs.): 2000.00
Budget set successfully: [2024-01] ID: B201 | Food         | Limit: Rs. 2000.00

Enter your choice: 6
Category: Food
Month (YYYY-MM): 2024-01
--- Budget Status ---
  Category: Food | Month: 2024-01
  Budget Limit: Rs. 2000.00
  Remaining   : Rs. 750.00 [Within Budget]
```
