package Model.Product;

public class Game extends Product{
    public Game(String id, String title, String rentalType, String genre, String publishedYear, int numOfCopies, double rentalFee, String loanType, String status, String imageLocation) {
        super(id, title, "GAME", genre, publishedYear, numOfCopies, rentalFee, loanType, status, imageLocation);
    }
}
