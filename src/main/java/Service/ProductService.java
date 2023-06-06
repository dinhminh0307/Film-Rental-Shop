package Service;

import DataAccess.DataAccess;
import FileLocation.FileLocation;
import Model.Product.Product;
import com.example.officialjavafxproj.Utils.SearchController;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



public class ProductService implements Services<Product> {

    private ProductService(){

    }


    public static ProductService builder(){
        return new ProductService();
    }
    @Override
    public String idCreation() {
        int numOfProduct = DataAccess.getAllProducts().size();
        if (numOfProduct < 10) {
            return "I00" + numOfProduct + "-" ;
        } else if (numOfProduct <= 99) {
            return "I0" + numOfProduct + "-";
        } else {
            return "I" + numOfProduct + "-";
        }
    }

    @Override
    public void add(Product product) {
        DataAccess.getAllProducts().put(idCreation() + product.getPublishedYear(), product);
    }

    @Override
    public void edit(Product product) {
        DataAccess.getAllProducts().put(product.getId(), product);

    }

    @Override
    public void delete(Product template) {
        DataAccess.getAllProducts().remove(template.getId());
        String imageDir = FileLocation.getImageDir() + template.getImageLocation();
//        try {
//            Files.delete(Path.of(imageDir));
//        }
//         catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public Product getOne(String id) {
        for(Map.Entry<String,Product> product : DataAccess.getAllProducts().entrySet()){
            if(product.getValue().getId().equals(id)){
                return product.getValue();
            }
        }
        return null;
    }

    public void sortByPrice() {
        List<Map.Entry<String, Product>> list = new LinkedList<Map.Entry<String, Product>>(DataAccess.getSortedProducts().entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Product>>() {
            @Override
            public int compare(Map.Entry<String, Product> o1, Map.Entry<String, Product> o2) {
                return (int) (o1.getValue().getRentalFee() - o2.getValue().getRentalFee());
            }
        });
        HashMap<String, Product> temp = new LinkedHashMap<String, Product>();
        DataAccess.getSortedProducts().clear();
        for (Map.Entry<String, Product> product : list) {
            temp.put(product.getKey(), product.getValue());
        }
        DataAccess.setSortedProducts(temp);
    }

    public void sortByTitle() {
        List<Map.Entry<String, Product>> list = new LinkedList<Map.Entry<String, Product>>(DataAccess.getSortedProducts().entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Product>>() {
            @Override
            public int compare(Map.Entry<String, Product> o1, Map.Entry<String, Product> o2) {
                return (o1.getValue().getTitle().toLowerCase().compareTo(o2.getValue().getTitle().toLowerCase()));
            }
        });
        HashMap<String, Product> temp = new LinkedHashMap<String, Product>();
        DataAccess.getSortedProducts().clear();
        for (Map.Entry<String, Product> product : list) {
            temp.put(product.getKey(), product.getValue());
        }
        DataAccess.setSortedProducts(temp);
    }

    @Override
    public HashMap<String, Product> getAll() {
        return DataAccess.getAllProducts();
    }

    public ArrayList<Product> getArrayProducts(){
        ArrayList<Product> products = new ArrayList<>();
        for(Map.Entry<String, Product> product : getAll().entrySet()){
            products.add(product.getValue());
        }
        return products;
    }

    public void setTargetProduct(Product currentProduct) {
        DataAccess.setChosenProduct(currentProduct);
    }

    public void setSortedProduct(HashMap<String, Product> sortedProducts){
        DataAccess.setSortedProducts(sortedProducts);
    }

    public Product getTargetProduct() {
        return DataAccess.getChosenProduct();
    }

    public void addSortedOptions(String[] options) {
        DataAccess.addSortedOptions(options);
    }

    public ArrayList<String[]> getSortedOptions() {
        return DataAccess.getSortedOptions();
    }

    public HashMap<String, Product> getSortedProducts() {
        return DataAccess.getSortedProducts();
    }

