package Store;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.awt.Desktop;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Store.Product;

public class Admin extends Staff {

    public Admin(String userName, String password){
        super(userName, password);
    }


public static void viewReport(String customerID) throws IOException {

    PrintWriter pw = new PrintWriter(new FileOutputStream("report.txt"));
    for (Cart c :Purchase.getListFromDB(customerID) ) {
        pw.println("Order ID - " + c.getId() + "\tCustomer ID - " + c.getCartName() + "   Date - " + c.getDate() + "\tAmount - " + c.getPrice());
        pw.flush();
    }
    pw.close();
    Desktop desktop = Desktop.getDesktop();
    File file = new File("C:\\Users\\Azhar\\IdeaProjects\\finalpp2\\report.txt");
    if(file.exists()) desktop.open(file);//This opens the report.txt in the default editor


}

    public static void addStaff(String username, String password){
        Staff s = new Staff(username, password);
        Main.executeQueryforUpdate("INSERT INTO staff (userName, password) VALUES ('"+username+"', '"+password+"')");

    }

    public static void removeStaffFromDB(Staff staff){
    int id = staff.id;
    Main.executeQueryforUpdate(" DELETE FROM staff WHERE id="+id);

}

    public static ObservableList<Staff> getStaffListFromDB(){
         ObservableList<Staff> staffList = FXCollections.observableArrayList();
        ResultSet rs = Main.executeQueryforRS("SELECT * FROM staff");
        Statement st;

            Staff staff;
        try {
            while (rs.next()){

                staff = new Staff(rs.getString("userName"),rs.getString("password"));
                staff.id = rs.getInt("id");
                staffList.add(staff);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return staffList;
    }
}
