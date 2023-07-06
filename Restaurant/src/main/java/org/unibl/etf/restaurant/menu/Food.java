package org.unibl.etf.restaurant.menu;

import javafx.beans.property.SimpleBooleanProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class Food extends Item {
    private SimpleBooleanProperty isFast;

    public Food(Integer id, String name, Double price, String description) {
        super(id, name, price, description);
    }

    public Food(Integer id, String name, Double price, String description, boolean isFast) {
        super(id,name,price, description);
        this.isFast = new SimpleBooleanProperty(isFast);
    }
    public boolean isFast() {
        return isFast.get();
    }

    public void setFast(boolean fast) {
        isFast.set(fast);
    }
}