package BO.Custom;

import BO.SuperBO;
import dto.ItemDto;

import java.sql.SQLException;
import java.util.List;

public interface ItemBO extends SuperBO {
    String generateNextID() throws SQLException, ClassNotFoundException;

    boolean save(ItemDto dto) throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    boolean update(ItemDto dto) throws SQLException, ClassNotFoundException;
    boolean searchProductById(String searchId) throws SQLException, ClassNotFoundException;

    List<ItemDto> getAll() throws SQLException, ClassNotFoundException;

}
