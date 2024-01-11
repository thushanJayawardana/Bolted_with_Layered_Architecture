package BO.Custom.impl;

import BO.Custom.SupplierBO;
import DAO.DAOFactory;
import DAO.custom.SupplierDAO;
import Entity.Supplier;
import dto.SupplierDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIERS);

    @Override
    public String generateNextSupplierID() throws SQLException, ClassNotFoundException {
        return supplierDAO.generateNextID();
    }

    @Override
    public boolean saveSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(new Supplier(dto.getSup_Id(),dto.getName(),dto.getProducts(),dto.getMobile(),dto.getDate()));
    }

    @Override
    public List<SupplierDto> getAllSuppliers() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierDto> supplierDto = new ArrayList<>();
        List<Supplier> suppliers = supplierDAO.getAll();

        for (Supplier supplier : suppliers) {
            supplierDto.add(new SupplierDto(supplier.getSup_Id(),supplier.getName(),supplier.getProducts(), supplier.getMobile(), supplier.getDate()));
        }
        return supplierDto;
    }

    @Override
    public SupplierDto searchSupplier(String searchId) throws SQLException, ClassNotFoundException {
        Supplier supplier = supplierDAO.search(searchId);
        if (supplier != null) {
            return new SupplierDto(supplier.getSup_Id(),supplier.getName(),supplier.getProducts(), supplier.getMobile(), supplier.getDate());
        } else {
            return null;
        }
    }

    @Override
    public SupplierDto searchSupplierByPhoneNumber(String searchPhoneNumber) throws SQLException, ClassNotFoundException {
        Supplier supplier = supplierDAO.search(searchPhoneNumber);
        if (supplier != null) {
            return new SupplierDto(supplier.getSup_Id(),supplier.getName(),supplier.getProducts(), supplier.getMobile(), supplier.getDate());
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(id);
    }

    @Override
    public boolean updateSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(dto.getSup_Id(),dto.getName(),dto.getProducts(),dto.getMobile(),dto.getDate()));
    }

}
