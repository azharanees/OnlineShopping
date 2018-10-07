package Store;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.awt.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

public class PaymentController{



@FXML private TextField cardName;
@FXML private TextField cardNumber;
@FXML private TextField cvc;
@FXML private TextField exp;

    @FXML
    private TableView<Purchase> cartTable;
    @FXML private TableColumn<Purchase,String> itemCol;
    @FXML private TableColumn<Purchase,Integer> qtyCol;
    @FXML private TableColumn<Purchase,BigDecimal> costCol;
    @FXML private TableColumn<Purchase,BigDecimal> amountCol;
    @FXML private TableColumn<Purchase,String>sizeCol;


    @FXML private Label totalLabel;
    @FXML private Label invalid;

    public boolean validateCard() {
        String cardRegx = "\\d{13,16}";
        String cvcReg = "\\d{3}";
        String expReg = ".{4}";
        return !cardNumber.getText().isEmpty() && cardNumber.getText().matches(cardRegx) && cvc.getText().matches(cvcReg) && exp.getText().matches(expReg);
    }

    public void handleConfirmButton(ActionEvent event) throws IOException {
        if(validateCard()){
            invalid.setVisible(false);
            Desktop desktop = Desktop.getDesktop();
            String fileName = "C:\\Users\\Azhar\\IdeaProjects\\finalpp2\\JF0010202.pdf";
            Email.sendInvoice(Customer.getCustomer().userName.get(),fileName);
            File file = new File(fileName);
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Payment Successful, Click to continue to the invoice");
            alert.showAndWait();
            if(file.exists()) desktop.open(new File(fileName));
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.close();


        }else {
            invalid.setVisible(true);
        }


    }


    public void setTable(){


        itemCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        costCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("purchaseAmount"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        cartTable.setItems(Cart.getCartList());
totalLabel.setText(CustomerDashboard.getTotal());
    }

    public void initialize(){
        setTable();

    }

}
