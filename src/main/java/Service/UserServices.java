package Service;

import DataAccess.DataAccess;
import Middleware.UserMiddleware;
import Model.Account.GuestAccount;
import Model.User.User;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class UserServices implements Services<User> {

    private UserServices(){

    }

    public static UserServices builder(){
        return new UserServices();
    }


    @Override
    public String idCreation() {
        int numOfUsers = DataAccess.getAllUsers().size();
        if(numOfUsers < 10){
            return "C00" + numOfUsers;
        }else if(numOfUsers <= 99){
            return "C0" + numOfUsers;
        }else{
            return "C" + numOfUsers;
        }
    }

    @Override
    public void add(User user) {
        if(!UserMiddleware.isDuplicatedUsername(user.getUserName(), DataAccess.getAllUsers())) {
            DataAccess.getAllUsers().put(idCreation(), user);
        }else{
            throw new Error("Duplicate username!");
        }
    }

    @Override
    public void edit(User user) {
//        DataAccess.getAllUsers().put(user.getUserId(), user);
    }

    @Override
    public void delete(User user) {
//        DataAccess.getAllUsers().remove(user.getUserId());
    }

    @Override
    public User getOne(String userId) {
        return DataAccess.getAllUsers().get(userId);
    }

    @Override
    public HashMap<String, User> getAll() {
        return DataAccess.getAllUsers();
    }

    public boolean login(String username, String password){
        String userId = UserMiddleware.isAuthorized(username, password, DataAccess.getAllUsers());
        if(userId == null){
            return false;
        }else{
            DataAccess.setCurrentUser(DataAccess.getAllUsers().get(userId));
            return true;
        }
    }

    public User getCurrentUser(){
        return DataAccess.getCurrentUser();
    }


    public void setCurrentUser(User user){
        DataAccess.setCurrentUser(user);
    }

    public void register(User user){
        this.add(user);
        DataAccess.setCurrentUser(user);
        user.getAccount().setOwner(user);
        DataAccess.addAccountToList(user.getAccount());
    }

}
