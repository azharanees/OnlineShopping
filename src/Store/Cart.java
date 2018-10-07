package Store;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Cart {


    private static Purchase head;
    private String cartName;
    private String price;
    private String id ;
    public String getDate() {
        return date;
    }


    private String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());;

    public String getCartName() {

        return cartName;
    }



    public void setDate(String date) {
        this.date = date;
    }

    public void setCartName(String cartName) {
        this.cartName = cartName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }



    public String getPrice() {
        return price;
    }



    public void setPrice(String price){
        this.price = price;
    }

    public Cart(){

    }


    public static void addToCartList(Purchase purchase){
        if(head==null){
            head = purchase;
        }else {
            Purchase p = head;
            while (p.next!=null){
                p = p.next;
            }
            p.next = purchase;
        }

    }


    public void removeFromCart(int index){
        if(index==0){
            head = head.next;
        }else {
            Purchase p = head;
            Purchase delP = null;
            for (int i =1; i <index ; i++) {
                p = p.next;
            }

            delP = p.next;
            p.next = delP.next;

        }
    }


    public static ObservableList<Purchase> getCartList(){
        ObservableList<Purchase> cartList = FXCollections.observableArrayList();
        Purchase p = head;

        while (p!=null) {
            cartList.add(p);
            p = p.next;
        }
        return cartList;
    }


    public BigDecimal getCartTotal(){
        BigDecimal total=new BigDecimal(0);
        for (Purchase p :getCartList()) {
         total =total.add(p.getPurchaseAmount());

        }
        return total;
    }
    public void showCart(){
        Purchase purchase = head;
        while (purchase.next!=null){
            purchase = purchase.next;
        }

    }

    public  void checkout() {
            Cart c = this;
            Main.executeQueryforUpdate("INSERT INTO purchase ( id, customerID, date, price) VALUES ('"
                    +c.getId()+"','"+c.getCartName()+"','"+c.getDate()+"','"+c.getPrice()+"')");

        }

}





