package BO.Custom.impl;

import BO.Custom.ProductCategoryBO;
import DAO.DAOFactory;
import DAO.custom.ProductCategoryDAO;
import Entity.ProductCategory;
import dto.ProductCategoryDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryBOImpl implements ProductCategoryBO {
    ProductCategoryDAO productDAO = (ProductCategoryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRODUCT_CATEGORY);

    @Override
    public String generateNextProductID() throws SQLException, ClassNotFoundException {
        return productDAO.generateNextID();
    }

    @Override
    public boolean saveProduct(ProductCategoryDto dto) throws SQLException, ClassNotFoundException {
        return productDAO.save(new ProductCategory(dto.getP_Id(), dto.getName(), dto.getType(), dto.getQty(), dto.getPrice()));
    }

    @Override
    public boolean updateProduct(ProductCategoryDto dto) throws SQLException, ClassNotFoundException {
        return productDAO.save(new ProductCategory(dto.getP_Id(), dto.getName(), dto.getType(), dto.getQty(), dto.getPrice()));
    }

    @Override
    public ProductCategoryDto searchProduct(String searchId) throws SQLException, ClassNotFoundException {
        ProductCategory product = productDAO.search(searchId);
        if (product != null) {
            return new ProductCategoryDto(product.getProduct_id(),product.getDescription(),product.getQty(),product.getPrice());
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteProduct(String itemId) throws SQLException, ClassNotFoundException {
        return productDAO.delete(itemId);
    }

    @Override
    public List<ProductCategoryDto> getAllProducts() throws SQLException, ClassNotFoundException {
        ArrayList<ProductCategoryDto> productDto = new ArrayList<>();
        List<ProductCategory> products = productDAO.getAll();

        for (ProductCategory product : products) {
            productDto.add(new ProductCategoryDto(product.getProduct_id(),product.getDescription(),product.getQty(),product.getPrice()));
        }
        return productDto;
    }

}
