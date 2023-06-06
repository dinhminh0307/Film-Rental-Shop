package Model.Order;
import java.util.ArrayList;

public class Cart {

    private String cartId;
    private String userId;
    private ArrayList<OrderDetail> shoppingItems = new ArrayList<>();

    public Cart(String cartId, String userId) {
        this.cartId = cartId;
        this.userId = userId;
    }

    public Cart() {
    }

    public String getCartId() {
        return cartId;
    }

    public String getUserId() {
        return userId;
    }

    public ArrayList<OrderDetail> getShoppingItems() {
        return shoppingItems;
    }

    public void addItemToCart(OrderDetail detail){
        shoppingItems.add(detail);
    }
}
