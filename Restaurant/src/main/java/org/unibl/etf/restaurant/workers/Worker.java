package org.unibl.etf.restaurant.workers;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.ToString;

import java.util.ArrayList;

@ToString
public class Worker {

    private SimpleStringProperty JMB;
    private SimpleStringProperty name;
    private SimpleStringProperty surname;
    private SimpleStringProperty address;
    private SimpleIntegerProperty payment;
    private SimpleObjectProperty<WorkerType> workerType;
    private SimpleObjectProperty<ArrayList<String>> phoneNumbers;

    public Worker(String JMB, String name, String surname, String address, Integer payment, WorkerType workerType){
        this.JMB = new SimpleStringProperty(JMB);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.address = new SimpleStringProperty(address);
        this.payment = new SimpleIntegerProperty(payment);
        this.workerType = new SimpleObjectProperty<WorkerType>(workerType);
        this.phoneNumbers = new SimpleObjectProperty<>();
    }
    public Worker(String JMB, String name, String surname, String address, Integer payment, WorkerType workerType,ArrayList<String> phoneNumbers){
        this.JMB = new SimpleStringProperty(JMB);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.address = new SimpleStringProperty(address);
        this.payment = new SimpleIntegerProperty(payment);
        this.workerType = new SimpleObjectProperty<WorkerType>(workerType);
        this.phoneNumbers = new SimpleObjectProperty<>(phoneNumbers);
    }

    public String getJMB() {
        return JMB.get();
    }
    public String getName() {
        return name.get();
    }

    public String getSurname() {
        return surname.get();
    }

    public String getAddress() {
        return address.get();
    }
    public Integer getPayment() {
        return payment.get();
    }

    public String getWorkerType() {
        if(workerType.get().equals(WorkerType.WAITER))
            return "Konobar";
        else if(workerType.get().equals(WorkerType.MANAGER))
            return "Menadžer";
        else if(workerType.get().equals(WorkerType.COOK))
            return "Kuvar";
        else return "Šanker";
    }

    public void setJMB(String JMB) {
        this.JMB.set(JMB);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setPayment(Integer payment) {
        this.payment.set(payment);
    }

    public void setWorkerType(WorkerType workerType) {
        this.workerType.set(workerType);
    }

    public ArrayList<String> getPhoneNumbers() {
        return phoneNumbers.get();
    }

    public void setPhoneNumbers(ArrayList<String> phoneNumbers) {
        this.phoneNumbers.set(phoneNumbers);
    }
}
