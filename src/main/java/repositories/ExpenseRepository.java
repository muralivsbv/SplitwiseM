package repositories;

import models.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseRepository {
    private List<Expense> expenseList;

    public ExpenseRepository() {
        expenseList = new ArrayList<>();
    }
}
