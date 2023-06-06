package Service;

import DataAccess.DataAccess;
import Model.Account.Account;

import java.util.ArrayList;
import java.util.Map;

public class AccountService {
    public static ArrayList<Account> getAllAccounts(){
        ArrayList<Account> arrayAccounts = new ArrayList<>();
        for(Map.Entry<String, Account> account : DataAccess.getAllAccounts().entrySet()){
            arrayAccounts.add(account.getValue());
        }
        return arrayAccounts;
    }

    public static void updateAccounts(Account account){
        DataAccess.getAllAccounts().put(account.getOwner().getUserId(), account);
    }
}
