package Model.Account;

public class AdminAccount extends Account{
    public AdminAccount() {
    }

    public AdminAccount(String accountId, String accountType, int points, int numReturnedItems, boolean isAllowed2DaysItems, int rentalThreshold, boolean isCurrentlyBorrowed) {
        super(accountId, accountType, points, numReturnedItems, isAllowed2DaysItems, rentalThreshold, isCurrentlyBorrowed);
    }

    @Override
    public void addPoint(int addedPoints) {
    }

    @Override
    public boolean isFreeToBorrowOne() {
        return false;
    }

    @Override
    public boolean isAllowedToPromoted() {
        return false;
    }
}
