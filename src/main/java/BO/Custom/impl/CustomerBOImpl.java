package BO.Custom.impl;

import BO.Custom.CustomerBO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.DAOFactory;
import DAO.custom.CustomerDAO;
import Entity.Customer;
import dto.CustomerDto;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public String generateNextCustomerID() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNextID();
    }

    @Override
    public boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(dto.getCust_Id(),dto.getName(),dto.getAddress(),dto.getMobile()));
    }

    @Override
    public List<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDto> customerDto = new ArrayList<>();
        List<Customer> customers = customerDAO.getAll();

        for (Customer customer : customers) {
            customerDto.add(new CustomerDto(customer.getCust_Id(),customer.getName(),customer.getAddress(),customer.getMobile()));
        }
        return customerDto;
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getCust_Id(),dto.getName(),dto.getAddress(),dto.getMobile()));
    }

    @Override
    public CustomerDto searchCustomer(String searchId) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(searchId);
        if (customer != null) {
            return new CustomerDto(customer.getCust_Id(),customer.getName(),customer.getAddress(),customer.getMobile());
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public boolean isValidCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.isValidCustomer(new Customer(dto.getCust_Id(),dto.getName(),dto.getAddress(),dto.getMobile()));
    }

    @Override
    public String[] getCustomerByID(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.getCustomerByID(id);
    }

}
