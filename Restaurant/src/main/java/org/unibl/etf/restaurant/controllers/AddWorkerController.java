package org.unibl.etf.restaurant.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.unibl.etf.restaurant.dao.WorkerDAO;
import org.unibl.etf.restaurant.dao.mysql.WorkerDAOImpl;
import org.unibl.etf.restaurant.exceptions.InvalidQuery;
import org.unibl.etf.restaurant.menu.Menu;
import org.unibl.etf.restaurant.util.ControllerUtil;
import org.unibl.etf.restaurant.workers.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddWorkerController {

    @FXML
    public Button addLanguageButton;
    @FXML
    public Button addPhoneNumButton;
    @FXML
    public Button addObligationButton;
    @FXML
    public Button addSpecializationButton;
    @FXML
    private CheckBox bartenderTypeCheckBox;

    @FXML
    private CheckBox cookTypeCheckBox;

    @FXML
    private TextField languagesTextField;

    @FXML
    private CheckBox managerTypeCheckBox;

    @FXML
    private CheckBox noCheckBox;

    @FXML
    private TextField obligationTextField;

    @FXML
    private TextField phoneNumTextField;

    @FXML
    private Button saveButton;

    @FXML
    private TextField specializationTextField;

    @FXML
    private CheckBox waiterTypeCheckBox;

    @FXML
    private CheckBox yesCheckBox;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField paymentTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField JMBTextField;

    private WorkerDAO workerDAO = new WorkerDAOImpl();

    private ArrayList<String> languages = new ArrayList<>();
    private ArrayList<String> obligations = new ArrayList<>();
    private ArrayList<String> specializations = new ArrayList<>();
    private ArrayList<String> numbers = new ArrayList<>();

    @FXML
    void onSaveButton(ActionEvent event) {
        try{
            if(cookTypeCheckBox.isSelected()){
                if("".equals(nameTextField.getText()) || "".equals(surnameTextField.getText()) || "".equals(JMBTextField.getText())
                        || "".equals(addressTextField.getText()) || "".equals(paymentTextField.getText())){
                    alert("Greska", "Molimo vas unesite podatke");
                    return;
                }else{
                    Cook cook = new Cook(JMBTextField.getText(),nameTextField.getText(),surnameTextField.getText(),addressTextField.getText(),
                            Integer.parseInt(paymentTextField.getText()), WorkerType.COOK,specializations,numbers);
                    if(!workerDAO.addWorker(cook,1)) {
                        ControllerUtil.showMessageBox(Alert.AlertType.ERROR, "Greška", "Nije moguće dodati radnika");
                        throw new InvalidQuery("Worker was not added!");
                    }
                    WorkersController.workerList.add(cook);
                }
            }else if(waiterTypeCheckBox.isSelected()){
                if("".equals(nameTextField.getText()) || "".equals(surnameTextField.getText()) || "".equals(JMBTextField.getText())
                        || "".equals(addressTextField.getText()) || "".equals(paymentTextField.getText())){
                    alert("Greska", "Molimo vas unesite podatke");
                    return;
                }else{
                    Waiter waiter = new Waiter(JMBTextField.getText(),nameTextField.getText(),surnameTextField.getText(),addressTextField.getText(),
                            Integer.parseInt(paymentTextField.getText()), WorkerType.WAITER,languages,numbers);
                    if(!workerDAO.addWorker(waiter,1)){
                        ControllerUtil.showMessageBox(Alert.AlertType.ERROR, "Greška", "Nije moguće dodati radnika");
                        throw new InvalidQuery("Worker was not added!");
                    }
                    WorkersController.workerList.add(waiter);
                }
            }
            else if(managerTypeCheckBox.isSelected()){
                if("".equals(nameTextField.getText()) || "".equals(surnameTextField.getText()) || "".equals(JMBTextField.getText())
                        || "".equals(addressTextField.getText()) || "".equals(paymentTextField.getText())){
                    alert("Greska", "Molimo vas unesite podatke");
                    return;
                }else{
                    Manager manager = new Manager(JMBTextField.getText(),nameTextField.getText(),surnameTextField.getText(),addressTextField.getText(),
                            Integer.parseInt(paymentTextField.getText()), WorkerType.MANAGER,obligations,numbers);
                    if(!workerDAO.addWorker(manager,1)){
                        ControllerUtil.showMessageBox(Alert.AlertType.ERROR, "Greška", "Nije moguće dodati radnika");
                        throw new InvalidQuery("Worker was not added!");
                    }
                    WorkersController.workerList.add(manager);
                }
            }
            else if(bartenderTypeCheckBox.isSelected()){
                if("".equals(nameTextField.getText()) || "".equals(surnameTextField.getText()) || "".equals(JMBTextField.getText())
                        || "".equals(addressTextField.getText()) || "".equals(paymentTextField.getText()) || (!yesCheckBox.isSelected() && !noCheckBox.isSelected())){
                    alert("Greska", "Molimo vas unesite podatke");
                    return;
                }else{
                    boolean cocktail = false;
                    if (yesCheckBox.isSelected())
                        cocktail = true;

                    Bartender bartender = new Bartender(JMBTextField.getText(),nameTextField.getText(),surnameTextField.getText(),addressTextField.getText(),
                            Integer.parseInt(paymentTextField.getText()), WorkerType.BARTENDER,cocktail,numbers);
                    if(!workerDAO.addWorker(bartender,1)){
                        ControllerUtil.showMessageBox(Alert.AlertType.ERROR, "Greška", "Nije moguće dodati radnika");
                        throw new InvalidQuery("Worker was not added!");
                    }
                    WorkersController.workerList.add(bartender);
                }
            }
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }catch (NumberFormatException ex){
            System.out.println(ex.getMessage());
        } catch (InvalidQuery e) {
            throw new RuntimeException(e);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    @FXML
    void onAddLanguageButton(ActionEvent event) {
        if(!"".equals(languagesTextField.getText())){
            languages.add(languagesTextField.getText());
            languagesTextField.clear();
        }
    }

    @FXML
    void onAddNumberButton(ActionEvent event) {
        if(!"".equals(phoneNumTextField.getText())){
            String phoneNumber = phoneNumTextField.getText();
            if(!phoneNumber.startsWith("+387"))
                phoneNumber="+387 (0)" + phoneNumber.substring(1,phoneNumber.length());
            numbers.add(phoneNumber);
            phoneNumTextField.clear();
        }
    }

    @FXML
    void onAddObligationButton(ActionEvent event) {
        if(!"".equals(obligationTextField.getText())){
            obligations.add(obligationTextField.getText());
            obligationTextField.clear();
        }
    }

    @FXML
    void onAddSpecializationButton(ActionEvent event) {
        if(!"".equals(specializationTextField.getText())){
            specializations.add(specializationTextField.getText());
            specializationTextField.clear();
        }
    }

    private void alert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void onBartenderTypeCheckBox(ActionEvent event) {
        if(bartenderTypeCheckBox.isSelected()){
            cookTypeCheckBox.setSelected(false);
            managerTypeCheckBox.setSelected(false);
            waiterTypeCheckBox.setSelected(false);
        }
        yesCheckBox.setDisable(false);
        noCheckBox.setDisable(false);
        yesCheckBox.setOpacity(1);
        noCheckBox.setOpacity(1);
        languagesTextField.setDisable(true);
        obligationTextField.setDisable(true);
        specializationTextField.setDisable(true);
        languagesTextField.setOpacity(0.5);
        obligationTextField.setOpacity(0.5);
        specializationTextField.setOpacity(0.5);
        addLanguageButton.setOpacity(0.5);
        addObligationButton.setOpacity(0.5);
        addSpecializationButton.setOpacity(0.5);
    }

    @FXML
    void onCookTypeCheckBox(ActionEvent event) {
        if(cookTypeCheckBox.isSelected()){
            bartenderTypeCheckBox.setSelected(false);
            managerTypeCheckBox.setSelected(false);
            waiterTypeCheckBox.setSelected(false);
        }
        yesCheckBox.setDisable(true);
        noCheckBox.setDisable(true);
        yesCheckBox.setOpacity(0.5);
        noCheckBox.setOpacity(0.5);
        languagesTextField.setDisable(true);
        obligationTextField.setDisable(true);
        specializationTextField.setDisable(false);
        languagesTextField.setOpacity(0.5);
        obligationTextField.setOpacity(0.5);
        specializationTextField.setOpacity(1);
        addLanguageButton.setOpacity(0.5);
        addObligationButton.setOpacity(0.5);
        addSpecializationButton.setOpacity(1);
    }

    @FXML
    void onManagerTypeCheckBox(ActionEvent event) {
        if(managerTypeCheckBox.isSelected()){
            cookTypeCheckBox.setSelected(false);
            bartenderTypeCheckBox.setSelected(false);
            waiterTypeCheckBox.setSelected(false);
        }
        yesCheckBox.setDisable(true);
        noCheckBox.setDisable(true);
        yesCheckBox.setOpacity(0.5);
        noCheckBox.setOpacity(0.5);
        languagesTextField.setDisable(true);
        obligationTextField.setDisable(false);
        specializationTextField.setDisable(true);
        languagesTextField.setOpacity(0.5);
        obligationTextField.setOpacity(1);
        specializationTextField.setOpacity(0.5);
        addLanguageButton.setOpacity(0.5);
        addObligationButton.setOpacity(1);
        addSpecializationButton.setOpacity(0.5);
    }

    @FXML
    void onNoCheckBox(ActionEvent event) {
        if(noCheckBox.isSelected()){
            yesCheckBox.setSelected(false);
        }
    }

    @FXML
    void onWaiterTypeCheckBox(ActionEvent event) {
        if(waiterTypeCheckBox.isSelected()){
            cookTypeCheckBox.setSelected(false);
            managerTypeCheckBox.setSelected(false);
            bartenderTypeCheckBox.setSelected(false);
        }
        yesCheckBox.setDisable(true);
        noCheckBox.setDisable(true);
        yesCheckBox.setOpacity(0.5);
        noCheckBox.setOpacity(0.5);
        languagesTextField.setDisable(false);
        obligationTextField.setDisable(true);
        specializationTextField.setDisable(true);
        languagesTextField.setOpacity(1);
        obligationTextField.setOpacity(0.5);
        specializationTextField.setOpacity(0.5);
        addLanguageButton.setOpacity(1);
        addObligationButton.setOpacity(0.5);
        addSpecializationButton.setOpacity(0.5);
    }

    @FXML
    void onYesCheckBox(ActionEvent event) {
        if(yesCheckBox.isSelected()){
            noCheckBox.setSelected(false);
        }
    }

}