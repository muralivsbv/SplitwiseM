package App;

import controllers.UserController;
import dtos.Transaction;
import models.*;
import repositories.ExpenseRepository;
import repositories.GroupRepository;
import repositories.UserExpenseRepository;
import repositories.UserRepository;
import services.UserService;
import strategies.HeapSettleUpStrategy;

import java.util.ArrayList;
import java.util.List;

public class Runner {

    public static void main(String[] args) {

        // need these repositories to store the data
        UserRepository userRepository = new UserRepository();
        GroupRepository groupRepository = new GroupRepository();
        ExpenseRepository expenseRepository = new ExpenseRepository();
        UserExpenseRepository userExpenseRepository = new UserExpenseRepository();

        // setting data for testing our app -> follow below steps to set it in organised way
        // make list of users ,
        // create a group with users list
        // and create expenses for that group .. in this case we are creating only one expense
        // define user expenses for each expense

        User murali = new User("Murali", "9840166389", "1234");
        User raghu = new User("Raghu", "1234", "5678");
        User abhi = new User("Abhi", "3456", "8901");
        User sanjay = new User("Sanjay", "12345", "123");
        User deepak = new User("Deepak", "5678", "0123");


        // create a group and make these users as a list and pass it to group
        ArrayList<User>  goaGuys = new ArrayList<>();
        goaGuys.add(murali);
        goaGuys.add(raghu);
        goaGuys.add(abhi);
        goaGuys.add(sanjay);
        goaGuys.add(deepak);

        //crating group
        Group goaGroup = new Group("goa-Trip");


        //Creating expenses for the group
        Expense dinner_expense = new Expense("Dinner",6000, ExpenseType.NORMAL);
        Expense room_expense = new Expense("room",9000, ExpenseType.NORMAL);

        // here only one expense but still if we have mutiple expenses then we need to make them as list and pass to the group
        ArrayList<Expense> goaExpenses = new ArrayList<>();
        goaExpenses.add(dinner_expense);
        goaExpenses.add(room_expense);

        //setting userlist and expenselist to the group
        goaGroup.setUsers(goaGuys);
        goaGroup.setExpenses(goaExpenses);

        //now spliiting this expenses into userExpense ..
        // share of each user(Had_to_pay) for the paritcular expense and also actual paid users (who_paid) for this expense

        UserExpense uE1 = new UserExpense(abhi,   dinner_expense, 1000, UserExpenseType.HAD_TO_PAY);
        UserExpense uE2 = new UserExpense(murali, dinner_expense, 1000, UserExpenseType.HAD_TO_PAY);
        UserExpense uE3 = new UserExpense(raghu,  dinner_expense, 1000, UserExpenseType.HAD_TO_PAY);
        UserExpense uE4 = new UserExpense(deepak,  dinner_expense, 1000, UserExpenseType.HAD_TO_PAY);
        UserExpense uE5 = new UserExpense(sanjay, dinner_expense, 2000, UserExpenseType.HAD_TO_PAY);

        UserExpense uE6 = new UserExpense(abhi, dinner_expense, 6000, UserExpenseType.PAID_BY);

        UserExpense uE7 = new UserExpense(abhi,   room_expense, 3000, UserExpenseType.HAD_TO_PAY);
        UserExpense uE8 = new UserExpense(murali, room_expense, 3000, UserExpenseType.HAD_TO_PAY);
        UserExpense uE9 = new UserExpense(raghu,  room_expense, 3000, UserExpenseType.HAD_TO_PAY);

        UserExpense uE10 = new UserExpense(abhi, room_expense, 4000, UserExpenseType.PAID_BY);
        UserExpense uE11 = new UserExpense(deepak, room_expense, 5000, UserExpenseType.PAID_BY);



        // We have created users,group,expenses,userexpenses and all now as we dont have the all controllers
        // to add data to respective repositories
        // so we manually add these data to repositories so we need to create the instances of those repositories
        // add all the prepared data

        userRepository.addUser(abhi);
        userRepository.addUser(murali);
        userRepository.addUser(sanjay);
        userRepository.addUser(raghu);
        userRepository.addUser(deepak);

        groupRepository.addGroup(goaGroup);

        expenseRepository.addExpense(dinner_expense);

        userExpenseRepository.addUserExpense(uE1);
        userExpenseRepository.addUserExpense(uE2);
        userExpenseRepository.addUserExpense(uE3);
        userExpenseRepository.addUserExpense(uE4);
        userExpenseRepository.addUserExpense(uE5);
        userExpenseRepository.addUserExpense(uE6);

        userExpenseRepository.addUserExpense(uE7);
        userExpenseRepository.addUserExpense(uE8);
        userExpenseRepository.addUserExpense(uE9);
        userExpenseRepository.addUserExpense(uE10);
        userExpenseRepository.addUserExpense(uE11);


        // after preparing all the required repositiores we need to create an instance of
        // UserController ->Needs an instance of UserService -> needs the instances of repositories/Heap Strategy

        UserService userService = new UserService(groupRepository,userExpenseRepository,new HeapSettleUpStrategy());
        UserController userController = new UserController(userService);

//        above thing can also be done in single instanctiation like below
//         UserController userController = new UserController(new UserService(
//                                       groupRepository,userExpenseRepository,new HeapSettleUpStrategy()
//                                  ));

      //  List<Transaction> transactionList = userController.settleUser("murali", "goa-Trip");
       List<Transaction> transactionList = userController.settleGroup("goa-Trip");
        // Transactiopn should be there where <input given in above calls> is involved
        System.out.println(transactionList);

//        Expected output for above input

        // raghu, deepak, murali -> 1k to abhi
        // sanjay -> 2k to murali
    }
}
