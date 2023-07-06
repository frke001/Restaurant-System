package org.unibl.etf.restaurant.orders;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OrderItem {
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty quantity;


    private SimpleIntegerProperty orderId;
    private SimpleIntegerProperty itemId;
    private SimpleStringProperty itemName;


    public OrderItem(Integer id, Integer quantity, Integer orderId, Integer itemId, String itemName) {
        this.id = new SimpleIntegerProperty(id);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.orderId = new SimpleIntegerProperty(orderId);
        this.itemId = new SimpleIntegerProperty(itemId);
        this.itemName = new SimpleStringProperty(itemName);
    }
    public OrderItem(Integer quantity,Integer orderId, String itemName) {
        this.quantity = new SimpleIntegerProperty(quantity);
        this.orderId = new SimpleIntegerProperty(orderId);
        this.itemId = new SimpleIntegerProperty();
        this.itemName = new SimpleStringProperty(itemName);
    }

    public String getItemName() {
        return itemName.get();
    }

    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public int getOrderId() {
        return orderId.get();
    }

    public void setOrderId(int orderId) {
        this.orderId.set(orderId);
    }

    public int getItemId() {
        return itemId.get();
    }


    public void setItemId(int itemId) {
        this.itemId.set(itemId);
    }
}