    public TreeMap<Product, String> getTopProducts(){
        return DataAccess.getTopProducts();
    }

    public void addToSortedProducts(ArrayList<String[]> sortedOptions, ArrayList<RadioButton> orderOptions) {
        DataAccess.getSortedProducts().clear();
        ArrayList<String> deletedProductId = new ArrayList<>();
        for (int i = 0; i < sortedOptions.size(); i++) {
            if (i == 0) {
                int noneCounter = 0;
                for (String option : sortedOptions.get(i)) {
                    if (option.equals("NONE")) {
                        noneCounter++;
                        continue;
                    }
                    for (Map.Entry<String, Product> sysProduct : getAll().entrySet()) {
                        if (sysProduct.getValue().getRentalType().equals(option)) {
                            DataAccess.addToSortedProducts(sysProduct.getValue());
                        }
                    }
                }
                if (noneCounter == sortedOptions.get(i).length) {
                    getSortedProducts().clear();
                    for (Map.Entry<String, Product> sysProduct : getAll().entrySet()) {
                        DataAccess.addToSortedProducts(sysProduct.getValue());
                    }
                }
            } else if (i == 1) {
                boolean isExisted = false;
                int noneCounter = 0;
                deletedProductId.clear();
//                System.out.println(getSortedProducts());
                for(Map.Entry<String, Product> sysProduct : getSortedProducts().entrySet()){
                    for(String option : sortedOptions.get(i)){
                        if(option.equals("NONE")){

                            noneCounter++;
                            continue;
                        }
                        if (sysProduct.getValue().getGenre().equals(option)) {
                            isExisted = true;
                            break;
                        }
                    }
                    if (!isExisted) {
                        deletedProductId.add(sysProduct.getKey());
                    }
                    isExisted = false;
                    if (noneCounter == sortedOptions.get(i).length) {
                        deletedProductId.clear();
                    }
                    noneCounter = 0;

                }

                for (String deletedId : deletedProductId) {
                    getSortedProducts().remove(deletedId);
                }
            } else if (i == 2) {
                boolean isExisted = false;
                int noneCounter = 0;

                deletedProductId.clear();
//                System.out.println(getSortedProducts());
                for(Map.Entry<String, Product> sysProduct : getSortedProducts().entrySet()){
                    for(String option : sortedOptions.get(i)){
                        if(option.equals("NONE")){
                            noneCounter++;
                            continue;
                        }
                        if (sysProduct.getValue().getLoanType().equals(option)) {
                            isExisted = true;
                            break;
                        }
                    }
                    if (!isExisted) {
                        deletedProductId.add(sysProduct.getKey());
                    }
                    isExisted = false;

                    if (noneCounter == sortedOptions.get(i).length) {
                        deletedProductId.clear();
                    }
                    noneCounter = 0;
                }
                for (String deletedId : deletedProductId) {
                    getSortedProducts().remove(deletedId);
                }
            } else{
                boolean isExisted = false;
                int noneCounter = 0;

                deletedProductId.clear();
//                System.out.println(getSortedProducts());
                for(Map.Entry<String, Product> sysProduct : getSortedProducts().entrySet()){
                    for(String option : sortedOptions.get(i)){
                        if(option.equals("NONE")){

                            noneCounter++;
                            continue;
                        }
                        if (sysProduct.getValue().getStatus().equals(option)) {
                            isExisted = true;
                            break;
                        }
                    }
                    if (!isExisted) {
                        deletedProductId.add(sysProduct.getKey());
                    }
                    isExisted = false;
                    if (noneCounter == sortedOptions.get(i).length) {
                        deletedProductId.clear();
                    }
                    noneCounter = 0;

                }

                for (String deletedId : deletedProductId) {
                    getSortedProducts().remove(deletedId);
                }
            }
        }
        if(orderOptions.get(0).isSelected()){
            sortByPrice();
        }else{
            sortByTitle();
        }
    }

}
