package Store;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer extends Account {

    private static final String VALID_PASSWORD_REGEX = "(?=^.{8,}$)(?=(.*[^A-Za-z]){2,})^.*";
    private static final String VALID_EMAIL_REGEX = "^(.+)@(.+)$";
    private String address;
    private String name;
    public Customer(String userName, String password, String name, String address) {
        super(userName, password);
        this.name = name;
        this.address = address;

    }

    public static boolean validateEmail(String email) {
        String pattern = VALID_EMAIL_REGEX;

        if (email.matches(pattern)) {
            return true;
        } else
            return false;
    }

    public static void add(String username, String password, String address, String name) {

            Main.executeQueryforUpdate("INSERT INTO customer (userName, password,address,name) VALUES ('"
                    + username + "', '" + password + "','" + address + "','" + name + "')");


    }

    public static boolean validatePassword(String password) {
        String pattern = VALID_PASSWORD_REGEX;

        if (password.matches(pattern)) {
            return true;
        } else
            return false;
    }

    public static ObservableList<Customer> getListFromDB() {

        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        ResultSet rs = Main.executeQueryforRS("SELECT * FROM customer");
        Customer customer;
        try {
            while (rs.next()) {
                customer = new Customer(rs.getString("userName"), rs.getString("password"),
                                         rs.getString("name"), rs.getString("address"));
                customer.id = rs.getInt("id");
                customerList.add(customer);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerList;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String email) {
        setName(name);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
