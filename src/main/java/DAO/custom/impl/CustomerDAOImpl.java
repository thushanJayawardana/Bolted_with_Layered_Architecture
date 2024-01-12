package DAO.custom.impl;

import DAO.SQLUtil;
import DAO.custom.CustomerDAO;
import Entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    private String splitCustomerID(String currentCustomerID) {
        if (currentCustomerID != null) {
            String[] split = currentCustomerID.split("[C]");

            int id = Integer.parseInt(split[1]);
            id++;
            return String.format("C%03d", id);
        } else {
            return "C001";
        }
    }

    @Override
    public String generateNextID() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT cust_Id FROM Customer ORDER BY cust_Id DESC LIMIT 1");
        if (resultSet.next()){
            return splitCustomerID(resultSet.getString(1));
        }
        return splitCustomerID(null);
    }

    @Override
    public boolean save(Customer dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Customer VALUES (?,?,?,?)",dto.getCust_Id(),dto.getName(),
                dto.getAddress(),dto.getMobile());
    }

    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Customer");

        ArrayList<Customer> dtoList = new ArrayList<>();
        while (resultSet.next()){
            if (resultSet.getString(2) != null
                    || resultSet.getString(3) != null
                    || resultSet.getString(4) != null) {
                dtoList.add(
                        new Customer(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4)));
            }
        }
        return dtoList;
    }

    @Override
    public boolean update(Customer dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Customer SET name = ?,address = ?,mobile = ? WHERE cust_Id =?",
                dto.getName(),dto.getAddress(),dto.getMobile(),dto.getCust_Id());
    }

    @Override
    public Customer search(String searchId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Customer WHERE cust_Id = ?",searchId);

        Customer dto = null;
        if (resultSet.next()){
            String customer_id = resultSet.getString(1);
            String customer_name = resultSet.getString(2);
            String customer_address = resultSet.getString(3);
            String customer_phoneNumber = resultSet.getString(4);

            dto = new Customer(customer_id,customer_name,customer_address,customer_phoneNumber);
        }
        return dto;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM Customer WHERE cust_Id = ?",id);
    }

    @Override
    public boolean isValidCustomer(Customer dto) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Customer WHERE cust_Id = ?",dto.getCust_Id());
        return resultSet.next();
    }


    @Override
    public String[] getCustomerByID(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Customer WHERE cust_Id LIKE ?","%" +id+ "%");

        List<String> customerPhoneNumbers = new ArrayList<>();
        while (resultSet.next()) {
            String ID = resultSet.getString(1);
            customerPhoneNumbers.add(ID);
        }
        return customerPhoneNumbers.toArray(new String[0]);
    }
}
