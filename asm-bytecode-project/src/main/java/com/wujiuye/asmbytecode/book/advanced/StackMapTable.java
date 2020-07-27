package com.wujiuye.asmbytecode.book.advanced;

public class StackMapTable {

    public void showUserName(int... userIds) {
        // basic block 1 start
        UserService userService = new UserService();
        for (int i = 0;// basic block 1 end
             i < userIds.length; i++) {
            // basic block 2 start
            int userId = userIds[i];
            String userName = userService.getUserName(userId);
            // basic block 2 end
        }
    }

    public void showUserName2(int userId) {
        UserService userService = new UserService();
        if (userId > 0) {
            String userName = userService.getUserName(userId);
        } else {
            String userName = "admin";
        }
    }

    public static void main(String[] args) {
        new StackMapTable().showUserName2(1);
    }

}
