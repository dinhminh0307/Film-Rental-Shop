package Model.User;

import Model.Product.Product;
import Service.AdminService;
import Service.ProductService;

import java.util.HashMap;

public class Admin extends User {
    public Admin(String userId, String userName, String password, String fullName, String address, String phoneNum, String imageLocation) {
        super(userId, userName, password, fullName, address, phoneNum, imageLocation);
    }




}
