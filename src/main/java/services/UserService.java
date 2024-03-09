package services;

import dtos.Transaction;
import models.Expense;
import models.ExpenseType;
import models.UserExpense;
import models.UserExpenseType;
import repositories.GroupRepository;
import repositories.UserExpenseRepository;
import strategies.SettleUpStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// checking the commits into git
public class UserService {

    private GroupRepository groupRepository;
    private UserExpenseRepository userExpenseRepository;
    private SettleUpStrategy settleUpStrategy;

    public UserService(GroupRepository groupRepository, UserExpenseRepository userExpenseRepository, SettleUpStrategy settleUpStrategy) {
        this.groupRepository = groupRepository;
        this.userExpenseRepository = userExpenseRepository;
        this.settleUpStrategy = settleUpStrategy;
    }

    public List<Transaction> settleUser(String userName, String groupName) {

       /* 1. get all the group expenses based on the groupName
          2. filter the Normal expenses as we have dummy expense also
          3. for each normal expense get the UserExpenses
          4. Iterate all the userExpenses of an expense (single expense)and find the extra amounts of every user
          5. pass the extraamountsMap to the HeapStrategy to get the list<trasactions>
          6. filter out the list<trasactions> for particular user and return that */

        // the 5 steps are nothing but the group settling , last 6th step is filtering out for particular user

        // create a map and keep track of the extra amount of users in that map for expenses for final settlement
        Map<String,Integer> extraAmountMap = new HashMap<>();
        // 1.get all the group expenses based on the groupName , for this we need group repository , instantiate it

        List<Expense> expenses = groupRepository.findExpensesByGroup(groupName);


        // 2. filter the Normal expenses as we have dummy expense also

           for(Expense expense: expenses){

               if(expense.getExpenseType().equals(ExpenseType.NORMAL)){
                   // 3. for each normal expense get the UserExpenses
                   List<UserExpense> userExpensesList
                           = userExpenseRepository.findUserExpensesByExpense(expense.getDescription());
                   //4. Iterate all the userExpenses of an expense and find the extra amounts of every user

                   for(UserExpense userExpence : userExpensesList){
                       // temp variable to make code looks simple , as a substitute
                         String name = userExpence.getUser().getName();
                         if(!extraAmountMap.containsKey(name)){
                             extraAmountMap.put(name,0);
                         }

                        // Based on the UserExpense type we need to add or subtract the userExpense amount to the
                        // map entry of that particular user and update that entry in map with a new amount again
                        Integer amount = extraAmountMap.get(name);
                        if(userExpence.getUserExpenseType().equals(UserExpenseType.PAID_BY)){
                            amount += userExpence.getAmount();
                        }else if (userExpence.getUserExpenseType().equals(UserExpenseType.HAD_TO_PAY)){
                            amount -= userExpence.getAmount();
                        }

                       extraAmountMap.put(name,amount);

                   }
               }
           }

           // 5. pass the extraamountsMap to the HeapStrategy to get the list<trasactions>
           // now the Map is prepared with details of whom has to owe/receive amounts user wise in the given group
           // now we need to find the transactions using the map , by applying the Heap strategy

          List<Transaction> transactions = settleUpStrategy.settleUpGroup(extraAmountMap);
         // 6. filter out the list<trasactions> for particular user and return that
         // As we have all the trasactions list now we can filter them by user required by checing if that
         // particular user is involved in FROM/TO in the trasaction DTO

         List<Transaction> userTrasactions = new ArrayList<>();
         for(Transaction transaction : transactions){
             if(transaction.getFrom().equalsIgnoreCase(userName) || transaction.getTo().equalsIgnoreCase(userName)){
                 userTrasactions.add(transaction);
             }
         }

         return userTrasactions;


    }
}
