package repositories;

import models.UserExpense;

import java.util.ArrayList;
import java.util.List;

public class UserExpenseRepository {
    private List<UserExpense> userExpenseList;



    public UserExpenseRepository() {
        userExpenseList = new ArrayList<>();
    }

    public void addUserExpense(UserExpense userExpense){
        userExpenseList.add(userExpense);
    }

    // with expense description (eg : e1 in otes) taking as input and getting that particular expense related all user
    // expenses and adding them to the list and returning
    public List<UserExpense> findUserExpensesByExpense(String description) {
        List<UserExpense> userExpenses = new ArrayList<>();
        for(UserExpense userExpense: userExpenseList){
            if(description.equalsIgnoreCase(userExpense.getExpense().getDescription())){
                userExpenses.add(userExpense);
            }
        }
        return userExpenses;
    }
}
