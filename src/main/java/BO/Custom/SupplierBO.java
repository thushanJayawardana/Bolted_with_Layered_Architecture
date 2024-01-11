package BO.Custom;

import BO.SuperBO;
import dto.SupplierDto;

import java.sql.SQLException;
import java.util.List;

public interface SupplierBO extends SuperBO {
    String generateNextSupplierID() throws SQLException, ClassNotFoundException;

    boolean saveSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException;

    List<SupplierDto> getAllSuppliers() throws SQLException, ClassNotFoundException;

    SupplierDto searchSupplier(String searchId) throws SQLException, ClassNotFoundException;

    boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException;

    boolean updateSupplier(SupplierDto dto) throws SQLException, ClassNotFoundException;
}
