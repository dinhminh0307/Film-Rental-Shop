package Model.Order;

import Model.Product.Product;
;import java.time.LocalDate;

public class OrderDetail {
    private static String[] statuses = {"RETURNED", "LATE", "OK"};
    private String orderId;

    private String OrderDetailId;

    private String cartId;
    private Product boughtItem;
    private int quantity;

    private LocalDate dueDate;

    private String status;

    public OrderDetail(String orderDetailId, String orderId, String cartId, Product boughtItem, int quantity) {
        this.OrderDetailId = orderDetailId;
        this.orderId = orderId;
        this.cartId = cartId;
        this.boughtItem = boughtItem;
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCartId() {
        return cartId;
    }

    public Product getBoughtItem() {
        return boughtItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getOrderDetailId() {
        return OrderDetailId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setOrderDetailId(String orderDetailId) {
        OrderDetailId = orderDetailId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static String[] getStatuses() {
        return statuses;
    }
}
