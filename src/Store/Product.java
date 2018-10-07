package Store;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;


 class Product {


    private String id;
    private String code;
    private String name;
    private BigDecimal price;
    private String size;
    public String getId() {
        return id;
    }
    public String getCode() {
        return code;
    }

    public void setPrice(BigDecimal price) {

            this.price = price;
    }


    public Product( String code,String name, BigDecimal price,String size){
        this.name = name;
        this.price = price;
        this.size = size;
        this.code = code;
    }





    public void setCode(String code) {
        this.code = code;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }






    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setName(String name){
        this.name = name;
    }

    public static ObservableList<Product> getListFromDB() {

        ObservableList<Product> productList = FXCollections.observableArrayList();
        ResultSet rs = Main.executeQueryforRS("SELECT * FROM products");

        Product product;
        try {
            while (rs.next()){

                product = new Product(rs.getString("code"),rs.getString("name"),rs.getBigDecimal("price"),rs.getString("size"));
                product.id = rs.getString("id2");
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return productList;
    }


    public static void add( String code, String name,BigDecimal price,String size){

        Product p = new Product(code, name,price,size);
        ResultSet rs = Main.executeQueryforRS("SELECT * FROM products");
        try {
            p.id = rs.getString("id2");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getListFromDB().add(p);
        Main.executeQueryforUpdate("INSERT INTO products ( code, name, price, size) VALUES ('" +code+"','"+name+"','"+price+"','"+size+"')");


    }
    public static void removeFromDB(Product product){
        String id = product.id;
        Main.executeQueryforUpdate(" DELETE FROM products WHERE id2="+"'"+id+"';");

        getListFromDB().remove(product);

    }



}
