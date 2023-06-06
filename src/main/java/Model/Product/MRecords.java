package Model.Product;

public class MRecords extends Product{
    public MRecords(String id, String title, String rentalType, String genre, String publishedYear, int numOfCopies, double rentalFee, String loanType, String status, String imageLocation) {
        super(id, title, "RECORD", genre, publishedYear, numOfCopies, rentalFee, loanType, status, imageLocation);
    }
}
