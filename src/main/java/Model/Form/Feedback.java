package Model.Form;

import java.time.LocalDate;

public class Feedback {
    private String customerId;
    private String productId;
    private String feedBackContent;
    private int rating;

    private LocalDate reviewDate;

    private Feedback(){
        this.customerId = null;
        this.productId = null;
        this.feedBackContent = null;
        this.rating = 0;
        this.reviewDate = null;
    }

    public static Feedback builder(){
        return new Feedback();
    }

    public Feedback withCustomerId (String customerId){
        this.customerId = customerId;
        return this;
    }

    public Feedback withProductId (String productId){
        this.productId = productId;
        return this;
    }

    public Feedback withFeedbackContent(String content){
        this.feedBackContent = content;
        return this;
    }

    public Feedback withReviewDate(LocalDate date){
        this.reviewDate = date;
        return this;
    }

    public Feedback withRating(int rating){
        this.rating = rating;
        return this;
    }

    public Feedback build(){
        return this;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getProductId() {
        return productId;
    }

    public String getFeedBackContent() {
        return feedBackContent;
    }

    public int getRating() {
        return rating;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }
}
