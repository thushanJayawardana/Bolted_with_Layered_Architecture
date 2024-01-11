package BO.Custom;

import BO.SuperBO;
import dto.ProductCategoryDto;

import java.sql.SQLException;
import java.util.List;

public interface ProductCategoryBO extends SuperBO {
    String generateNextProductID() throws SQLException, ClassNotFoundException;

    boolean saveProduct(ProductCategoryDto dto) throws SQLException, ClassNotFoundException;

    boolean updateProduct(ProductCategoryDto dto) throws SQLException, ClassNotFoundException;

    ProductDto searchProduct(String searchId) throws SQLException, ClassNotFoundException;

    ProductDto searchProductByName(String searchName) throws SQLException, ClassNotFoundException;

    boolean deleteProduct(String itemId) throws SQLException, ClassNotFoundException;

    List<ProductDto> getAllProducts() throws SQLException, ClassNotFoundException;

    String[] getProductsByName(String searchTerm) throws SQLException, ClassNotFoundException;
}
