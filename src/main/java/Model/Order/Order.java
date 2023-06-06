package Model.Order;

import DataAccess.DataAccess;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Order {

    private String orderId;
    private String userId;
    private ArrayList<OrderDetail> orders = new ArrayList<>();

    private LocalDate orderDate;
    private double totalPrice;

    public Order(String orderId, String userId, LocalDate date, double totalPrice) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = date;
        this.totalPrice = totalPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Double.compare(order.totalPrice, totalPrice) == 0 && Objects.equals(orderId, order.orderId) && Objects.equals(userId, order.userId) && Objects.equals(orders, order.orders) && Objects.equals(orderDate, order.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, orders, orderDate, totalPrice);
    }

    public LocalDate getOrderDate(){
        return orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public ArrayList<OrderDetail> getOrders() {
        return orders;
    }

    public void addOrderDetailsToOrder(OrderDetail detail){
        orders.add(detail);
    }

}
