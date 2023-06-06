package com.example.officialjavafxproj.Utils;

import Model.Product.Product;
import Service.FeedbackService;

import java.util.Comparator;

public class TopProductComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        if(FeedbackService.getAverageRatings(o1.getId()) >= FeedbackService.getAverageRatings(o2.getId())){
            return -1;
        }else{
            return 1;
        }
//        return (int) (FeedbackService.getAverageRatings(o2.getId()) - FeedbackService.getAverageRatings(o1.getId()));
    }
}
