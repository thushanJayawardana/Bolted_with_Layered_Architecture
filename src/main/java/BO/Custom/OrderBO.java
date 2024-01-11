package BO.Custom;

import BO.SuperBO;
import dto.OrderDto;

import java.sql.SQLException;

public interface OrderBO extends SuperBO {
    String generateNextID() throws SQLException, ClassNotFoundException;

    boolean placeOrder(OrderDto orderDto) throws SQLException;

}
