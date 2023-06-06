package DataAccess;

import Middleware.DateMiddleware;
import Model.Account.*;
import Model.Form.Feedback;
import Model.Order.Cart;
import Model.Order.Order;
import Model.Order.OrderDetail;
import Model.Product.DVD;
import Model.Product.Game;
import Model.Product.MRecords;
import Model.Product.Product;
import Model.User.Admin;
import Model.User.Customer;
import Model.User.User;
import FileLocation.FileLocation;
import Service.FeedbackService;
import Service.UserServices;
import com.example.officialjavafxproj.Utils.DateComparator;
import com.example.officialjavafxproj.Utils.TopProductComparator;


import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataAccess {
    private static final HashMap<String, User> users = new HashMap<>();

    private static HashMap<String, User> sortedUsers = new HashMap<>();

    private static HashMap<String, Product> sortedProducts = new HashMap<>();

    private static final ArrayList<String[]> sortedOptions = new ArrayList<>();
    private static final HashMap<String, Account> accounts = new HashMap<>();

    private static final HashMap<LocalDate, Double> revenueDaily = new HashMap<>();

    private static final ArrayList<Cart> carts = new ArrayList<>();

    private static final ArrayList<Feedback> feedbacks = new ArrayList<>();
    private static final HashMap<String, Product> products = new HashMap<>();

    private static final ArrayList<OrderDetail> orderDetails = new ArrayList<>();
    private static final ArrayList<OrderDetail> orderAdminDetails = new ArrayList<>();

    private static final ArrayList<Order> orders = new ArrayList<>();
    private static final ArrayList<Order> adminOrders = new ArrayList<>();

    private static User currentUser;

    private static Product chosenProduct;

    private static Order currentOrder;

    private static User selectedCustomer;

    private static ArrayList<String[]> getDataFromFile(String fileLocation) {
        try {
            ArrayList<String[]> dataFile = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(fileLocation));
            String line = reader.readLine();
            while (line != null) {
                String[] dataLine = line.split(";");
                dataFile.add(dataLine);
                // Do something with the values array
                line = reader.readLine();
            }
            reader.close();
            return dataFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static void loadRevenueAdmin(){
        ArrayList<String[]> dataFile = getDataFromFile(FileLocation.getRevenueDir());
        for(String[] revenueData : Objects.requireNonNull(dataFile)){
            revenueDaily.put(LocalDate.parse(revenueData[0], DateMiddleware.dateParser()), Double.parseDouble(revenueData[1]));
        }
    }

    private static void transferRevenueAdmin(){
        try {
            FileWriter writer = new FileWriter(FileLocation.getRevenueDir(), false);
            for (Map.Entry<LocalDate, Double> revenue : revenueDaily.entrySet()) {
                writer.write(DateMiddleware.dateAfterFormat(revenue.getKey()) + ";"
                        + revenue.getValue() + "\n");
            }
            writer.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    private static void loadAllProductFeedback(){
        ArrayList<String[]> dataFile = getDataFromFile(FileLocation.getProductFeedbackDir());
        for(Map.Entry<String, Product> product : products.entrySet()){
            for(String[] feedbackData : Objects.requireNonNull(dataFile)){
                Feedback feedback = Feedback.builder()
                        .withCustomerId(feedbackData[0])
                        .withProductId(feedbackData[1])
                        .withRating(Integer.parseInt(feedbackData[2]))
                        .withFeedbackContent(feedbackData[3])
                        .withReviewDate(LocalDate.parse(feedbackData[4], DateMiddleware.dateParser()))
                        .build();
                if(product.getKey().equals(feedbackData[1])){
                    product.getValue().addFeedback(feedback);
                }
            }
        }

        for(Map.Entry<String, User> user : users.entrySet()){
            for(String[] feedbackData : Objects.requireNonNull(dataFile)){
                Feedback feedback = Feedback.builder()
                        .withCustomerId(feedbackData[0])
                        .withProductId(feedbackData[1])
                        .withRating(Integer.parseInt(feedbackData[2]))
                        .withFeedbackContent(feedbackData[3])
                        .withReviewDate(LocalDate.parse(feedbackData[4], DateMiddleware.dateParser()))
                        .build();
                if(user.getKey().equals(feedbackData[0])){
                    user.getValue().addReview(feedback);
                    feedbacks.add(feedback);
                }
            }
        }

    }

    private static void loadAllUsersNoAccounts() {
        ArrayList<String[]> dataFile = getDataFromFile(FileLocation.getUserFileDir());
        for (String[] userData : Objects.requireNonNull(dataFile)) {
            if (userData[0].equals("ADMIN")) {
                users.put(userData[0],
                        new Admin(userData[0], userData[1], userData[2], userData[3], userData[4], userData[5], userData[6]));
            }
            else {
                users.put(userData[0], new Customer(userData[0], userData[1], userData[2], userData[3], userData[4], userData[5], Double.parseDouble(userData[6]), new GuestAccount(), new Cart(), userData[7]));
            }
        }
    }

    private static void loadAllAccounts() {
        ArrayList<String[]> dataFile = getDataFromFile(FileLocation.getAccountFileDir());
        for (String[] accountData : Objects.requireNonNull(dataFile)) {
            if (Objects.equals(accountData[1], "GuestAccount")) {
                users.get(accountData[7]).setAccount(new GuestAccount(accountData[0], accountData[1], Integer.parseInt(accountData[2]), Integer.parseInt(accountData[3]), Boolean.parseBoolean(accountData[4]), Integer.parseInt(accountData[5]), Boolean.parseBoolean(accountData[6])));
                GuestAccount guestAcc = (GuestAccount) users.get(accountData[7]).getAccount();
                guestAcc.setOwner(users.get(accountData[7]));
                accounts.put(accountData[0], guestAcc);
            } else if (Objects.equals(accountData[1], "RegularAccount")) {
                users.get(accountData[7]).setAccount(new RegularAccount(accountData[0], accountData[1], Integer.parseInt(accountData[2]), Integer.parseInt(accountData[3]), Boolean.parseBoolean(accountData[4]), Integer.parseInt(accountData[5]), Boolean.parseBoolean(accountData[6])));
                RegularAccount regularAcc = (RegularAccount) users.get(accountData[7]).getAccount();
                regularAcc.setOwner(users.get(accountData[7]));
                accounts.put(accountData[0], regularAcc);
            } else if (Objects.equals(accountData[1],"AdminAccount")) {
                users.get(accountData[7]).setAccount(new AdminAccount(accountData[0], accountData[1], Integer.parseInt(accountData[2]), Integer.parseInt(accountData[3]), Boolean.parseBoolean(accountData[4]), Integer.parseInt(accountData[5]), Boolean.parseBoolean(accountData[6])));
                AdminAccount adminAccount = (AdminAccount) users.get(accountData[7]).getAccount();
                adminAccount.setOwner(users.get(accountData[7]));
                accounts.put(accountData[0],adminAccount);
            } else {
                users.get(accountData[7]).setAccount(new VIPAccount(accountData[0], accountData[1], Integer.parseInt(accountData[2]), Integer.parseInt(accountData[3]), Boolean.parseBoolean(accountData[4]), Integer.parseInt(accountData[5]), Boolean.parseBoolean(accountData[6])));
                VIPAccount VIPAcc = (VIPAccount) users.get(accountData[7]).getAccount();
                VIPAcc.setOwner(users.get(accountData[7]));
                accounts.put(accountData[0], VIPAcc);
            }
        }
    }

    private static void loadAllProducts() {
        ArrayList<String[]> dataFile = getDataFromFile(FileLocation.getProductFileDir());
        for (String[] productData : Objects.requireNonNull(dataFile)) {
            if (productData[2].equals("DVD")) {
                DVD dvd = new DVD(productData[0], productData[1], productData[2], productData[3], productData[4], Integer.parseInt(productData[5]), Double.parseDouble(productData[6]), productData[7], productData[8], productData[9]);
                dvd.setBeingBorrowed(Boolean.parseBoolean(productData[10]));
                products.put(productData[0], dvd);
            } else if (productData[2].equals("RECORD")) {
                MRecords record = new MRecords(productData[0], productData[1], productData[2], productData[3], productData[4], Integer.parseInt(productData[5]), Double.parseDouble(productData[6]), productData[7], productData[8], productData[9]);
                record.setBeingBorrowed(Boolean.parseBoolean(productData[10]));
                products.put(productData[0], record);
            } else {
                Game game = new Game(productData[0], productData[1], productData[2], productData[3], productData[4], Integer.parseInt(productData[5]), Double.parseDouble(productData[6]), productData[7], productData[8], productData[9]);
                game.setBeingBorrowed(Boolean.parseBoolean(productData[10]));
                products.put(productData[0], game);
            }
        }
    }

    private static void loadAllOrderDetails() {
        ArrayList<String[]> dataFile = getDataFromFile(FileLocation.getOrderDetailFileDir());
        for (String[] orderDetailData : Objects.requireNonNull(dataFile)) {
            OrderDetail details = new OrderDetail(orderDetailData[0], orderDetailData[1], orderDetailData[2], (products.get(orderDetailData[3]) == null ? products.get("deleted") : products.get(orderDetailData[3])), Integer.parseInt(orderDetailData[4]));
            details.setDueDate(LocalDate.parse(orderDetailData[5], DateMiddleware.dateParser()));
            if(details.getDueDate().compareTo(LocalDate.now()) < 0 && !details.getBoughtItem().getId().equals("deleted")){
                details.setStatus("LATE");
            }else{
                details.setStatus(orderDetailData[6]);
            }
            details.setStatus(orderDetailData[6]);
            orderDetails.add(details);
        }
    }

    private static void loadAllAdminOrderDetails(){
        ArrayList<String[]> dataFile = getDataFromFile(FileLocation.getAdminOrdersDetailDir());
        for (String[] orderDetailData : Objects.requireNonNull(dataFile)) {
            OrderDetail details = new OrderDetail(orderDetailData[0], orderDetailData[1], orderDetailData[2],(products.get(orderDetailData[3]) == null ? products.get("deleted") : products.get(orderDetailData[3])), Integer.parseInt(orderDetailData[4]));
            details.setDueDate(LocalDate.parse(orderDetailData[5], DateMiddleware.dateParser()));

            if(orderDetailData[6].equals("RETURNED")){
                details.setStatus(orderDetailData[6]);
            }else{
                if(details.getDueDate().compareTo(LocalDate.now()) < 0 && !details.getBoughtItem().getId().equals("deleted")){
                    details.setStatus("LATE");
                }else{
                    details.setStatus(orderDetailData[6]);
                }

            }
            orderAdminDetails.add(details);
        }
    }

    private static void loadAllOrdersNoDetail() {
        ArrayList<String[]> dataFile = getDataFromFile(FileLocation.getOrderFileDir());
        for (String[] orderData : Objects.requireNonNull(dataFile)) {
            Order order = new Order(orderData[0], orderData[1], LocalDate.parse(orderData[2], DateMiddleware.dateParser()), Double.parseDouble(orderData[3]));
            orders.add(order);
        }
    }

    private static void loadAllAdminOrdersNoDetail() {
        ArrayList<String[]> dataFile = getDataFromFile(FileLocation.getAdminOrdersDir());
        for (String[] orderData : Objects.requireNonNull(dataFile)) {
            Order order = new Order(orderData[0], orderData[1], LocalDate.parse(orderData[2], DateMiddleware.dateParser()), Double.parseDouble(orderData[3]));
            adminOrders.add(order);
        }
    }

    private static void loadAllCartsNoDetail() {
        ArrayList<String[]> dataFile = getDataFromFile(FileLocation.getCartFileDir());
        for (String[] cartData : Objects.requireNonNull(dataFile)) {
            Cart cart = new Cart(cartData[0], cartData[1]);
            carts.add(cart);
        }
    }

    private static void loadAllOrders() {
        for (Order order : orders) {
            for (OrderDetail details : orderDetails) {
                if (order.getOrderId().equals(details.getOrderId())) {
                    order.addOrderDetailsToOrder(details);
                }
            }
            users.get(order.getUserId()).addOrderToList(order);
        }
    }

    private static void loadAllAdminOrders(){
        for (Order order : adminOrders){
            for (OrderDetail details : orderAdminDetails){
                if (order.getOrderId().equals(details.getOrderId())) {
                    order.addOrderDetailsToOrder(details);
                }
            }
        }
    }

    private static void loadAllCarts() {
        for (Cart cart : carts) {
            for (OrderDetail details : orderDetails) {
                if (cart.getCartId().equals(details.getCartId())) {
                    cart.addItemToCart(details);
                }
            }
            users.get(cart.getUserId()).addCard(cart);
        }
    }

    private static void transferAllUsers() {
        try {
            FileWriter writer = new FileWriter(FileLocation.getUserFileDir(), false);
            for (Map.Entry<String, User> user : users.entrySet()) {
                writer.write(user.getValue().getUserId() + ";"
                        + user.getValue().getUserName() + ";"
                        + user.getValue().getPassword() + ";"
                        + user.getValue().getFullName() + ";"
                        + user.getValue().getAddress() + ";"
                        + user.getValue().getPhoneNum() + ";"
                        + user.getValue().getBalance() + ";"
                        + user.getValue().getImageLocation() + "\n");
            }
            writer.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    private static void transferAllAccounts() {
        try {
            FileWriter writer = new FileWriter(FileLocation.getAccountFileDir(), false);
            for (Map.Entry<String, User> user : users.entrySet()) {
                writer.write(user.getValue().getAccount().getAccountId() + ";"
                        + user.getValue().getAccount().getAccountType() + ";"
                        + user.getValue().getAccount().getPoints() + ";"
                        + user.getValue().getAccount().getNumReturnedItems() + ";"
                        + user.getValue().getAccount().getIsAllowed2DaysItems() + ";"
                        + user.getValue().getAccount().getRentalThreshold() + ";"
                        + user.getValue().getAccount().getIsCurrentlyBorrowed() + ";"
                        + user.getValue().getAccount().getOwner().getUserId() + "\n");
            }
            writer.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    private static void transferAllProduct() {
        try {
            FileWriter writer = new FileWriter(FileLocation.getProductFileDir(), false);
            for (Map.Entry<String, Product> product : products.entrySet()) {
                writer.write(product.getValue().getId() + ";"
                        + product.getValue().getTitle() + ";"
                        + product.getValue().getRentalType() + ";"
                        + product.getValue().getGenre() + ";"
                        + product.getValue().getPublishedYear() + ";"
                        + product.getValue().getNumOfCopies() + ";"
                        + product.getValue().getRentalFee() + ";"
                        + product.getValue().getLoanType() + ";"
                        + product.getValue().getStatus() + ";"
                        + product.getValue().getImageLocation() + ";"
                        + product.getValue().getIsBeingBorrowed() + "\n");
            }
            writer.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    private static void transferAllProductFeedback(){
        try {
            FileWriter writer = new FileWriter(FileLocation.getProductFeedbackDir(), false);
            for (Map.Entry<String, Product> product : products.entrySet()) {
                if(product.getValue().getItemsFeedback().size() == 0){
                    continue;
                }
                for(Feedback feedback : product.getValue().getItemsFeedback()){
                    writer.write(feedback.getCustomerId() + ";"
                            + feedback.getProductId() + ";"
                            + feedback.getRating() + ";"
                            + feedback.getFeedBackContent().replaceAll("\n", "") + ";"
                            + DateMiddleware.dateAfterFormat(feedback.getReviewDate()) + "\n");
                }
            }
            writer.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    private static void transferAllOrderDetails() {
        try {
            FileWriter writer = new FileWriter(FileLocation.getOrderDetailFileDir(), false);
            for (Map.Entry<String, User> user : users.entrySet()) {
                if(user.getValue().getRentalList() != null){
                    for (Order order : user.getValue().getRentalList()) {
                        for (OrderDetail detail : order.getOrders()) {
                            writer.write(detail.getOrderDetailId() + ";"
                                    + order.getOrderId() + ";"
                                    + "NaN" + ";"
                                    + detail.getBoughtItem().getId() + ";"
                                    + detail.getQuantity() + ";"
                                    + DateMiddleware.dateAfterFormat(detail.getDueDate()) + ";"
                                    + detail.getStatus() + "\n");
                        }
                    }
                }
                if(user.getValue().getCart() != null){
                    for (OrderDetail detail : user.getValue().getCart().getShoppingItems()) {
                        writer.write(detail.getOrderDetailId() + ";"
                                + "NaN" + ";"
                                + user.getValue().getCart().getCartId() + ";"
                                + detail.getBoughtItem().getId() + ";"
                                + detail.getQuantity()+ ";"
                                + DateMiddleware.dateAfterFormat(LocalDate.now()) + ";"
                                + "UNDEFINED" + "\n");
                    }
                }
            }
            writer.close();

            FileWriter writerAdmin = new FileWriter(FileLocation.getAdminOrdersDetailDir(), false);
            for(OrderDetail orderDetail : orderAdminDetails){
                if(!orderDetail.getOrderId().equals("NaN")){
                    writerAdmin.write(orderDetail.getOrderDetailId() + ";"
                            + orderDetail.getOrderId() + ";"
                            + "NaN" + ";"
                            + (orderDetail.getBoughtItem() == null ? "deleted" : orderDetail.getBoughtItem().getId()) + ";"
                            + orderDetail.getQuantity() + ";"
                            + DateMiddleware.dateAfterFormat(orderDetail.getDueDate())+ ";"
                            + orderDetail.getStatus()+ "\n");
                }
            }
            writerAdmin.close();

        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    private static void transferAllCarts() {
        try {
            FileWriter writer = new FileWriter(FileLocation.getCartFileDir(), false);
            for (Map.Entry<String, User> user : users.entrySet()) {
                if (user.getValue().getCart() == null) {
                    continue;
                }
                writer.write(user.getValue().getCart().getCartId() + ";"
                        + user.getValue().getUserId() + "\n");
            }
            writer.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    private static void transferAllOrders() {
        try {
            FileWriter writer = new FileWriter(FileLocation.getOrderFileDir(), false);
            for (Map.Entry<String, User> user : users.entrySet()) {
                for (Order order : user.getValue().getRentalList()) {
                    writer.write(order.getOrderId() + ";"
                            + user.getValue().getUserId() + ";"
                            + DateMiddleware.dateAfterFormat(order.getOrderDate()) + ";"
                            + order.getTotalPrice() + "\n");
                }
            }

            writer.close();
            FileWriter writerAdmin = new FileWriter(FileLocation.getAdminOrdersDir(), false);
            for(Order order : adminOrders){
                writerAdmin.write(order.getOrderId() + ";"
                        + order.getUserId() + ";"
                        + DateMiddleware.dateAfterFormat(order.getOrderDate()) + ";"
                        + order.getTotalPrice() + "\n");
            }
            writerAdmin.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    public static void loadAllData() {
        loadAllCartsNoDetail();
        loadRevenueAdmin();
        loadAllUsersNoAccounts();
        loadAllAccounts();
        loadAllProducts();
        loadAllProductFeedback();
        loadAllOrderDetails();
        loadAllAdminOrderDetails();
        loadAllAdminOrdersNoDetail();
        loadAllOrdersNoDetail();
        loadAllOrders();
        loadAllAdminOrders();
        loadAllCarts();
    }

    public static void transferAllData() {
        transferAllUsers();
        transferAllProduct();
        transferAllProductFeedback();
        transferAllAccounts();
        transferAllOrderDetails();
        transferAllCarts();
        transferAllOrders();
        transferRevenueAdmin();
    }


    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        DataAccess.currentUser = currentUser;
    }

    public static void addAccountToList(Account account) {
        accounts.put(account.getAccountId(), account);
    }

    public static HashMap<String, User> getAllUsers() {
        return users;
    }

    public static HashMap<String, Account> getAllAccounts() {
        return accounts;
    }

    public static HashMap<String, Product> getAllProducts() {
        return products;
    }

    public static ArrayList<Cart> getAllCarts() {
        return carts;
    }

    public static ArrayList<Order> getAllOrders() {
        return orders;
    }

    public static ArrayList<Order> getAllAdminOrders() {
        return adminOrders;
    }

    public static ArrayList<OrderDetail> getOrderAdminDetails(){
        return orderAdminDetails;
    }

    public static void setChosenProduct(Product product) {
        DataAccess.chosenProduct = product;
    }

    public static Product getChosenProduct() {
        return chosenProduct;
    }


    public static Order getCurrentOrder() {
        return currentOrder;
    }



    public static void setCurrentOrder(Order currentOrder) {
        DataAccess.currentOrder = currentOrder;
    }

    public static void addSortedOptions(String[] options){
        sortedOptions.add(options);
    }

    public static ArrayList<String[]> getSortedOptions(){
        return sortedOptions;
    }

    public static ArrayList<OrderDetail> getOrderDetails(){
        return orderDetails;
    }

    public static HashMap<String, Product> getSortedProducts(){
        return sortedProducts;
    }
//    public static void setSortedProducts(HashMap<String, Product> sortedProducts) {DataAccess.sortedProducts = sortedProducts;}

    public static void addToSortedProducts(Product product){
        sortedProducts.put(product.getId(), product);
    }
    public static void addToSortedUsers(User user){sortedUsers.put(user.getUserId(),user);}
    public static void setSortedProducts(HashMap<String, Product> sortProducts) {
        sortedProducts = sortProducts;
    }

    public static HashMap<String, User> getGetSortedUsers() {return sortedUsers;}

    public static void setSortedUsers(HashMap<String, User> sortedUser) {
        DataAccess.sortedUsers = sortedUser;
    }
    public static HashMap<String, User> getSortedUsers() {return sortedUsers;}

    public static User getSelectedCustomer() {return selectedCustomer;}
    public static void setSelectedCustomer(User user) {DataAccess.selectedCustomer = user;}

    public static void addRevenue(LocalDate date, double totalPrice){
        if(!revenueDaily.containsKey(date)){
            revenueDaily.put(date, totalPrice);
        }else{
            revenueDaily.put(date, revenueDaily.get(date) + totalPrice);

        }
    }

    public static TreeMap<LocalDate, Double> getRevenueDaily() {
        DateComparator dateComparator = new DateComparator();
        TreeMap<LocalDate, Double> sortedRevenue = new TreeMap<>(dateComparator);
        sortedRevenue.putAll(revenueDaily);
        return sortedRevenue;
    }

    public static TreeMap<Product, String> getTopProducts(){
        TopProductComparator topProductComparator = new TopProductComparator();
        TreeMap<Product, String> topProducts = new TreeMap<>(topProductComparator);
        for(Map.Entry<String, Product> product : products.entrySet()){
            topProducts.put(product.getValue(), product.getKey());
        }
        return topProducts;
    }

    public static double getTotalRevenue(){
        double total = 0;
        for(Map.Entry<LocalDate, Double> revenue : revenueDaily.entrySet()){
            total += revenue.getValue();
        }
        return total;
    }

    public static ArrayList<Feedback> getFeedbacks(){
        return feedbacks;
    }

    public static void addFeedback(Feedback feedback){
        feedbacks.add(feedback);
    }

    public static void addToOrders(Order order){
        adminOrders.add(order);
    }
}
