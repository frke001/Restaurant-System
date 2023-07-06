package org.unibl.etf.restaurant.orders;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OrderItemTemporary {

    private SimpleIntegerProperty quantity;
    private SimpleStringProperty name;

    public OrderItemTemporary(Integer quantity, String name) {
        this.quantity = new SimpleIntegerProperty(quantity);
        this.name = new SimpleStringProperty(name);
    }

    public Integer getQuantity() {
        return quantity.get();
    }


    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public String getName() {
        return name.get();
    }


    public void setName(String name) {
        this.name.set(name);
    }

}
