package Store;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class CustomerDashboard {

    @FXML private ImageView mainImg;
    @FXML private ChoiceBox<String> productNameChoice;
    @FXML private ChoiceBox<String> productSizeChoice;
    @FXML private Spinner<Integer> productQty;
    @FXML private TableView<Purchase> cartTable;
    @FXML private TableColumn<Purchase,String> itemCol;
    @FXML private TableColumn<Purchase,Integer> qtyCol;
    @FXML private TableColumn<Purchase,BigDecimal> costCol;
    @FXML private TableColumn<Purchase,BigDecimal> amountCol;
    @FXML private Label username;
    @FXML private Label customerIDLabel;
    @FXML private Button checkoutBtn;
    @FXML private Button removeBtn;
    @FXML private TextArea enquiryMessage;

    @FXML private Label modifyNameLabel;
    @FXML private TextField modifyName;
    @FXML private TextField oldPassword;
    @FXML private TextField newPassword;
    @FXML private TextField modifyEmail;
    @FXML private Label modifyAddressLabel;
    @FXML private Label modifyEmailLabel;
    @FXML private TextArea modifyAddress;

    @FXML private TableView<Cart> purchaseHistory;
    @FXML private TableColumn<Cart,String> purchaseIdCol;
    @FXML private TableColumn<Cart,String> purchaseDayCol;
    @FXML private TableColumn<Cart, String> purchaseAmountCol;



@FXML private Button store_btn, history_btn, account_btn, enquiry_btn;

    @FXML private Pane store_pane,history_pane,account_pane,enquiry_pane;
    @FXML private Label totalLabel;
    private static String total;


    public void handleTabButtons(ActionEvent event) {
        if (event.getSource() == store_btn) {
            store_pane.toFront();
        } else if (event.getSource() == history_btn) {
            history_pane.toFront();
        } else if (event.getSource() == account_btn) {
            account_pane.toFront();
        } else if (event.getSource() == enquiry_btn) {
            enquiry_pane.toFront();
        }
    }


    public void setTable(){


          itemCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            costCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            amountCol.setCellValueFactory(new PropertyValueFactory<>("purchaseAmount"));
            cartTable.setItems(Cart.getCartList());


            ObservableList hist = Purchase.getListFromDB(Customer.getCustomer().getId()+"");
        purchaseDayCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        purchaseIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        purchaseAmountCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            purchaseHistory.setItems(Purchase.getListFromDB(Customer.getCustomer().getId()+""));
    }

    public void initialize(){
        setTable();

        removeBtn.setDisable(true);
      checkoutBtn.setDisable(true);
        getItems();
        setAccountPane();
        username.setText(Account.getUser());
    productNameChoice.setValue(productNameChoice.getItems().get(2));
    productSizeChoice.setValue(productSizeChoice.getItems().get(1));
    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        productQty.setValueFactory(valueFactory);
    }

    private void getItems(){
        List<String> list = new ArrayList<>();
        List<Product> l = Product.getListFromDB();
        for (Product p : l) {
            list.add(p.getName());//adding  product object to list
        }

        HashSet<String> nameList = new HashSet<>(list); //using hashset to filter duplicated products
        productNameChoice.getItems().addAll(nameList);  //adding all the items to combobox

        //Listening for changes and changing the list to new values
        productNameChoice.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue)->{
            getItemChoice(productNameChoice);
        });

    }



