package com.expensetracker.util;

import com.expensetracker.model.Expense;

import java.util.List;

/**
 * DSA part of the project: selection sort written by hand.
 *
 * It arranges expenses from the highest amount to the lowest,
 * so the top spending records can be displayed first.
 * Time complexity: O(n^2), appropriate for console-based datasets.
 */
public class ExpenseSorter {

    public static void sortByAmountDescending(List<Expense> list) {
        int n = list.size();

        for (int i = 0; i < n - 1; i++) {
            // Find the expense with highest amount in unsorted portion
            int maxIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (list.get(j).getAmount() > list.get(maxIndex).getAmount()) {
                    maxIndex = j;
                }
            }

            // Swap into position i
            if (maxIndex != i) {
                Expense temp = list.get(i);
                list.set(i, list.get(maxIndex));
                list.set(maxIndex, temp);
            }
        }
    }
}
