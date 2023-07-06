package org.unibl.etf.restaurant.dao;

import java.util.List;

public interface TableDAO {

    List<Integer> getTableIds(Integer restaurantId);
}
