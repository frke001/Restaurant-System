package org.unibl.etf.restaurant.dao;

import org.unibl.etf.restaurant.workers.Worker;

import java.util.List;

public interface WorkerDAO {

    List<Worker> getWorkers(Integer restaurantId);
    List<String> getWaiterLanguages(String waiterJMB);

    List<String> getPhoneNumbers(String workerJMB);
    List<String> getCookSpecializations(String cookJMB);
    List<String> getManagerObligations(String managerJMB);
    boolean getCocktailExperience(String bartenderJMB);

    boolean addWorker(Worker worker, Integer restaurantId);
    boolean deleteWorker(Worker workers,Integer restaurantId);

    boolean updateWorker(Worker worker, Integer restaurantId);

    boolean addPhoneNumber(String workerJMB, String phoneNumber);
    boolean addLanguage(String workerJMB, String language);
    boolean addSpecialization(String workerJMB, String specialization);
    boolean addObligation(String workerJMB, String obligation);

    boolean deletePhoneNumber(String workerJMB, String phoneNumber);
    boolean deleteLanguage(String workerJMB, String language);
    boolean deleteSpecialization(String workerJMB, String specialization);
    boolean deleteObligation(String workerJMB, String obligation);

    String getWorkerNameByJMB(String JMB);

    List<String> getWaiterNames(Integer restaurantId);
}
