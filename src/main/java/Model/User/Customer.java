package Model.User;

import Model.Account.Account;
import Model.Order.Cart;

public class Customer extends User {
    public Customer(String userId, String userName, String password, String fullName, String address, String phoneNum, double balance, Account account, Cart cart, String imageLocation) {
        super(userId, userName, password, fullName, address, phoneNum, balance, account,cart, imageLocation);
    }
    public Customer() {
        super();
    }

}
