package com.example.officialjavafxproj.Utils;

import Middleware.DateMiddleware;
import Model.Account.Account;
import Model.Form.Feedback;
import Model.Product.Product;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class ChartDataController {
    public static ArrayList<String[]> getChartProductData(ArrayList<Product> products, String[] groups){
        ArrayList<String[]> dataFile = new ArrayList<>();
        for(String group : groups){
            int[] genresNum = {0, 0, 0, 0};
            for(Product product : products){
                if(product.getRentalType().equals(group)){
                    switch (product.getGenre()) {
                        case "ACTION" -> genresNum[0]++;
                        case "HORROR" -> genresNum[1]++;
                        case "DRAMA" -> genresNum[2]++;
                        case "COMEDY" -> genresNum[3]++;
                    }
                }
            }

            dataFile.add(new String[]{Product.getGenres()[0], String.valueOf(genresNum[0]), group});
            dataFile.add(new String[]{Product.getGenres()[1], String.valueOf(genresNum[1]), group});
            dataFile.add(new String[]{Product.getGenres()[2], String.valueOf(genresNum[2]), group});
            dataFile.add(new String[]{Product.getGenres()[3], String.valueOf(genresNum[3]), group});
        }
//        for(String[] string : dataFile){
//            System.out.println(Arrays.asList(string));
//        }

        return dataFile;
    }

    public static ArrayList<String[]> getChartAccountData(ArrayList<Account> accounts){
        ArrayList<String[]> dataFile = new ArrayList<>();
        int[] typeNum = {0, 0, 0};
        for(Account account : accounts){
            switch (account.getAccountType()) {
                case "GuestAccount" -> typeNum[0]++;
                case "RegularAccount" -> typeNum[1]++;
                case "VIPAccount" -> typeNum[2]++;
            }
        }

        dataFile.add(new String[]{"GuestAccount", String.valueOf(typeNum[0])});
        dataFile.add(new String[]{"RegularAccount", String.valueOf(typeNum[1])});
        dataFile.add(new String[]{"VIPAccount", String.valueOf(typeNum[2])});

//        for(String[] string : dataFile){
//            System.out.println(Arrays.asList(string));
//        }

        return dataFile;
    }

    public static ArrayList<String[]> getChartRevenueData(TreeMap<LocalDate, Double> revenue){
        ArrayList<String[]> dataFile = new ArrayList<>();
       for(Map.Entry<LocalDate, Double> revenueDaily : revenue.entrySet()){
           dataFile.add(new String[]{revenueDaily.getKey().toString(), String.valueOf(revenueDaily.getValue())});
       }
        return dataFile;
    }

    public static ArrayList<String[]> getChartRating(ArrayList<Feedback> feedbacks, String productId){
        ArrayList<String[]> dataFile = new ArrayList<>();
        int[] starNum = {0, 0, 0, 0, 0};
        for(Feedback feedback : feedbacks){
            if(feedback.getProductId().equals(productId)){
                switch (feedback.getRating()){
                    case 1 ->  starNum[0]++;
                    case 2 -> starNum[1]++;
                    case 3 ->  starNum[2]++;
                    case 4 -> starNum[3]++;
                    case 5 -> starNum[4]++;
                }
            }
        }
        dataFile.add(new String[]{"1", String.valueOf(starNum[0])});
        dataFile.add(new String[]{"2", String.valueOf(starNum[1])});
        dataFile.add(new String[]{"3", String.valueOf(starNum[2])});
        dataFile.add(new String[]{"4", String.valueOf(starNum[3])});
        dataFile.add(new String[]{"5", String.valueOf(starNum[4])});
//        for(String[] string : dataFile){
//            System.out.println(Arrays.asList(string));
//        }

        return dataFile;
    }


}
