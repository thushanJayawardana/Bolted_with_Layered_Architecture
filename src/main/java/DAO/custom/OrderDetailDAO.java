package DAO.custom;

import DAO.CrudDAO;
import Entity.Order;
import dto.OrderDto;
import dto.tm.OrderTm;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailDAO extends CrudDAO<OrderDto> {
    boolean save(String orderId, List<OrderTm> orderTmList) throws SQLException, ClassNotFoundException;

}
