package org.unibl.etf.restaurant.menu;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class Drink extends Item{
    private SimpleBooleanProperty isAlcoholic;

    public Drink(Integer id, String name, Double price, String description) {
        super(id, name, price, description);
    }
    public Drink(Integer id, String name, Double price, String description, boolean isAlcoholic) {
        super(id, name, price, description);
        this.isAlcoholic = new SimpleBooleanProperty(isAlcoholic);
    }

    public boolean isAlcoholic() {
        return isAlcoholic.get();
    }

    public void setIsAlcoholic(boolean isAlcoholic) {
        this.isAlcoholic.set(isAlcoholic);
    }

}