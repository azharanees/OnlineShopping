package Store;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



import java.sql.*;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        //loading the source from the login fxml file
        Parent root = FXMLLoader.load(getClass().getResource("FXMLs/login.fxml"));
        primaryStage.setTitle("Jeff's Fishing shack");
        //Setting the stage style undecorated to use custom buttons
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image("Store/productPics/logo.jpg"));
        primaryStage.setResizable(false);
        primaryStage.show();

    }


//
    private  static final String CONN = "jdbc:mysql://localhost/fishing shack";
      private static final String USERNAME="root";
      private static final String PASSWORD="";

//

    public static Connection connectDB() {
        try {
            Connection con = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
            return con;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args){launch(args);}



    public static ResultSet executeQueryforRS(String statement){
        Connection connection;
        Statement st;
        ResultSet rs = null;
        try {
            connection = Main.connectDB();
            st = connection.createStatement();
            rs = st.executeQuery(statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;

    }

    public static void executeQueryforUpdate(String statement){
        Connection connection;
        Statement st;
        try {
            connection = Main.connectDB();
            st = connection.createStatement();
            st.executeUpdate(statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }



}
