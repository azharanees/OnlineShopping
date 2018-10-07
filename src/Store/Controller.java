package Store;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;

public class Controller {

    @FXML private AnchorPane ap;

    @FXML private TextField userName;
    @FXML private TextField pass;
    @FXML private TextArea enquiryMessage;
    @FXML private TextField enquiryName;
    @FXML private TextField enquiryEmail;
    @FXML private Label invalidLabel;


    public void handleEnquiryLink() throws IOException {
        Parent enquiry = FXMLLoader.load(getClass().getResource("FXMLs/Enquiry.fxml"));
        Scene enquiryScene = new Scene(enquiry);
        Stage window = new Stage();
        window.setTitle("Enquiries");
        window.getIcons().add(new Image("Store/productPics/logo.jpg"));
        window.setScene(enquiryScene);
        window.show();
    }

    public void handleContactUsLink(){

        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Email - jeffsfishingshack1@gmail.com\nPhone-+9477880 4522");
        alert.setHeaderText("Contact Us");
        alert.showAndWait();
    }
    public void handleEnquirySubmit(ActionEvent event){
        Email.sendEnquiry(enquiryName.getText(),enquiryEmail.getText(),enquiryMessage.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"We'll get back to you shortly");
        alert.setHeaderText("Thank you");
        alert.showAndWait();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setResizable(false);
    window.close();
    }

    @FXML public void onEnter(ActionEvent event) throws IOException {
        handleLoginButton(event);
    }


    public void handleCloseButton(){
        Stage stage = (Stage) ap.getScene().getWindow();
        stage.close();
    }
    public void changeScene(ActionEvent event, String fxml) throws IOException {
        Parent dashboard = FXMLLoader.load(getClass().getResource(fxml));
        Scene dashboardScene = new Scene(dashboard);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(dashboardScene);
//        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
//        window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
//        window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);
        window.show();
    }

    public void handleRegisterButton()throws IOException{
        Parent register = FXMLLoader.load(getClass().getResource("FXMLs/Register.fxml"));
        Scene registerScene = new Scene(register);
        Stage window = new Stage();
        window.setScene(registerScene);
        window.setTitle("Registration");
        window.getIcons().add(new Image("Store/productPics/logo.jpg"));
        window.show();
    }
@FXML private ImageView loginIcon;
    public void handleLoginButton(ActionEvent event) throws IOException{
        loginIcon.setVisible(true);

        String userName = this.userName.getText();
        String pass = this.pass.getText();
        String validation =  Account.validateLogin(userName,pass);
        switch (validation) {
            case "staff":{
               changeScene(event,"FXMLs/Dashboard.fxml");
                break;
            }
            case "customer": {
              changeScene(event,"FXMLs/CustomerDashboard.fxml");
                break;
            }
            default:
                invalidLabel.setVisible(true);
                Alert alert = new Alert(Alert.AlertType.ERROR, validation);
                alert.setHeaderText("Connection Failure");
                alert.showAndWait();
                break;
        }

    }


}
