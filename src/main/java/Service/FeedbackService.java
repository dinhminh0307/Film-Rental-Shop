package Service;

import DataAccess.DataAccess;
import Model.Form.Feedback;

import java.util.ArrayList;
import java.util.Map;

import static java.lang.Math.round;

public class FeedbackService {

    public static void addFeedbackToDb(Feedback feedback){
        DataAccess.addFeedback(feedback);
    }

    public static ArrayList<Feedback> getAllReviews(){
//        for(Feedback feedback : DataAccess.getFeedbacks()){
//            System.out.println(feedback.getRating());
//        }
        return DataAccess.getFeedbacks();
    }

    public static double getAverageRatings(String itemId){
        double total = 0;
        double totalFeedbacks = 0;
        if(ProductService.builder().getOne(itemId).getItemsFeedback().size() == 0){
            return 0;
        }
        for(Feedback feedback : ProductService.builder().getOne(itemId).getItemsFeedback()){
                total += feedback.getRating();
                totalFeedbacks++;
        }
        return total/totalFeedbacks;
    }
}
