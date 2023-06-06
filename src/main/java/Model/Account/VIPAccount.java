
package Model.Account;

public class VIPAccount extends Account{
    public VIPAccount(String accountId, String accountType, int points, int numReturnedItems, boolean isAllowed2DaysItems, int rentalThreshold, boolean isCurrentlyBorrowed) {
        super(accountId, accountType, points, numReturnedItems, isAllowed2DaysItems, rentalThreshold, isCurrentlyBorrowed);
    }

    @Override
    public void addPoint(int addedPoints) {
        setPoints(getPoints() + addedPoints);
    }

    @Override
    public boolean isFreeToBorrowOne() {
        return getPoints() > 100;
    }
    @Override
    public boolean isAllowedToPromoted() {
        return false;
    }

}
