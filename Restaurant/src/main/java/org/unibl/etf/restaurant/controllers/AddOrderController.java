package org.unibl.etf.restaurant.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.unibl.etf.restaurant.dao.MenuDAO;
import org.unibl.etf.restaurant.dao.OrderDAO;
import org.unibl.etf.restaurant.dao.TableDAO;
import org.unibl.etf.restaurant.dao.WorkerDAO;
import org.unibl.etf.restaurant.dao.mysql.MenuDAOImpl;
import org.unibl.etf.restaurant.dao.mysql.OrderDAOImpl;
import org.unibl.etf.restaurant.dao.mysql.TableDAOImpl;
import org.unibl.etf.restaurant.dao.mysql.WorkerDAOImpl;
import org.unibl.etf.restaurant.exceptions.InvalidQuery;
import org.unibl.etf.restaurant.menu.Item;
import org.unibl.etf.restaurant.orders.Order;
import org.unibl.etf.restaurant.orders.OrderItem;
import org.unibl.etf.restaurant.orders.OrderItemTemporary;
import org.unibl.etf.restaurant.util.ControllerUtil;
import org.unibl.etf.restaurant.util.ThreadPool;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddOrderController implements Initializable {
    @FXML
    private ComboBox<Integer> tableComboBox;

    @FXML
    private ComboBox<String> waiterComboBox;

    @FXML
    private Button addOrderItemButton;

    @FXML
    private TableColumn<OrderItemTemporary, String> foodDrinkColumn;

    @FXML
    private ComboBox<String> foodDrinkComboBox;


    @FXML
    private TableView<OrderItemTemporary> orderItemsTable;

    @FXML
    private TableColumn<OrderItemTemporary, Integer> quantityColumn;

//    @FXML
//    private Button removeOrderItemButton;

    @FXML
    private Button saveButton;
    @FXML
    private TextField quantityTextField;


    ObservableList<OrderItemTemporary> orderItems;

    private MenuDAO menuDAO;
    private WorkerDAO workerDAO;
    private TableDAO tableDAO;

    private OrderDAO orderDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuDAO = new MenuDAOImpl();
        workerDAO = new WorkerDAOImpl();
        tableDAO = new TableDAOImpl();
        orderDAO = new OrderDAOImpl();
        ThreadPool.executeTask(() -> {
            List<Item> items = menuDAO.getItems(1);
            Platform.runLater(() -> {
                orderItems = FXCollections.observableArrayList();
                foodDrinkColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                orderItemsTable.setItems(orderItems);
                List<String> itemNames = new ArrayList<>();
                for(var el : items){
                    itemNames.add(el.getName());
                }
                foodDrinkComboBox.setItems(FXCollections.observableArrayList(itemNames));
                tableComboBox.setItems(FXCollections.observableArrayList(tableDAO.getTableIds(1)));
                waiterComboBox.setItems(FXCollections.observableArrayList(workerDAO.getWaiterNames(1)));
            });
        });
        ThreadPool.shutdown();

    }

    @FXML
    void onAddOrderItemButton(ActionEvent event) {
        if(foodDrinkComboBox.getSelectionModel().getSelectedItem() == null || "".equals(quantityTextField.getText())){
            alert("Greska!", "Molimo vas unesite podatke");
            return;
        }
        try {
            String foodDrinkName = foodDrinkComboBox.getSelectionModel().getSelectedItem();
            Integer quantity = Integer.parseInt(quantityTextField.getText());
            orderItemsTable.getItems().add(new OrderItemTemporary(quantity,foodDrinkName));
            foodDrinkComboBox.getSelectionModel().clearSelection();
            quantityTextField.clear();
        }catch (NumberFormatException ex){
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    void onSaveButton(ActionEvent event) {
        if(waiterComboBox.getSelectionModel().getSelectedItem() == null || tableComboBox.getSelectionModel().getSelectedItem() == null){
            alert("Greska!", "Molimo vas unesite podatke");
            return;
        }
        try {
            String waiter = waiterComboBox.getSelectionModel().getSelectedItem();
            Integer tableId = tableComboBox.getSelectionModel().getSelectedItem();

            Integer orderId = orderDAO.addOrder(new Order(false,tableId,waiter));
            if(orderId == null){
                ControllerUtil.showMessageBox(Alert.AlertType.ERROR, "Greška", "Nije moguće dodati narudžbu");
                throw new InvalidQuery("Order was not added!");
            }

            for(var el : orderItemsTable.getItems()){
                if(orderDAO.addOrderItem(new OrderItem(el.getQuantity(),orderId,el.getName()))){
                    ControllerUtil.showMessageBox(Alert.AlertType.ERROR, "Greška", "Nije moguće dodati stavku narudžbe");
                    throw new InvalidQuery("Order Item was not added!");
                }

            }

            OrdersController.orders.add(orderDAO.getOrderById(orderId));

            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }catch (NumberFormatException ex){
            System.out.println(ex.getMessage());
        } catch (InvalidQuery ex) {
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
