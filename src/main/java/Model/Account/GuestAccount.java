package Model.Account;

public class GuestAccount extends Account {

    public GuestAccount(String accountId, String accountType, int points, int numReturnedItems, boolean isAllowed2DaysItems, int rentalThreshold, boolean isCurrentlyBorrowed) {
        super(accountId, accountType, points, numReturnedItems, isAllowed2DaysItems, rentalThreshold, isCurrentlyBorrowed);
    }

    public GuestAccount() {
        super();
    }

    public boolean isRestricted() {
        return getRentalThreshold() > 2;
    }


    @Override
    public void addPoint(int addedPoints) {
        throw new Error("You are not qualified to add point");
    }

    @Override
    public boolean isFreeToBorrowOne() {
        throw new Error("You are not qualified to borrow anything free");
    }

    @Override
    public boolean isAllowedToPromoted() {
        return getNumReturnedItems() > 3;
    }
}
