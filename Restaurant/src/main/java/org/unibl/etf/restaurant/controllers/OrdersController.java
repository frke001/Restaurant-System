package org.unibl.etf.restaurant.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.unibl.etf.restaurant.MainApplication;
import org.unibl.etf.restaurant.dao.OrderDAO;
import org.unibl.etf.restaurant.dao.WorkerDAO;
import org.unibl.etf.restaurant.dao.mysql.OrderDAOImpl;
import org.unibl.etf.restaurant.dao.mysql.WorkerDAOImpl;
import org.unibl.etf.restaurant.exceptions.InvalidQuery;
import org.unibl.etf.restaurant.orders.Order;
import org.unibl.etf.restaurant.orders.OrderItem;
import org.unibl.etf.restaurant.util.ControllerUtil;
import org.unibl.etf.restaurant.util.ThreadPool;
import org.unibl.etf.restaurant.workers.*;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {
    @FXML
    private TableColumn<Order, String> dateTimeColumn;

    @FXML
    private Button homeButton;

    @FXML
    private Button menuButton;

    @FXML
    private TableColumn<Order, Integer> numberColumn;

    @FXML
    private Button orderButton;

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private TableColumn<Order, String> stateColumn;

    @FXML
    private Button workersButton;

    @FXML
    private Button billsButton;

    @FXML
    private Button processButton;

    private OrderDAO orderDAO;
    private WorkerDAO workerDAO;

    public void onMenuButtonClick(ActionEvent actionEvent) {
        try{
            ControllerUtil.optionButtonAction(actionEvent, "/fxml/menu.fxml", "Meni");
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void onHomeButtonClick(ActionEvent actionEvent) {
        try{
            ControllerUtil.optionButtonAction(actionEvent, "/fxml/main.fxml", "Meni");
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void onWorkersButtonClick(ActionEvent actionEvent) {
        try{
            ControllerUtil.optionButtonAction(actionEvent, "/fxml/workers.fxml", "Workers");
        }catch (IOException ex){
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void onOrderButtonClick(ActionEvent actionEvent) {
        try{
            ControllerUtil.optionButtonAction(actionEvent, "/fxml/orders.fxml", "Orders");
        }catch (IOException ex){
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
    public static ObservableList<Order> orders;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderDAO = new OrderDAOImpl();
        workerDAO = new WorkerDAOImpl();
        ThreadPool.executeTask(() -> {
            orders = FXCollections.observableArrayList(orderDAO.getOrders());

            Platform.runLater(() -> {
                numberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
                stateColumn.setCellValueFactory(new PropertyValueFactory<>("processed"));
                ordersTable.setItems(orders);

                ordersTable.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 1) {
                        ordersTable.getSelectionModel().setCellSelectionEnabled(true);
                        ordersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                    }
                    if (event.getClickCount() == 2) {
                        try {
                            Order selectedData = ordersTable.getSelectionModel().getSelectedItem();
                            Scene scene = ControllerUtil.newSceneButtonAction("/fxml/orderInfo.fxml", "Worker Info");
                            TableView<OrderItem> items = (TableView<OrderItem>) scene.lookup("#orderItemsTable");
                            TextField table = (TextField) scene.lookup("#tableTextField");
                            TextField waiter = (TextField) scene.lookup("#waiterTextField");
                            waiter.setText(workerDAO.getWorkerNameByJMB(selectedData.getWaiterJMB()));
                            table.setText(selectedData.getTableId().toString());
                            selectedData.setItems((ArrayList<OrderItem>) orderDAO.getOrderItems(selectedData.getId()));
                            items.setItems(FXCollections.observableArrayList(selectedData.getItems()));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            });
        });
        ThreadPool.shutdown();
    }

    public void onAddButtonClick(ActionEvent actionEvent) {
        try{
            ControllerUtil.newSceneButtonAction("/fxml/addOrder.fxml", "Add Order");
        }catch (IOException ex){
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void onBillsButtonClick(ActionEvent actionEvent) {
        try{
            ControllerUtil.newSceneButtonAction("/fxml/bills.fxml", "Bills");
        }catch (IOException ex){
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void onProcessOrderButtonClick(ActionEvent actionEvent) {
        Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        if(selectedOrder != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml/process.fxml"));
                ProcessOrderController processOrderController = new ProcessOrderController(selectedOrder);
                fxmlLoader.setController(processOrderController);
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Process Order");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
