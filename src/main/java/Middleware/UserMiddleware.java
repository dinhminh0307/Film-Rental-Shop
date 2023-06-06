package Middleware;

import Model.User.User;

import java.util.HashMap;
import java.util.Map;

public class UserMiddleware {
    public static boolean isDuplicatedUsername(String username, HashMap<String, User> users){
        for(Map.Entry<String, User> user : users.entrySet()){
            if(username.equals(user.getValue().getUserName())){
                return true;
            }
        }
        return false;
    }

    public static String isAuthorized(String username, String password, HashMap<String, User> users){
        for(Map.Entry<String, User> user : users.entrySet()){
            if(username.equals(user.getValue().getUserName())){
                if(password.equals(user.getValue().getPassword())){
                    return user.getKey();
                }
            }
        }
        return null;
    }

}
