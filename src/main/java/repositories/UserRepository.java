package repositories;

import models.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private List<User> userList;

    public UserRepository() {
        userList = new ArrayList<>();
    }

    public void addUser         (User user) {
        userList.add(user);
    }
}
