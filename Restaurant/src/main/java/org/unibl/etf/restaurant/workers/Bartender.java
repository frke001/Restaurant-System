package org.unibl.etf.restaurant.workers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;

public class Bartender extends Worker{

    private SimpleBooleanProperty cocktailExperience;

    public Bartender(String JMB, String name, String surname, String address, Integer payment, WorkerType workerType, Boolean cocktailExperience) {
        super(JMB, name, surname, address, payment, workerType);
        this.cocktailExperience = new SimpleBooleanProperty(cocktailExperience);
    }
    public Bartender(String JMB, String name, String surname, String address, Integer payment, WorkerType workerType, Boolean cocktailExperience,ArrayList<String> phoneNumbers) {
        super(JMB, name, surname, address, payment, workerType,phoneNumbers);
        this.cocktailExperience = new SimpleBooleanProperty(cocktailExperience);
    }
    public Bartender(String JMB, String name, String surname, String address, Integer payment, WorkerType workerType) {
        super(JMB, name, surname, address, payment, workerType);
    }
    public boolean isCocktailExperience() {
        return cocktailExperience.get();
    }

    public void setCocktailExperience(boolean cocktailExperience) {
        this.cocktailExperience.set(cocktailExperience);
    }
}
