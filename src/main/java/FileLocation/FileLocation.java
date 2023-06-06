package FileLocation;

public class FileLocation {
    private static final String userFileDir = "src/main/java/DataFile/User.csv";

    private static final String adminOrdersDir = "src/main/java/DataFile/AdminOrders.csv";

    private static final String adminOrdersDetailDir = "src/main/java/DataFile/AdminOrdersDetail.csv";
    private static final String productFeedbackDir = "src/main/java/DataFile/ProductFeedback.csv";

    private static final String workingDir = System.getProperty("user.dir");

    private static final String revenueDir = "src/main/java/DataFile/StoreRevenue.csv";
    private static final String imageDir = "src/main/resources/com/example/officialjavafxproj/Image/";

    private static final String AdminFileDir = "src/main/java/DataFile/Admin.csv";

    private static final String AccountFileDir = "src/main/java/DataFile/Account.csv";
    private static final String ProductFileDir = "src/main/java/DataFile/Product.csv";
    private static final String OrderFileDir = "src/main/java/DataFile/Order.csv";
    private static final String OrderDetailFileDir = "src/main/java/DataFile/OrderDetail.csv";
    private static final String CartFileDir = "src/main/java/DataFile/Cart.csv";

    public static String getUserFileDir() {
        return userFileDir;
    }

    public static String getAccountFileDir() {
        return AccountFileDir;
    }

    public static String getProductFileDir() {
        return ProductFileDir;
    }

    public static String getOrderFileDir() {
        return OrderFileDir;
    }

    public static String getOrderDetailFileDir() {
        return OrderDetailFileDir;
    }

    public static String getCartFileDir() {
        return CartFileDir;
    }

    public static String getAdminFileDir() {
        return AdminFileDir;
    }

    public static String getWorkingDir() {
        return workingDir;
    }

    public static String getRevenueDir() {
        return revenueDir;
    }

    public static String getProductFeedbackDir() {
        return productFeedbackDir;
    }

    public static String getImageDir() {
        return workingDir + "/" + imageDir;
    }

    public static String getAdminOrdersDir() {
        return adminOrdersDir;
    }

    public static String getAdminOrdersDetailDir() {
        return adminOrdersDetailDir;
    }
}
