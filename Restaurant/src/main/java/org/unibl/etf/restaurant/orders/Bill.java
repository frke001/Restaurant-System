package org.unibl.etf.restaurant.orders;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ToString
public class Bill {
    private SimpleIntegerProperty id;
    private SimpleObjectProperty<Timestamp> dateTime;
    private SimpleBooleanProperty cash;

    public Bill(Integer id, Timestamp dateTime, Boolean cash) {
        this.id = new SimpleIntegerProperty(id);
        this.dateTime = new SimpleObjectProperty<>(dateTime);
        this.cash = new SimpleBooleanProperty(cash);
    }


    public int getId() {
        return id.get();
    }


    public void setId(int id) {
        this.id.set(id);
    }

    public Timestamp getDateTime() {
        return dateTime.get();
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime.set(dateTime);
    }

    public String getCash() {
        if(cash.get())
            return "Gotovina";
        else return "Kartica";
    }

    public void setCash(Boolean cash) {
        this.cash.set(cash);
    }
}
