package org.unibl.etf.restaurant.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.unibl.etf.restaurant.dao.MenuDAO;
import org.unibl.etf.restaurant.dao.mysql.MenuDAOImpl;
import org.unibl.etf.restaurant.exceptions.InvalidQuery;
import org.unibl.etf.restaurant.menu.Drink;
import org.unibl.etf.restaurant.menu.Food;
import org.unibl.etf.restaurant.util.ControllerUtil;
import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class AddItemController {

    @FXML
    private CheckBox drinkCheckBox;

    @FXML
    private ComboBox<String> drinkTypeComboBox;

    @FXML
    private CheckBox foodCheckBox;

    @FXML
    private ComboBox<String> foodTypeComboBox;

    @FXML
    private Button saveItemButton;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField idTextField;

    private MenuDAO menuDAO = new MenuDAOImpl();
    @FXML
    void onDrinkCheckBoxCheck(ActionEvent event) {
        if(drinkCheckBox.isSelected())
            foodCheckBox.setSelected(false);
        foodTypeComboBox.setOpacity(0.5);
        foodTypeComboBox.setDisable(true);
        drinkTypeComboBox.setOpacity(1);
        drinkTypeComboBox.setDisable(false);
    }

    @FXML
    void onFoodCheckBoxCheck(ActionEvent event) {
        if(foodCheckBox.isSelected())
            drinkCheckBox.setSelected(false);
        drinkTypeComboBox.setOpacity(0.5);
        drinkTypeComboBox.setDisable(true);
        foodTypeComboBox.setOpacity(1);
        foodTypeComboBox.setDisable(false);
    }

    @FXML
    void onSaveItemButtonClick(ActionEvent event) {
        try {
            if (foodCheckBox.isSelected()) {
                if("".equals(idTextField.getText()) || "".equals(nameTextField.getText()) || "".equals(priceTextField.getText())
                        || foodTypeComboBox.getValue() == null){
                    alert("Greska", "Molimo vas unesite podatke");
                    return;
                }

                else {
                    boolean isFast;
                    if (foodTypeComboBox.getValue().equals("Posna"))
                        isFast = true;
                    else
                        isFast = false;
                    Food food = new Food(Integer.parseInt(idTextField.getText()), nameTextField.getText(),
                            Double.parseDouble(priceTextField.getText()), descriptionTextArea.getText(), isFast);
                    if(!menuDAO.addItem(food, 1)) {
                        ControllerUtil.showMessageBox(Alert.AlertType.ERROR, "Greška", "Nije moguće dodati jelo");
                        throw new InvalidQuery("Item was not added!");
                    }
                    food.setDescription(food.getDescription() + (isFast? " (Posno)" : " (Nije posno)"));
                    MenuController.itemList.add(food);
                }
            } else if (drinkCheckBox.isSelected()) {
                if("".equals(idTextField.getText()) || "".equals(nameTextField.getText()) || "".equals(priceTextField.getText())
                        || drinkTypeComboBox.getValue() == null){
                    alert("Greska", "Molimo vas unesite podatke");
                    return;
                }
                else {
                    boolean isAlcoholic;
                    if (drinkTypeComboBox.getValue().equals("Alkoholno"))
                        isAlcoholic = true;
                    else
                        isAlcoholic = false;
                    Drink drink = new Drink(Integer.parseInt(idTextField.getText()), nameTextField.getText(),
                            Double.parseDouble(priceTextField.getText()), descriptionTextArea.getText(), isAlcoholic);
                    if(!menuDAO.addItem(drink, 1)) {
                        ControllerUtil.showMessageBox(Alert.AlertType.ERROR, "Greška", "Nije moguće dodati piće");
                        throw new InvalidQuery("Item was not added!");
                    }
                    drink.setDescription(drink.getDescription() + (isAlcoholic? " (Alkoholno)" : " (Bezlkoholno)"));
                    MenuController.itemList.add(drink);
                }
            }
            Stage stage = (Stage) saveItemButton.getScene().getWindow();
            stage.close();
        }catch (NumberFormatException ex){
            System.out.println("Number format is not valid!");
        }catch (InvalidQuery ex){
            System.out.println(ex.getMessage());
        } catch (Exception ex){
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
