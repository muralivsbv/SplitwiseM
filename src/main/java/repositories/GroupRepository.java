package repositories;

import models.Expense;
import models.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupRepository {
    private List<Group> groupList;

    public GroupRepository() {
        groupList = new ArrayList<>();
    }

    public void addGroup(Group group){
        groupList.add(group);
    }

    public List<Expense> findExpensesByGroup(String groupName) {
         for(Group group : groupList){
             if(group.getName().equalsIgnoreCase(groupName)){
                 return group.getExpenses();
             }
         }
         return new ArrayList<>();
    }
}
