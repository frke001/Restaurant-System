package org.unibl.etf.restaurant.workers;

import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;

public class Waiter extends Worker{

    private SimpleObjectProperty<ArrayList<String>> languages;

    public Waiter(String JMB, String name, String surname, String address, Integer payment, WorkerType workerType, ArrayList<String> languages) {
        super(JMB, name, surname, address, payment, workerType);
        this.languages = new SimpleObjectProperty<ArrayList<String>>(languages);
    }
    public Waiter(String JMB, String name, String surname, String address, Integer payment, WorkerType workerType, ArrayList<String> languages,ArrayList<String> phoneNumbers) {
        super(JMB, name, surname, address, payment, workerType,phoneNumbers);
        this.languages = new SimpleObjectProperty<ArrayList<String>>(languages);
    }
    public Waiter(String JMB, String name, String surname, String address, Integer payment, WorkerType workerType) {
        super(JMB, name, surname, address, payment, workerType);
        this.languages = new SimpleObjectProperty<>();
    }

    public ArrayList<String> getLanguages() {
        return languages.get();
    }

    public void setLanguages(ArrayList<String> languages) {
        this.languages.set(languages);
    }
}
