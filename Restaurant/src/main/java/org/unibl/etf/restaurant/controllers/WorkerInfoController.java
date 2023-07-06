package org.unibl.etf.restaurant.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class WorkerInfoController {
    @FXML
    public ListView<String> infoListView;
    @FXML
    private Label infoLabel;
    @FXML
    private Label numberLabel;

    @FXML
    private ListView<String> numberListView;

}
