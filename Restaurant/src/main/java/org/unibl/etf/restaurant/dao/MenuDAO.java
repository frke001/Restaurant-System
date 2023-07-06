package org.unibl.etf.restaurant.dao;

import org.unibl.etf.restaurant.menu.Item;
import org.unibl.etf.restaurant.menu.Menu;

import java.util.List;

public interface MenuDAO {


    List<Item> getItems(Integer menuId);
    boolean addItem(Item item, Integer menuId);
    boolean deleteItem(Item item,Integer menuId, boolean type);

    boolean updateItem(Item item, Integer menuId, boolean type);

    String getFoodNameById(Integer foodId);
    String getDrinkNameById(Integer drinkId);
}
