package org.unibl.etf.restaurant.controllers;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import org.unibl.etf.restaurant.util.ControllerUtil;

import java.io.IOException;

public class MainController {
    @FXML
    public Button menuButton;

    @FXML
    void onMenuButtonClick(ActionEvent event) {
        try{
            ControllerUtil.optionButtonAction(event, "/fxml/menu.fxml", "Meni");
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void onHomeButtonClick(ActionEvent actionEvent)  {
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
}