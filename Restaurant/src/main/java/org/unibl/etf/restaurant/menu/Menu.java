package org.unibl.etf.restaurant.menu;

import javafx.beans.property.*;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Menu {
    private SimpleIntegerProperty id;
    private SimpleObjectProperty<Date> publicationDate;

    public Menu(Integer id, Date publicationDate) {
        this.id = new SimpleIntegerProperty(id);
        this.publicationDate = new SimpleObjectProperty<Date>(publicationDate);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public Date getPublicationDate() {
        return publicationDate.get();
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate.set(publicationDate);
    }

}
