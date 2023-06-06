package Service;

import DataAccess.DataAccess;
import Model.Order.Cart;
import java.util.HashMap;

public class UserCartServices implements Services<Cart>{

    private UserCartServices(){
    }

    public static UserCartServices builder(){
        return new UserCartServices();
    }

    @Override
    public String idCreation() {
        return "CART00" + DataAccess.getAllCarts().size();
    }
    @Override
    public void add(Cart userCart) {
        DataAccess.getCurrentUser().addCard(userCart);
    }

    @Override
    public void edit(Cart anotherCart) {
        DataAccess.getCurrentUser().addCard(anotherCart);
    }

    @Override
    public void delete(Cart deletedCart) {
        DataAccess.getCurrentUser().addCard(deletedCart);
    }

    @Override
    public Cart getOne(String id) {
        return DataAccess.getCurrentUser().getCart();
    }

    @Override
    public HashMap<String, Cart> getAll() {
        HashMap<String, Cart> cartMap = new HashMap<>();
        for(Cart cart : DataAccess.getAllCarts()){
            cartMap.put(cart.getCartId(), cart);
        }
        return cartMap;
    }
}
