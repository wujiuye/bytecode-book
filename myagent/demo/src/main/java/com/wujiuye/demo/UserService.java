package com.wujiuye.demo;

import java.util.HashMap;
import java.util.Map;


public class UserService {

    public Map<String, Object> queryUser(String username, Integer age) {
        Map<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("age", age);
        return result;
    }

}
