package Store;

import javafx.beans.property.SimpleStringProperty;

import java.sql.*;

abstract public class Account {

    abstract public void setId(Integer id);

    public static Customer getCustomer() {
        return customer;
    }

    public String getUserName() {
        return userName.get();
    }



    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getPassword() {
        return password.get();
    }


    public void setPassword(String password) {
        this.password.set(password);
    }

    public Integer getId() {
        return id;
    }



    protected SimpleStringProperty userName, password;
    protected Integer id;
    private static String user = "";


    public Account(String userName, String password){

        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);

    }
        public static String getUser(){
        return user;
            }

private static Customer customer ;



public static String validateLogin(String username, String password) {
        String table;
if(username.isEmpty()||password.isEmpty()){
    return "Enter username AND password";
}else
        if(username.contains("@")){
            table = "customer";

        }else {
            table = "staff";

        }

        try (Connection con = Main.connectDB();
             Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = st.executeQuery("SELECT * FROM "+ table +" WHERE userName ='" + username + "' AND password = '" + password + "'");) {


            if (rs.next()) {
                user = rs.getString("userName");

                for (Customer c :Customer.getListFromDB()) {
                    if(user.equals(c.userName.getValue())) {
                        customer = c;
                        user = c.getName();
                        break;

                    }
                }

                return table;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);

        }

        return "Invalid Credentials !, If the problem persist check your connection and contact a staff";
        }

}


