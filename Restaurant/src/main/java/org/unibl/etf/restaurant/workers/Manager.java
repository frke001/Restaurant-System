package org.unibl.etf.restaurant.workers;

import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;

public class Manager extends Worker{
    private SimpleObjectProperty<ArrayList<String>> workObligation;

    public Manager(String JMB, String name, String surname, String address, Integer payment, WorkerType workerType, ArrayList<String> workObligation) {
        super(JMB, name, surname, address, payment, workerType);
        this.workObligation = new SimpleObjectProperty<ArrayList<String>>(workObligation);
    }
    public Manager(String JMB, String name, String surname, String address, Integer payment, WorkerType workerType, ArrayList<String> workObligation,ArrayList<String> phoneNumbers) {
        super(JMB, name, surname, address, payment, workerType,phoneNumbers);
        this.workObligation = new SimpleObjectProperty<ArrayList<String>>(workObligation);
    }
    public Manager(String JMB, String name, String surname, String address, Integer payment, WorkerType workerType) {
        super(JMB, name, surname, address, payment, workerType);
        this.workObligation = new SimpleObjectProperty<>();
    }

    public ArrayList<String> getWorkObligation() {
        return workObligation.get();
    }

    public void setWorkObligation(ArrayList<String> workObligation) {
        this.workObligation.set(workObligation);
    }
}
