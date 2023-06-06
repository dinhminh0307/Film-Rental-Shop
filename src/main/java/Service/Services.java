package Service;

import Model.User.User;

import java.util.ArrayList;
import java.util.HashMap;

public interface Services<T> {
    String idCreation();
    void add(T template);
    void edit(T template);
    void delete(T template);
    T getOne(String id);
    HashMap<String, T> getAll();
}