public void getItemChoice(ChoiceBox<String> productNameChoice) {
    String name = productNameChoice.getValue();
    List<Product> productList = Product.getListFromDB();

    for (Product p : productList) {
        productSizeChoice.getItems().removeAll(p.getSize());
        if (p.getName().equals(name)) {
            productSizeChoice.getItems().add(p.getSize());
        }
    }

}



    private Cart cart = new Cart();
    public void handleAddToCart(){

        checkoutBtn.setDisable(false);
        removeBtn.setDisable(false);
        List<Product> inventory = Product.getListFromDB();
        for (Product p:inventory) {
            if (productNameChoice.getValue().equals(p.getName()) && productSizeChoice.getValue().equals(p.getSize())) {
                int qty = (productQty.getValue());
                Purchase item = new Purchase(p, qty);
                Cart.addToCartList(item);
            }
        }
        totalLabel.setText(""+cart.getCartTotal());
        setTable();
    }
    public static String getTotal() {
        return total;
    }


    public void handleRemoveButton(){

if(cartTable.getSelectionModel().getSelectedItem()==null){
    return;
}
        cart.removeFromCart(cartTable.getSelectionModel().getSelectedIndex());
        setTable();
        totalLabel.setText(""+cart.getCartTotal());
        if(cart.getCartTotal().equals(new BigDecimal("0"))){
            checkoutBtn.setDisable(true);
            removeBtn.setDisable(true);
        }

    }

    public void handleCheckout() throws IOException {

            total=totalLabel.getText();
            cart.setPrice(total);
            cart.setDate(cart.getDate());
            cart.setId("00"+ Customer.getCustomer().getId());
            cart.setCartName(""+Customer.getCustomer().getId());
            cart.checkout();

        Parent dashboard = FXMLLoader.load(getClass().getResource("FXMLs/Payment.fxml"));
        Scene checkout = new Scene(dashboard);
        Stage window = new Stage();
        window.setScene(checkout);
        window.setTitle("Confirm Payment");
        window.show();


    }
    public void handleEnquiryButton(){
        Email.sendEnquiry(Customer.getCustomer().getName(),Customer.getCustomer().id.toString(),enquiryMessage.getText());
    }
    public void handleUpdateDetailsButton(){
    }
    public void setAccountPane(){
        customerIDLabel.setText(Customer.getCustomer().getId()+"");
        modifyName.setText(Customer.getCustomer().getName());
        modifyAddress.setText(Customer.getCustomer().getAddress());
        modifyEmail.setText(Customer.getCustomer().getUserName());
        modifyNameLabel.setText(modifyName.getText());
        modifyEmailLabel.setText(Customer.getCustomer().getUserName());
        modifyAddressLabel.setText(modifyAddress.getText());

    }
    public void modifyAccount(){
        String name = modifyName.getText();
        String address = modifyAddress.getText();
        String email = modifyEmail.getText();
        String id = Customer.getCustomer().getId().toString();
        String qry =  "UPDATE customer SET name = '"+name+"', address = '"+address+"' WHERE id = '"+id+"';";
        Main.executeQueryforUpdate(qry);
        modifyNameLabel.setText(modifyName.getText());
        modifyEmailLabel.setText(Customer.getCustomer().getUserName());
        modifyAddressLabel.setText(modifyAddress.getText());


    }

public void changePassword(){
        if(oldPassword.getText().equals(Customer.getCustomer().getPassword())){
            String pswrd = newPassword.getText();
            if(Customer.validatePassword(pswrd)){
                String qry =  "UPDATE customer SET password = '"+newPassword.getText()+"' WHERE id = '"
                        +Customer.getCustomer().getId()+"';";
                Main.executeQueryforUpdate(qry);

                Alert alert  = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Password successfully changed");
                alert.showAndWait();

            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Password must,\n#contain at least 2 non-alphabetic characters" +
                                "\n#be at least 8 characters");
                alert.setHeaderText("Password does not meet the criteria");
                alert.showAndWait();
            }

        }else {
            Alert alert  = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Your old password does not match");
            alert.showAndWait();
            oldPassword.setText("");
            newPassword.setText("");
        }
}

    public void handleImg1(){
    mainImg.setImage( new Image("Store/productPics/1.jpg"));
    productNameChoice.setValue("Fishing rod");


}
public void handleImg2(){
        mainImg.setImage( new Image("Store/productPics/2.jpg"));
    productNameChoice.setValue("Fishing Reel");

    }
    public void handleImg3(){
        mainImg.setImage( new Image("Store/productPics/3.jpg"));
        productNameChoice.setValue("Fishing Hook");
    }
    public void handleImg4(){
        mainImg.setImage( new Image("Store/productPics/4.jpg"));
        productNameChoice.setValue("Fishing Line");

    }
    public void handleImg5(){
        mainImg.setImage( new Image("Store/productPics/5.jpg"));
        productNameChoice.setValue("Sinkers");
    }
    public void handleImg6(){
        mainImg.setImage( new Image("Store/productPics/6.jpg"));
        productNameChoice.setValue("Swivels");

    }
    public void handleImg7(){
        mainImg.setImage( new Image("Store/productPics/7.jpg"));


    }    public void handleImg8(){;
        mainImg.setImage( new Image("Store/productPics/8.jpg"));

    }    public void handleImg9(){
     //   mainImg.setImage( new Image("Store/productPics/9.jpg"));

    }
    public void handleHomeLink(ActionEvent event) throws IOException {

        Parent register = FXMLLoader.load(getClass().getResource("FXMLs/login.fxml"));
        Scene registerScene = new Scene(register);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(registerScene);
        window.show();
    }


}
