package org.unibl.etf.restaurant.menu;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class Item {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleObjectProperty<Double> price;
    private SimpleStringProperty description;


    public Item(){

    }
    public Item(Integer id, String name, Double price, String description) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleObjectProperty<Double>(price);
        this.description = new SimpleStringProperty(description);

    }

    public String getDescription() {
        return description.get();
    }

    public Integer getId() {
        return id.get();
    }


    public String getName() {
        return name.get();
    }

    public Double getPrice() {
        return price.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setPrice(Double price) {
        this.price.set(price);
    }

    public void setDescription(String description) {
        this.description.set(description);
    }
}
