package DAO.custom;

import DAO.CrudDAO;
import Entity.Customer;

import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> {
    boolean isValidCustomer(Customer customer) throws SQLException, ClassNotFoundException;

    String[] getCustomerByPhoneNumber(String phone) throws SQLException, ClassNotFoundException;

    String[] getCustomerByID(String id) throws SQLException, ClassNotFoundException;

    String getCountOFLoyalty() throws SQLException, ClassNotFoundException;

}
