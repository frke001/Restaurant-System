package org.unibl.etf.restaurant.dao.mysql;

import org.unibl.etf.restaurant.dao.WorkerDAO;
import org.unibl.etf.restaurant.util.DBUtil;
import org.unibl.etf.restaurant.workers.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkerDAOImpl implements WorkerDAO {
    @Override
    public List<Worker> getWorkers(Integer restaurantId) {
        List<Worker> workers = new ArrayList<>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        String procedureCall = "{call dobavi_radnike(?)}";
        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setInt(1,restaurantId);
            resultSet = callableStatement.executeQuery();

            while(resultSet.next()){
                if(resultSet.getString(6).equals("Konobar"))
                    workers.add(new Waiter(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),
                            resultSet.getString(4),resultSet.getInt(5), WorkerType.WAITER));
                else if(resultSet.getString(6).equals("Kuvar"))
                    workers.add(new Cook(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),
                            resultSet.getString(4),resultSet.getInt(5), WorkerType.COOK));
                else if(resultSet.getString(6).equals("Menadžer"))
                    workers.add(new Manager(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),
                            resultSet.getString(4),resultSet.getInt(5), WorkerType.MANAGER));
                else if(resultSet.getString(6).equals("Šanker"))
                    workers.add(new Bartender(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),
                            resultSet.getString(4),resultSet.getInt(5), WorkerType.BARTENDER));
            }

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(resultSet,callableStatement,connection);
        }
        return workers;
    }

    @Override
    public List<String> getWaiterLanguages(String waiterJMB) {
        List<String> languages = new ArrayList<>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        String procedureCall = "{call dobavi_jezike(?)}";

        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setString(1,waiterJMB);
            resultSet = callableStatement.executeQuery();

            while (resultSet.next()){
                languages.add(resultSet.getString(1));
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(resultSet,callableStatement,connection);
        }
        return languages;
    }

    @Override
    public List<String> getPhoneNumbers(String workerJMB) {
        List<String> phoneNumbers = new ArrayList<>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        String procedureCall = "{call dobavi_brojeve(?)}";

        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setString(1,workerJMB);
            resultSet = callableStatement.executeQuery();

            while(resultSet.next()){
                phoneNumbers.add(resultSet.getString(1));
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(resultSet,callableStatement,connection);
        }
        return phoneNumbers;
    }

    @Override
    public List<String> getCookSpecializations(String cookJMB) {
        List<String> specializations = new ArrayList<>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        String procedureCall = "{call dobavi_specijalizacije(?)}";

        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setString(1,cookJMB);
            resultSet = callableStatement.executeQuery();

            while(resultSet.next()){
                specializations.add(resultSet.getString(1));
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(resultSet,callableStatement,connection);
        }
        return specializations;
    }

    @Override
    public List<String> getManagerObligations(String managerJMB) {
        List<String> obligations = new ArrayList<>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        String procedureCall = "{call dobavi_obaveze(?)}";

        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setString(1,managerJMB);
            resultSet = callableStatement.executeQuery();

            while(resultSet.next()){
                obligations.add(resultSet.getString(1));
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(resultSet,callableStatement,connection);
        }
        return obligations;
    }

    @Override
    public boolean getCocktailExperience(String bartenderJMB) {
        boolean result = false;
        Connection connection = null;
        CallableStatement callableStatement = null;


        String procedureCall = "{call dobavi_iskustvo_kokteli(?,?)}";

        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setString(1,bartenderJMB);
            callableStatement.registerOutParameter(2, Types.BOOLEAN);

            callableStatement.execute();
            result = callableStatement.getBoolean(2);


        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(callableStatement,connection);
        }
        return result;
    }

    @Override
    public boolean addWorker(Worker worker, Integer restaurantId) {
        boolean result = false;
        Connection connection = null;
        CallableStatement callableStatement = null;

        String insertWorkerCall = "{call dodaj_zaposlenog(?, ?, ?, ?, ?, ?, ?, ?)}";
        String insertLanguageCall = "{call dodaj_jezik(?, ?)}";
        String insertSpecializationCall = "{call dodaj_specijalizaciju(?, ?)}";
        String insertObligationCall = "{call dodaj_obavezu(?, ?)}";
        String insertNumberCall = "{call dodaj_broj(?, ?)}";

        try{
            connection = DBUtil.getConnection();
            // dodavanje zaposlenog
            callableStatement = connection.prepareCall(insertWorkerCall);
            callableStatement.setString(1,worker.getJMB());
            callableStatement.setString(2,worker.getName());
            callableStatement.setString(3,worker.getSurname());
            callableStatement.setString(4,worker.getAddress());
            callableStatement.setInt(5,worker.getPayment());
            callableStatement.setInt(6,restaurantId);
            callableStatement.setString(7,worker.getWorkerType());
            if(worker instanceof Bartender){
                Bartender bartender = (Bartender) worker;
                callableStatement.setBoolean(8,bartender.isCocktailExperience());
            }else
                callableStatement.setBoolean(8,true);

            if(callableStatement.executeUpdate() != 1)
                return false;
            DBUtil.close(callableStatement);

            // dodavanje brojeva telefona za zaposlenog
            callableStatement = connection.prepareCall(insertNumberCall);
            for(var el : worker.getPhoneNumbers()) {
                callableStatement.setString(1, worker.getJMB());
                callableStatement.setString(2, el);
                if(callableStatement.executeUpdate() != 1)
                    return false;
            }
            DBUtil.close(callableStatement);
            // dodavanje kuvara
            if(worker instanceof Cook) {
                Cook cook = (Cook) worker;

                callableStatement = connection.prepareCall(insertSpecializationCall);
                for(var el : cook.getFoodSpecialization()) {
                    callableStatement.setString(1, worker.getJMB());
                    callableStatement.setString(2, el);
                    if(callableStatement.executeUpdate() != 1)
                        return false;
                }
                DBUtil.close(callableStatement);
                //dodavanje menadzera
            }else if(worker instanceof Manager) {
                Manager manager = (Manager) worker;

                callableStatement = connection.prepareCall(insertObligationCall);
                for (var el : manager.getWorkObligation()) {
                    callableStatement.setString(1, worker.getJMB());
                    callableStatement.setString(2, el);
                    if (callableStatement.executeUpdate() != 1)
                        return false;
                }
                DBUtil.close(callableStatement);
                // dodavanje konobara
            }else if(worker instanceof Waiter) {
                Waiter waiter = (Waiter) worker;

                callableStatement = connection.prepareCall(insertLanguageCall);
                for (var el : waiter.getLanguages()) {
                    callableStatement.setString(1, worker.getJMB());
                    callableStatement.setString(2, el);
                    if (callableStatement.executeUpdate() != 1)
                        return false;
                }
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(callableStatement,connection);
        }
        return true;
    }

    @Override
    public boolean deleteWorker(Worker worker, Integer restaurantId) {
        boolean result = false;
        Connection connection = null;
        CallableStatement callableStatement = null;

        String procedureCall = "{call obriši_zaposlenog(?, ?)}";

        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setString(1, worker.getJMB());
            callableStatement.setInt(2,restaurantId);

            result = callableStatement.executeUpdate() == 1;

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(callableStatement,connection);
        }
        return result;
    }

    @Override
    public boolean updateWorker(Worker worker, Integer restaurantId) {
        boolean result = false;
        Connection connection = null;
        CallableStatement callableStatement = null;

        String updateWorkerCall = "{call ažuriraj_zaposlenog(?, ?, ?, ?)}";

        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(updateWorkerCall);
            callableStatement.setString(1,worker.getJMB());
            callableStatement.setInt(2,restaurantId);
            callableStatement.setInt(3,worker.getPayment());
            callableStatement.setString(4,worker.getAddress());

            result = callableStatement.executeUpdate() == 1;
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean addPhoneNumber(String workerJMB, String phoneNumber) {
        boolean result = false;
        Connection connection = null;
        CallableStatement callableStatement = null;

        String insertNumberCall = "{call dodaj_broj(?, ?)}";
        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(insertNumberCall);
            callableStatement.setString(1,workerJMB);
            callableStatement.setString(2,phoneNumber);
            result = callableStatement.executeUpdate() == 1;

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(callableStatement,connection);
        }
        return result;
    }

    @Override
    public boolean addLanguage(String workerJMB, String language) {
        boolean result = false;
        Connection connection = null;
        CallableStatement callableStatement = null;

        String insertLanguageCall = "{call dodaj_jezik(?, ?)}";
        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(insertLanguageCall);
            callableStatement.setString(1,workerJMB);
            callableStatement.setString(2,language);
            result = callableStatement.executeUpdate() == 1;

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(callableStatement,connection);
        }
        return result;
    }

    @Override
    public boolean addSpecialization(String workerJMB, String specialization) {
        boolean result = false;
        Connection connection = null;
        CallableStatement callableStatement = null;

        String insertSpecializationCall = "{call dodaj_specijalizaciju(?, ?)}";
        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(insertSpecializationCall);
            callableStatement.setString(1,workerJMB);
            callableStatement.setString(2,specialization);
            result = callableStatement.executeUpdate() == 1;

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(callableStatement,connection);
        }
        return result;
    }

    @Override
    public boolean addObligation(String workerJMB, String obligation) {
        boolean result = false;
        Connection connection = null;
        CallableStatement callableStatement = null;

        String insertObligationCall = "{call dodaj_obavezu(?, ?)}";
        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(insertObligationCall);
            callableStatement.setString(1,workerJMB);
            callableStatement.setString(2,obligation);
            result = callableStatement.executeUpdate() == 1;

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(callableStatement,connection);
        }
        return result;
    }

    @Override
    public boolean deletePhoneNumber(String workerJMB, String phoneNumber) {
        boolean result = false;
        Connection connection = null;
        CallableStatement callableStatement = null;

        String deletePhoneNumberCall = "{call obriši_broj(?, ?)}";
        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(deletePhoneNumberCall);
            callableStatement.setString(1,workerJMB);
            callableStatement.setString(2,phoneNumber);
            result = callableStatement.executeUpdate() == 1;

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(callableStatement,connection);
        }
        return result;
    }

    @Override
    public boolean deleteLanguage(String workerJMB, String language) {
        boolean result = false;
        Connection connection = null;
        CallableStatement callableStatement = null;

        String deleteLanguageCall = "{call obriši_jezik(?, ?)}";
        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(deleteLanguageCall);
            callableStatement.setString(1,workerJMB);
            callableStatement.setString(2,language);
            result = callableStatement.executeUpdate() == 1;

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(callableStatement,connection);
        }
        return result;
    }

    @Override
    public boolean deleteSpecialization(String workerJMB, String specialization) {
        boolean result = false;
        Connection connection = null;
        CallableStatement callableStatement = null;

        String deleteSpecializationCall = "{call obriši_specijalizaciju(?, ?)}";
        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(deleteSpecializationCall);
            callableStatement.setString(1,workerJMB);
            callableStatement.setString(2,specialization);
            result = callableStatement.executeUpdate() == 1;

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(callableStatement,connection);
        }
        return result;
    }

    @Override
    public boolean deleteObligation(String workerJMB, String obligation) {
        boolean result = false;
        Connection connection = null;
        CallableStatement callableStatement = null;

        String deleteObligationCall = "{call obriši_obavezu(?, ?)}";
        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(deleteObligationCall);
            callableStatement.setString(1,workerJMB);
            callableStatement.setString(2,obligation);
            result = callableStatement.executeUpdate() == 1;

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(callableStatement,connection);
        }
        return result;
    }

    @Override
    public String getWorkerNameByJMB(String JMB) {
        String result = "";
        Connection connection = null;
        CallableStatement callableStatement = null;


        String procedureCall = "{call dobavi_ime_zaposlenog(?,?)}";

        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setString(1,JMB);
            callableStatement.registerOutParameter(2, Types.VARCHAR);

            callableStatement.execute();
            result = callableStatement.getString(2);


        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(callableStatement,connection);
        }
        return result;
    }

    @Override
    public List<String> getWaiterNames(Integer restaurantId) {
        List<String> workerNames = new ArrayList<>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        String procedureCall = "{call dobavi_JMB_konobara(?)}";
        try{
            connection = DBUtil.getConnection();
            callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setInt(1,restaurantId);
            resultSet = callableStatement.executeQuery();

            while(resultSet.next()){
               workerNames.add(resultSet.getString(1));
            }

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            DBUtil.close(resultSet,callableStatement,connection);
        }
        return workerNames;
    }
}
