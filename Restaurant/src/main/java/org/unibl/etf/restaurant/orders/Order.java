package org.unibl.etf.restaurant.orders;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ToString
public class Order {
    private SimpleIntegerProperty id;
    private SimpleObjectProperty<Timestamp> dateTime;
    private SimpleBooleanProperty processed;
    private SimpleStringProperty waiterJMB;
    private SimpleIntegerProperty tableId;

    private SimpleObjectProperty<ArrayList<OrderItem>> items;

    public Order(){
        super();
    }
    public Order(Integer id, Timestamp dateTime, Boolean processed, Integer tableId,String waiterJMB) {
        this.id = new SimpleIntegerProperty(id);
        this.dateTime = new SimpleObjectProperty<>(dateTime);
        this.processed = new SimpleBooleanProperty(processed);
        this.waiterJMB = new SimpleStringProperty(waiterJMB);
        this.tableId = new SimpleIntegerProperty(tableId);
        items = new SimpleObjectProperty<>();
    }
    public Order(Boolean processed, Integer tableId,String waiterJMB) {
        this.processed = new SimpleBooleanProperty(processed);
        this.waiterJMB = new SimpleStringProperty(waiterJMB);
        this.tableId = new SimpleIntegerProperty(tableId);
        items = new SimpleObjectProperty<>();
    }

    public String getProcessed() {
        if(processed.get())
            return "Završena";
        else return "U toku";
    }


    public String getWaiterJMB() {
        return waiterJMB.get();
    }

    public Integer getTableId() {
        return tableId.get();
    }


    public Integer getId() {
        return id.get();
    }

    public Timestamp getDateTime() {
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return this.dateTime.get();
    }

    public List<OrderItem> getItems() {
        return items.get();
    }

    public Boolean isProcessed() {
        return processed.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime.set(dateTime);
    }

    public void setProcessed(boolean processed) {
        this.processed.set(processed);
    }

    public void setWaiterJMB(String waiterJMB) {
        this.waiterJMB.set(waiterJMB);
    }

    public void setTableId(int tableId) {
        this.tableId.set(tableId);
    }

    public void setItems(ArrayList<OrderItem> items) {
        this.items.set(items);
    }
}
