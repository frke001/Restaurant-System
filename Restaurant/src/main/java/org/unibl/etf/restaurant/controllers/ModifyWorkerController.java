package org.unibl.etf.restaurant.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.unibl.etf.restaurant.dao.WorkerDAO;
import org.unibl.etf.restaurant.dao.mysql.WorkerDAOImpl;
import org.unibl.etf.restaurant.exceptions.InvalidQuery;
import org.unibl.etf.restaurant.util.ControllerUtil;
import org.unibl.etf.restaurant.workers.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyWorkerController implements Initializable {


    @FXML
    private Button addLanguageButton;

    @FXML
    private Button addObligationButton;

    @FXML
    private Button addPhoneNumButton;

    @FXML
    private Button addSpecializationButton;

    @FXML
    private Button getButton;

    @FXML
    private ListView<String> languagesListView;

    @FXML
    private ListView<String> obligationListView;

    @FXML
    private ListView<String> phoneNumberListView;

    @FXML
    private Button removeLanguageButton;

    @FXML
    private Button removeObligationButton;

    @FXML
    private Button removePhoneNumButton;

    @FXML
    private Button removeSpecializationButton;

    @FXML
    private Button saveButton;

    @FXML
    private ListView<String> specializationListView;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField paymentTextField;

    private Worker worker;
    private WorkerDAO workerDAO;

    public ModifyWorkerController(Worker worker) {
        this.worker = worker;
        workerDAO = new WorkerDAOImpl();
    }

    private TextInputDialog addDialog(String title, String content) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(content);
        return dialog;
    }

    private TextInputDialog removeDialog(String title, String content) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(content);
        return dialog;
    }

    @FXML
    void onAddPhoneNumButton(ActionEvent event) {
        String item = null;
        TextInputDialog dialog = addDialog("Dodaj broj telefona", "Unesite novi broj telefona:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newItem ->
                {
                    String phoneNumber = newItem;
                    if(!phoneNumber.startsWith("+387"))
                        phoneNumber="+387 (0)" + phoneNumber.substring(1,phoneNumber.length());
                    phoneNumberListView.getItems().add(phoneNumber);
                    if (!workerDAO.addPhoneNumber(worker.getJMB(), phoneNumber)) {
                        try {
                            throw new InvalidQuery("Phone number was not added!");
                        } catch (InvalidQuery ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
        );

    }

    @FXML
    void onRemovePhoneNumButton(ActionEvent event) {
        TextInputDialog dialog = removeDialog("Ukloni broj telefona", "Unesite broj telefona za brisanje:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newItem ->
        {
            phoneNumberListView.getItems().remove(newItem);
            if (!workerDAO.deletePhoneNumber(worker.getJMB(), newItem)) {
                try {
                    throw new InvalidQuery("Phone number was not deleted!");
                } catch (InvalidQuery ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    @FXML
    void onRemoveSpecializationButton(ActionEvent event) {
        TextInputDialog dialog = removeDialog("Ukloni specijalizaciju", "Unesite specijalizaciju za brisanje:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newItem -> {
            specializationListView.getItems().remove(newItem);
            if (!workerDAO.deleteSpecialization(worker.getJMB(), newItem)) {
                try {
                    throw new InvalidQuery("Specialization was not deleted!");
                } catch (InvalidQuery ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    @FXML
    void onAddSpecializationButton(ActionEvent event) {
        TextInputDialog dialog = addDialog("Dodaj specijalizaciju", "Unesite novu specijalizaciju:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newItem -> {
            specializationListView.getItems().add(newItem);
            if (!workerDAO.addSpecialization(worker.getJMB(), newItem)) {
                try {
                    throw new InvalidQuery("Specialization was not added!");
                } catch (InvalidQuery ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    @FXML
    void onRemoveLanguageButton(ActionEvent event) {
        TextInputDialog dialog = removeDialog("Ukloni jezik", "Unesite jezik za brisanje:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newItem -> {
            languagesListView.getItems().remove(newItem);
            if (!workerDAO.deleteLanguage(worker.getJMB(), newItem)) {
                try {
                    throw new InvalidQuery("Language was not deleted!");
                } catch (InvalidQuery ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    @FXML
    void onAddLanguageButton(ActionEvent event) {
        TextInputDialog dialog = addDialog("Dodaj jezik", "Unesite novi jezik:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newItem -> {
            languagesListView.getItems().add(newItem);
            if (!workerDAO.addLanguage(worker.getJMB(), newItem)) {
                try {
                    throw new InvalidQuery("Language was not added!");
                } catch (InvalidQuery ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    @FXML
    void onRemoveObligationButton(ActionEvent event) {
        TextInputDialog dialog = removeDialog("Ukloni radnu obavezu", "Unesite radnu obavezu za brisanje:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newItem -> {
            obligationListView.getItems().remove(newItem);
            if (!workerDAO.deleteObligation(worker.getJMB(), newItem)) {
                try {
                    throw new InvalidQuery("Obligation was not deleted!");
                } catch (InvalidQuery ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    @FXML
    void onAddObligationButton(ActionEvent event) {
        TextInputDialog dialog = addDialog("Dodaj radnu obavezu", "Unesite novu radnu obavezu:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newItem -> {
            obligationListView.getItems().add(newItem);
            if (!workerDAO.addObligation(worker.getJMB(), newItem)) {
                try {
                    throw new InvalidQuery("Obligation was not added!");
                }catch (InvalidQuery ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(worker.getJMB());
        worker.setPhoneNumbers((ArrayList<String>) workerDAO.getPhoneNumbers(worker.getJMB()));
        paymentTextField.setText(worker.getPayment().toString());
        addressTextField.setText(worker.getAddress());
        phoneNumberListView.setItems(FXCollections.observableArrayList(worker.getPhoneNumbers()));

        if (worker.getWorkerType().equals("Konobar")) {
            //Waiter waiter = new Waiter(worker.getJMB(),worker.getName(),worker.get)
            Waiter waiter = (Waiter) worker;
            waiter.setLanguages((ArrayList<String>) workerDAO.getWaiterLanguages(worker.getJMB()));
            languagesListView.setItems(FXCollections.observableArrayList(waiter.getLanguages()));

        } else if (worker.getWorkerType().equals("Menadžer")) {
            Manager manager = (Manager) worker;
            manager.setWorkObligation((ArrayList<String>) workerDAO.getManagerObligations(worker.getJMB()));
            obligationListView.setItems(FXCollections.observableArrayList(manager.getWorkObligation()));
        } else if (worker.getWorkerType().equals("Kuvar")) {
            Cook cook = (Cook) worker;
            cook.setFoodSpecialization((ArrayList<String>) workerDAO.getCookSpecializations(worker.getJMB()));
            specializationListView.setItems(FXCollections.observableArrayList(cook.getFoodSpecialization()));
        }
    }

    @FXML
    void onSaveButton(ActionEvent event) {
        try {
            WorkersController.workerList.remove(worker);
            worker.setPayment(Integer.parseInt(paymentTextField.getText()));
            worker.setAddress(addressTextField.getText());
            worker.setPhoneNumbers(new ArrayList<>(phoneNumberListView.getItems()));
            if (worker instanceof Cook) {
                Cook cook = (Cook) worker;
                cook.setFoodSpecialization(new ArrayList<>(specializationListView.getItems()));
                if (!workerDAO.updateWorker(cook, 1)) {
                    ControllerUtil.showMessageBox(Alert.AlertType.ERROR, "Greška", "Nije moguće ažurirati radnika");
                    throw new InvalidQuery("Worker was not updated!");
                }
            } else if (worker instanceof Waiter) {
                Waiter waiter = (Waiter) worker;
                waiter.setLanguages(new ArrayList<>(languagesListView.getItems()));
                if (!workerDAO.updateWorker(waiter, 1)) {
                    ControllerUtil.showMessageBox(Alert.AlertType.ERROR, "Greška", "Nije moguće ažurirati radnika");
                    throw new InvalidQuery("Worker was not updated!");
                }
            } else if (worker instanceof Manager) {
                Manager manager = (Manager) worker;
                manager.setWorkObligation(new ArrayList<>(obligationListView.getItems()));
                if (!workerDAO.updateWorker(manager, 1)) {
                    ControllerUtil.showMessageBox(Alert.AlertType.ERROR, "Greška", "Nije moguće ažurirati radnika");
                    throw new InvalidQuery("Worker was not updated!");
                }
            } else if (worker instanceof Bartender) {
                Bartender bartender = (Bartender) worker;
                if (!workerDAO.updateWorker(bartender, 1)) {
                    ControllerUtil.showMessageBox(Alert.AlertType.ERROR, "Greška", "Nije moguće ažurirati radnika");
                    throw new InvalidQuery("Worker was not updated!");
                }
            }
            Platform.runLater(() -> {
                WorkersController.workerList.add(worker);
            });
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
