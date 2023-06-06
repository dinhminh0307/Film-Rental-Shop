package Model.Account;

import DataAccess.DataAccess;
import Model.User.User;
import javafx.scene.chart.PieChart;

public abstract class Account {
    private String accountId;

    private User owner;
    private String accountType;
    private int points;

    private int numReturnedItems;
    private boolean isAllowed2DaysItems;
    private int rentalThreshold;

    private boolean isCurrentlyBorrowed;

    private String createAccountId(){
        DataAccess db = new DataAccess();
        int numOfAccount = DataAccess.getAllAccounts().size();
        return "Acc00" + numOfAccount;
    }

    public Account(String accountId, String accountType, int points, int numReturnedItems, boolean isAllowed2DaysItems, int rentalThreshold, boolean isCurrentlyBorrowed) {
        this.accountId = accountId;
        this.accountType = accountType;
        this.points = points;
        this.numReturnedItems = numReturnedItems;
        this.isAllowed2DaysItems = isAllowed2DaysItems;
        this.rentalThreshold = rentalThreshold;
        this.isCurrentlyBorrowed = isCurrentlyBorrowed;
    }

    public Account() {
        this.accountId = createAccountId();
        this.accountType = "GuestAccount";
        this.points = 0;
        this.numReturnedItems = 0;
        this.isAllowed2DaysItems = false;
        this.rentalThreshold = 2;
        this.isCurrentlyBorrowed = false;
    }


    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public User getOwner() {
        return owner;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public boolean isAllowed2DaysItems() {
        return isAllowed2DaysItems;
    }

    public boolean isCurrentlyBorrowed() {
        return isCurrentlyBorrowed;
    }

    public int getRentalThreshold() {
        return rentalThreshold;
    }

    public void setRentalThreshold(int rentalThreshold) {
        this.rentalThreshold = rentalThreshold;
    }

    public boolean getIsAllowed2DaysItems() {
        return isAllowed2DaysItems;
    }

    public void setAllowed2DaysItems(boolean allowed2DaysItems) {
        isAllowed2DaysItems = allowed2DaysItems;
    }

    public boolean getIsCurrentlyBorrowed() {
        return isCurrentlyBorrowed;
    }

    public void setCurrentlyBorrowed(boolean currentlyBorrowed) {
        isCurrentlyBorrowed = currentlyBorrowed;
    }

    public int getNumReturnedItems() {
        return numReturnedItems;
    }

    public void setNumReturnedItems(int numReturnedItems) {
        this.numReturnedItems = numReturnedItems;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }



    abstract public void addPoint(int addedPoints);

    abstract public boolean isFreeToBorrowOne();

    abstract public boolean isAllowedToPromoted();


}
