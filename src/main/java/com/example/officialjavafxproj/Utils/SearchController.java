package com.example.officialjavafxproj.Utils;

import DataAccess.DataAccess;
import Model.Product.Product;
import Model.User.User;

import java.util.HashMap;
import java.util.Map;

public class SearchController {
    private static HashMap<String,Product> tempContainer = new HashMap<String,Product>();

    private static HashMap<String,User> tempUserContainer = new HashMap<String,User>();

    private static void addProduct(Product product){tempContainer.put(product.getId(),product);}
    private static void addUser(User user){tempUserContainer.put(user.getUserId(),user);}

    public static HashMap<String, Product> getTempContainer() {return tempContainer;}

    public static HashMap<String, User> getTempUserContainer() {
        return tempUserContainer;
    }

    public static boolean searchByString(String input, String searchField){
        if(input.equalsIgnoreCase(searchField) || searchField.toLowerCase().contains(input.toLowerCase())){
            return true;
        } else {
            return false;
        }
    }
    public static void searchByIdentify(String productIdentifier, HashMap<String,Product> productHashMap) {
        tempContainer.clear();
        for(Map.Entry<String,Product> product : productHashMap.entrySet()){
            if(SearchController.searchByString(productIdentifier,product.getValue().getTitle())){
                SearchController.addProduct(product.getValue());
            }
        }
    }
    public static void searchByUserIdentify(String userIdentifier, HashMap<String, User> userHashMap){
        tempUserContainer.clear();
        for(Map.Entry<String,User> user : userHashMap.entrySet()){
            if(SearchController.searchByString(userIdentifier,user.getValue().getFullName()) || SearchController.searchByString(userIdentifier,user.getValue().getUserId())){
                SearchController.addUser(user.getValue());
            }
        }
    }
}
