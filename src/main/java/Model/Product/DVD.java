package Model.Product;

public class DVD extends Product{
    public DVD(String id, String title, String rentalType, String genre, String publishedYear, int numOfCopies, double rentalFee, String loanType, String status, String imageLocation) {
        super(id, title, "DVD", genre, publishedYear, numOfCopies, rentalFee, loanType, status, imageLocation);
    }
}
