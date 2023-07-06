package org.unibl.etf.restaurant.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.unibl.etf.restaurant.dao.OrderDAO;
import org.unibl.etf.restaurant.dao.mysql.OrderDAOImpl;
import org.unibl.etf.restaurant.exceptions.InvalidQuery;
import org.unibl.etf.restaurant.orders.Order;
import org.unibl.etf.restaurant.util.ControllerUtil;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ProcessOrderController implements Initializable {

    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private Button processButton;

    private Order order;

    private OrderDAO orderDAO;
    public ProcessOrderController(Order order) {
        this.order = order;
        orderDAO = new OrderDAOImpl();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeComboBox.setItems(FXCollections.observableArrayList(Arrays.asList("Gotovina","Kartica")));
    }

    @FXML
    void onProcessOrderButtonClick(ActionEvent event) {
        if(typeComboBox.getSelectionModel().getSelectedItem() == null){
            alert("Greska!", "Molimo vas unesite podatke");
            return;
        }
        try{
                String text = typeComboBox.getSelectionModel().getSelectedItem();
                boolean type;
                if ("Gotovina".equals(text))
                    type = true;
                else
                    type = false;
                if (!orderDAO.updateOrder(order, type)) {
                    ControllerUtil.showMessageBox(Alert.AlertType.ERROR, "Greška", "Nije moguće procesirati narudžbu");
                    throw new InvalidQuery("Order was not processed!");
                }

                OrdersController.orders.remove(order);
                OrdersController.orders.add(orderDAO.getOrderById(order.getId()));
                Stage stage = (Stage) processButton.getScene().getWindow();
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
