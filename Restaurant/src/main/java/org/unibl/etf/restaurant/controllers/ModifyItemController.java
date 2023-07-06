package org.unibl.etf.restaurant.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.unibl.etf.restaurant.dao.MenuDAO;
import org.unibl.etf.restaurant.dao.mysql.MenuDAOImpl;
import org.unibl.etf.restaurant.exceptions.InvalidQuery;
import org.unibl.etf.restaurant.menu.Item;
import org.unibl.etf.restaurant.util.ControllerUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyItemController implements Initializable {

    Item item;
    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField priceTextField;
    @FXML
    private Button saveButton;

    private MenuDAO menuDAO;

    public ModifyItemController(Item item){
        this.item = item;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuDAO = new MenuDAOImpl();
        nameTextField.setText(item.getName());
        priceTextField.setText(item.getPrice().toString());
        descriptionTextArea.setText(item.getDescription());
    }

    @FXML
    void onSaveButtonClick(ActionEvent event) {
        MenuController.itemList.remove(item);
        if("".equals(nameTextField.getText()) || "".equals(priceTextField.getText()))
            alert("Greska", "Molimo vas unesite podatke");
        item.setName(nameTextField.getText());
        item.setDescription(descriptionTextArea.getText());
        item.setPrice(Double.parseDouble(priceTextField.getText()));
        try{
            if(!menuDAO.updateItem(item,1, true) && !menuDAO.updateItem(item,1, false)) {
                ControllerUtil.showMessageBox(Alert.AlertType.ERROR, "Greška", "Nije moguće ažurirati stavku");
                throw new InvalidQuery("Item was not updated!");
            }
            Platform.runLater(() -> {
                MenuController.itemList.add(item);
            });
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }catch (InvalidQuery ex){
            System.out.println(ex.getMessage());
        }
    }
    private void alert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
