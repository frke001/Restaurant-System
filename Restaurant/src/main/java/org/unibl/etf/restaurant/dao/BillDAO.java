package org.unibl.etf.restaurant.dao;

import org.unibl.etf.restaurant.orders.Bill;

import java.util.List;

public interface BillDAO {

    List<Bill> getBills();
    Bill getBillById(Integer billId);
}
