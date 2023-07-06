package org.unibl.etf.restaurant.dao;

import org.unibl.etf.restaurant.orders.Order;
import org.unibl.etf.restaurant.orders.OrderItem;

import java.util.List;

public interface OrderDAO {

    List<Order> getOrders();
    List<OrderItem> getOrderItems(Integer orderNumber);

    Integer addOrder(Order order);

    boolean addOrderItem(OrderItem orderItem);

    Order getOrderById(Integer id);

    boolean deleteOrder(Order order);

    boolean updateOrder(Order order, boolean type);
}
