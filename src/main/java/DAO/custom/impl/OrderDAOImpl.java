package DAO.custom.impl;

import DAO.SQLUtil;
import DAO.custom.OrderDAO;
import Entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    private String splitOrderID(String currentOrderID){
        if (currentOrderID != null){
            String [] split = currentOrderID.split("[O]");

            int id = Integer.parseInt(split[1]);
            id++;
            return String.format("O%03d",id);
        }else {
            return "O001";
        }
    }

    public String generateNextID() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT order_Id FROM Order ORDER BY order_Id DESC LIMIT 1");
        if (resultSet.next()){
            return splitOrderID(resultSet.getString(1));
        }
        return splitOrderID(null);
    }

    @Override
    public boolean save(Order dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Order VALUES (?,?,?)",dto.getOrder_Id(),dto.getDate(),dto.getCustomer_Id());
    }

    @Override
    public List<Order> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(Order dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Order search(String searchId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
