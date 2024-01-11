package BO.Custom;

import BO.SuperBO;
import dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    String generateNextCustomerID() throws SQLException, ClassNotFoundException;

    boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException;

    List<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException;

    boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException;

    CustomerDto searchCustomer(String searchId) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

    boolean isValidCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException;

    String[] getCustomerByID(String id) throws SQLException, ClassNotFoundException;
}
