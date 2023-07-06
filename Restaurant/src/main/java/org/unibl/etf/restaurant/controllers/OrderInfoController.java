package org.unibl.etf.restaurant.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.unibl.etf.restaurant.menu.Item;
import org.unibl.etf.restaurant.orders.OrderItem;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderInfoController implements Initializable {
    @FXML
    private TableColumn<OrderItem, String> foodDrinkColumn;

    @FXML
    private TableColumn<OrderItem, Integer> idColumn;

    @FXML
    private TableView<OrderItem> orderItemsTable;

    @FXML
    private TableColumn<OrderItem, Integer> quantityColumn;
    @FXML
    private TextField tableTextField;

    @FXML
    private TextField waiterTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        foodDrinkColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }
}
