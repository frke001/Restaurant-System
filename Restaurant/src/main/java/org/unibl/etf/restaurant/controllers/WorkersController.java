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
import org.unibl.etf.restaurant.dao.WorkerDAO;
import org.unibl.etf.restaurant.dao.mysql.WorkerDAOImpl;
import org.unibl.etf.restaurant.exceptions.InvalidQuery;
import org.unibl.etf.restaurant.util.ControllerUtil;
import org.unibl.etf.restaurant.util.ThreadPool;
import org.unibl.etf.restaurant.workers.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WorkersController implements Initializable {


    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button menuButton;

    @FXML
    private Button modifyButton;

    @FXML
    private TableColumn<Worker, String> JMBColumn;

    @FXML
    private TableColumn<Worker, String> addressColumn;
    @FXML
    private TableColumn<Worker, String> nameColumn;

    @FXML
    private TableColumn<Worker, Integer> paymentColumn;

    @FXML
    private TableColumn<Worker, String> surnameColumn;

    @FXML
    private TableColumn<Worker, String> typeColumn;
    @FXML
    private Button workersButton;

    @FXML
    private TableView<Worker> workersTable;

    public static ObservableList<Worker> workerList;
    private WorkerDAO workerDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        workerDAO = new WorkerDAOImpl();
        ThreadPool.executeTask(() -> {
            workerList = FXCollections.observableArrayList(workerDAO.getWorkers(1));

            Platform.runLater(() -> {
                JMBColumn.setCellValueFactory(new PropertyValueFactory<>("JMB"));
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
                addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
                paymentColumn.setCellValueFactory(new PropertyValueFactory<>("payment"));
                typeColumn.setCellValueFactory(new PropertyValueFactory<>("workerType"));

                workersTable.setItems(workerList);

                workersTable.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 1) {
                        workersTable.getSelectionModel().setCellSelectionEnabled(true);
                        workersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                    }
                    if (event.getClickCount() == 2) {
                        Worker selectedData = workersTable.getSelectionModel().getSelectedItem();
                        try {
                            if (selectedData.getWorkerType().equals("Konobar")) {
                                List<String> languages = workerDAO.getWaiterLanguages(selectedData.getJMB());
                                List<String> numbers = workerDAO.getPhoneNumbers(selectedData.getJMB());
                                info("Strani jezici:", languages,numbers);
                            } else if (selectedData.getWorkerType().equals("Kuvar")) {
                                List<String> numbers = workerDAO.getPhoneNumbers(selectedData.getJMB());
                                List<String> specializations = workerDAO.getCookSpecializations(selectedData.getJMB());
                                info("Specijalizacije jela:", specializations,numbers);
                            } else if (selectedData.getWorkerType().equals("Menadžer")) {
                                List<String> numbers = workerDAO.getPhoneNumbers(selectedData.getJMB());
                                List<String> obligations = workerDAO.getManagerObligations(selectedData.getJMB());
                                info("Radne obaveze:", obligations,numbers);
                            } else {
                                Scene scene = ControllerUtil.newSceneButtonAction("/fxml/bartenderInfo.fxml", "Worker Info");
                                Label info = (Label) scene.lookup("#infoLabel");
                                ListView<String> PhoneNumbers = (ListView<String>) scene.lookup("#numberListView");
                                if (workerDAO.getCocktailExperience(selectedData.getJMB()))
                                    info.setText("Šanker ima iskustvo sa koktelima");
                                else
                                    info.setText("Šanker nema iskustvo sa koktelima");
                                List<String> numbers = workerDAO.getPhoneNumbers(selectedData.getJMB());
                                PhoneNumbers.setItems(FXCollections.observableArrayList(numbers));
                            }

                        } catch (IOException ex) {
                            System.err.println(ex.getMessage());
                            ex.printStackTrace();
                        }

                    }
                });
            });
        });
        ThreadPool.shutdown();
    }

    private void info(String title, List<String> info, List<String> phoneNumbers) throws IOException {
        Scene scene = ControllerUtil.newSceneButtonAction("/fxml/workerInfo.fxml", "Worker Info");
        ListView<String> infoList = (ListView<String>) scene.lookup("#infoListView");
        ListView<String> numbers = (ListView<String>) scene.lookup("#numberListView");
        infoList.setItems(FXCollections.observableArrayList(info));
        numbers.setItems(FXCollections.observableArrayList(phoneNumbers));
        Label infoLabel = (Label) scene.lookup("#infoLabel");
        infoLabel.setText(title);

    }

    public void onAddButtonClick(ActionEvent actionEvent) {
        try {
            ControllerUtil.newSceneButtonAction("/fxml/addWorker.fxml", "Add Worker");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void onModifyButtonClick(ActionEvent actionEvent) {
        try {
            Worker selectedWorker = workersTable.getSelectionModel().getSelectedItem();
            if (selectedWorker != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml/modifyWorker.fxml"));
                ModifyWorkerController modifyWorkerController = new ModifyWorkerController(selectedWorker);
                fxmlLoader.setController(modifyWorkerController);
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Modify Worker");
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void onDeleteButtonClick(ActionEvent actionEvent) {
        Worker selectedItem = workersTable.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            try{
                if(!workerDAO.deleteWorker(selectedItem,1)) {
                    ControllerUtil.showMessageBox(Alert.AlertType.ERROR, "Greška", "Nije moguće obrisati radnika");
                    throw new InvalidQuery("Worker was not deleted!");
                }
                workerList.remove(selectedItem);
            }catch (InvalidQuery ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    public void onMenuButtonClick(ActionEvent actionEvent) {
        try {
            ControllerUtil.optionButtonAction(actionEvent, "/fxml/menu.fxml", "Menu");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void onHomeButtonClick(ActionEvent actionEvent) {
        try {
            ControllerUtil.optionButtonAction(actionEvent, "/fxml/main.fxml", "Welcome");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void onWorkersButtonClick(ActionEvent actionEvent) {
        try {
            ControllerUtil.optionButtonAction(actionEvent, "/fxml/workers.fxml", "Workers");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }


    public void onOrderButtonClick(ActionEvent actionEvent) {
        try {
            ControllerUtil.optionButtonAction(actionEvent, "/fxml/orders.fxml", "Orders");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

}
