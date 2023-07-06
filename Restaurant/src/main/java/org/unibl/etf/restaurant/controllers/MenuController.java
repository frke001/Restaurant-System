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
import org.unibl.etf.restaurant.dao.MenuDAO;
import org.unibl.etf.restaurant.dao.mysql.MenuDAOImpl;
import org.unibl.etf.restaurant.exceptions.InvalidQuery;
import org.unibl.etf.restaurant.menu.Drink;
import org.unibl.etf.restaurant.menu.Food;
import org.unibl.etf.restaurant.menu.Item;
import org.unibl.etf.restaurant.menu.Menu;
import org.unibl.etf.restaurant.util.ControllerUtil;
import org.unibl.etf.restaurant.util.ThreadPool;


import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;
    @FXML
    private Button modifyButton;

    @FXML
    private TableView<Item> menuTable;
    @FXML
    private Button menuButton;
    @FXML
    private TableColumn<Item, String> descriptionColumn;

    @FXML
    private TableColumn<Item, Integer> idColumn;
    @FXML
    private TableColumn<Item, String> nameColumn;

    @FXML
    private TableColumn<Item, BigDecimal> priceColumn;
    @FXML
    private Label menuDateLabel;

    @FXML
    private Label menuIdLabel;

    private MenuDAO menuDAO;

    public static ObservableList<Item> itemList;
    private static final Integer menuId = 1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuDAO = new MenuDAOImpl();
            ThreadPool.executeTask(() -> {

                itemList = FXCollections.observableArrayList(menuDAO.getItems(1));
                Platform.runLater(() -> {
                    idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name")); // ime propertia
                    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
                    priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


                    menuTable.getItems().clear();
                    menuTable.setItems(itemList);

                    menuTable.setOnMouseClicked(event -> {
                        if (event.getClickCount() == 1) {
                            menuTable.getSelectionModel().setCellSelectionEnabled(true);
                            menuTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                        }
                    });
                });
            });

            ThreadPool.shutdown();
        //}
    }
    @FXML
    void onAddButtonClick(ActionEvent event) {
        try {
            ControllerUtil.newSceneButtonAction("/fxml/addItem.fxml", "Add Item");
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    void onDeleteButtonClick(ActionEvent event) {
        Item selectedItem = menuTable.getSelectionModel().getSelectedItem();
        try {
//            if((selectedItem.getDescription().contains("(Posno)") || selectedItem.getDescription().contains("(Nije posno)"))) {
//                if(!menuDAO.deleteItem(selectedItem, 1, true)) {
//                    ControllerUtil.showMessageBox(Alert.AlertType.ERROR, "Greška", "Nije moguće obrisati jelo");
//                    throw new InvalidQuery("Item was not deleted");
//                }
//            }
//            if((selectedItem.getDescription().contains("(Alkoholno)") || selectedItem.getDescription().contains("(Bezalkoholno)"))) {
//                if(!menuDAO.deleteItem(selectedItem, 1, false)) {
//                    ControllerUtil.showMessageBox(Alert.AlertType.ERROR, "Greška", "Nije moguće obrisati piće");
//                    throw new InvalidQuery("Item was not deleted");
//                }
//            }
            if(!menuDAO.deleteItem(selectedItem,1, true) && !menuDAO.deleteItem(selectedItem,1, false)) {
                ControllerUtil.showMessageBox(Alert.AlertType.ERROR, "Greška", "Nije moguće obrisati stavku");
                throw new InvalidQuery("Item was not updated!");
            }

            itemList.remove(selectedItem);
        }catch (InvalidQuery ex){
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void onModifyButtonClick(ActionEvent event) {

        try {
            Item selectedItem = menuTable.getSelectionModel().getSelectedItem();
            if(selectedItem !=null) {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml/modifyItem.fxml"));
                ModifyItemController modifyItemController = new ModifyItemController(selectedItem);
                fxmlLoader.setController(modifyItemController);
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Modify Item");
                stage.setScene(scene);
                stage.show();
            }
        }catch (IOException ex){
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void onMenuButtonClick(ActionEvent actionEvent) {
        try{
            ControllerUtil.optionButtonAction(actionEvent, "/fxml/menu.fxml", "Meni");
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void onHomeButtonClick(ActionEvent actionEvent) throws IOException {
        try{
            ControllerUtil.optionButtonAction(actionEvent, "/fxml/main.fxml", "Welcome");
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void onWorkersButtonClick(ActionEvent actionEvent) {
        try{
            ControllerUtil.optionButtonAction(actionEvent, "/fxml/workers.fxml", "Workers");
        }catch (IOException ex){
            System.err.println(ex.getMessage());
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
}
