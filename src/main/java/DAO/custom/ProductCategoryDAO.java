package DAO.custom;

import DAO.CrudDAO;
import Entity.ProductCategory;
import dto.tm.OrderTm;

import java.sql.SQLException;
import java.util.List;

public interface ProductCategoryDAO extends CrudDAO <ProductCategory> {
    Product searchProductByName(String searchName) throws SQLException, ClassNotFoundException;

    boolean updateProduct(List<OrderTm> orderTmList) throws SQLException, ClassNotFoundException;

    String[] getProductsByName(String searchTerm) throws SQLException, ClassNotFoundException;

}
