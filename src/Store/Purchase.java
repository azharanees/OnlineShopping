package Store;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Purchase {


    public Product getItem() {
        return item;
    }

    private Product item;
    private String date;
    public Purchase(Product item, int quantity) {
        this.item = item;
        this.quantity = quantity;
        this.purchaseAmount = item.getPrice().multiply(new BigDecimal(quantity));
        this.date = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
    }



    private SimpleStringProperty name;


    public BigDecimal getPrice() {
        return item.getPrice();
    }

    public String getSize() {

        return item.getSize();
    }

    public String getName() {
        return item.getName();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private int quantity;

    private BigDecimal purchaseAmount;
    public Purchase next;




    //public String nameProperty(){
//        return this.name;
//}
    public String getInvoice() {
        //This will return relevant invoice detail
        return String.valueOf(this.purchaseAmount);
    }

    public String getReceipt() {
        //This will return relevant receipt details
        return "Receipt";
    }


    public String getDate() {
        return date;
    }


    //This method returns an observable list of purchases stored in the database
    public static ObservableList<Cart> getListFromDB() {
        ObservableList<Cart> purchases = FXCollections.observableArrayList();
        ResultSet rs = Main.executeQueryforRS("SELECT * FROM purchase");//Getting data from database
        Cart cart;
        try {
            while (rs.next()) {
                cart = new Cart();
                cart.setId(rs.getString("orderID"));
                cart.setCartName(rs.getString("customerID"));
                cart.setDate(rs.getString("date"));
                cart.setPrice(rs.getString("price"));
                purchases.add(cart); //Adding the cart object to the list
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return purchases;
    }
public static ObservableList<Cart>getListFromDB(boolean date,String day ){
          ObservableList<Cart> purchases = FXCollections.observableArrayList();
          ResultSet rs = Main.executeQueryforRS("SELECT * FROM purchase WHERE date  LIKE '%" + day + "%'");
          Cart cart;
          try {
              while (rs.next()) {

                  cart = new Cart();
                  cart.setId(rs.getString("orderID"));
                  cart.setCartName(rs.getString("customerID"));
                  cart.setDate(rs.getString("date"));
                  cart.setPrice(rs.getString("price"));
                  purchases.add(cart);
              }
          } catch (SQLException e) {
              e.printStackTrace();
          }

    return purchases;
}

    public static ObservableList<Cart> getListFromDB(String customerID) {
        ObservableList<Cart> fullList = getListFromDB();//Getting all the data from database & storing it

        //handling when admin enters a date separated by a '/' to return the purchases made on that specific date
        if (customerID.contains("/")) {
            fullList = getListFromDB(true,customerID);
            return fullList;
        //If admin request for the complete list
        } else {
            if (customerID.equalsIgnoreCase("all")) {
                return fullList;
            }
        //If admin wants to get list of purchases by customerID
            ObservableList<Cart> newList = FXCollections.observableArrayList();
            int left = 0, right = newList.size() - 1; //Variables need to binary search
        //Sorting the list according to the customer id
            Cart temp; //temporary variable to store selected variable when sorting
            for (int i = 0; i < fullList.size(); i++) {
        //setting the minimum value to the i element's id
                int minValue  = Integer.valueOf(fullList.get(i).getId());
                int minIndex = i;
                for (int j = i; j < fullList.size(); j++) {
                    if (Integer.valueOf(fullList.get(j).getId()) < minValue) {
                        minValue = Integer.valueOf(fullList.get(j).getId());
                        minIndex = j;
                    }
                }
                //Completing the sorting by swapping the objects in the list
                if(minValue<Integer.valueOf(fullList.get(i).getId())){
                    temp = (fullList.get(i));
                    fullList.set(i,fullList.get(minIndex));
                    fullList.set(minIndex,temp);
                }
            }

            //Searching thr the list which was sorted
            //making customer id as the key value to search for
            for (Cart c : fullList) {

                int mid = (left + right / 2);

                if (c.getCartName().equals(customerID)) {
                    newList.add(c); //if the object of that customer id is found adding it a new list

                } else if (Integer.valueOf(c.getId()) < Integer.valueOf(c.getId())) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            return newList; //returning the new list containing only the keys
        }
    }

}
