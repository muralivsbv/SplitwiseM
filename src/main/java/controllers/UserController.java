package controllers;

import dtos.Transaction;
import services.UserService;

import java.util.List;

public class UserController {
    // in user controller we need to create the instance of user service and pass on the given data
    // to it for processing , it will be done by invoking the respective function in user service

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public List<Transaction> settleUser(String userName, String groupName){
           return userService.settleUser(userName,groupName);
    }
}
