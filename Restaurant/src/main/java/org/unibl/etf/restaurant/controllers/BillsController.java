package org.unibl.etf.restaurant.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.unibl.etf.restaurant.dao.BillDAO;
import org.unibl.etf.restaurant.dao.mysql.BillDAOImpl;
import org.unibl.etf.restaurant.orders.Bill;
import org.unibl.etf.restaurant.util.ThreadPool;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class BillsController implements Initializable {
    @FXML
    private TableView<Bill> billsTable;

    @FXML
    private TableColumn<Bill, String> cashColumn;

    @FXML
    private TableColumn<Bill, String> dateTimeColumn;

    @FXML
    private TableColumn<Bill, Integer> idColumn;

    private ObservableList<Bill> bills;

    private BillDAO billDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        billDAO = new BillDAOImpl();
        ThreadPool.executeTask(() -> {
            bills = FXCollections.observableArrayList(billDAO.getBills());
            Platform.runLater(() -> {

                idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
                cashColumn.setCellValueFactory(new PropertyValueFactory<>("cash"));
                billsTable.setItems(bills);
            });
        });
        ThreadPool.shutdown();
    }
}
