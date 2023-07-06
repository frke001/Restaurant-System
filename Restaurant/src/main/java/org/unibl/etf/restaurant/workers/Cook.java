package org.unibl.etf.restaurant.workers;

import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;

public class Cook extends Worker{
    private SimpleObjectProperty<ArrayList<String>> foodSpecialization;
    public Cook(String JMB, String name, String surname, String address, Integer payment, WorkerType workerType, ArrayList<String> foodSpecialization) {
        super(JMB, name, surname, address, payment, workerType);
        this.foodSpecialization = new SimpleObjectProperty<ArrayList<String>>(foodSpecialization);
    }

    public Cook(String JMB, String name, String surname, String address, Integer payment, WorkerType workerType, ArrayList<String> foodSpecialization,ArrayList<String> phoneNumbers) {
        super(JMB, name, surname, address, payment, workerType,phoneNumbers);
        this.foodSpecialization = new SimpleObjectProperty<ArrayList<String>>(foodSpecialization);
    }
    public Cook(String JMB, String name, String surname, String address, Integer payment, WorkerType workerType) {
        super(JMB, name, surname, address, payment, workerType);
        this.foodSpecialization = new SimpleObjectProperty<>();
    }
    public ArrayList<String> getFoodSpecialization() {
        return foodSpecialization.get();
    }

    public void setFoodSpecialization(ArrayList<String> foodSpecialization) {
        this.foodSpecialization.set(foodSpecialization);
    }
}
