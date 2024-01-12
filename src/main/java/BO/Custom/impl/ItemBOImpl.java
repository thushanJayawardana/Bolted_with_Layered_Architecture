package BO.Custom.impl;

import BO.Custom.ItemBO;
import DAO.DAOFactory;
import DAO.custom.ItemDAO;
import Entity.Item;
import dto.ItemDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEMS);

    @Override
    public String generateNextID() throws SQLException, ClassNotFoundException {
        return itemDAO.generateNextID();
    }

    @Override
    public boolean save(ItemDto dto) throws SQLException, ClassNotFoundException {
        return itemDAO.save(new Item(dto.getItem_Id(), dto.getItem_description(),dto.getQty(),dto.getPrice(),dto.getSup_id(),
                dto.getSup_name(),dto.getMobile()));
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(id);
    }

    @Override
    public boolean update(ItemDto dto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(dto.getItem_Id(), dto.getItem_description(),dto.getQty(),dto.getPrice(),dto.getSup_id(),
                dto.getSup_name(),dto.getMobile()));
    }

    @Override
    public boolean searchProductById(String searchId) throws SQLException, ClassNotFoundException {
        /*Item dto = itemDAO.search(searchId);
        if (dto != null) {
            return new ItemDto(dto.getItem_Id(), dto.getItem_description(),dto.getQty(),dto.getPrice(),dto.getSup_id(),
                    dto.getSup_name(),dto.getMobile());
        }*/
        return false;
    }

    @Override
    public List<ItemDto> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDto> itemDto = new ArrayList<>();
        List<Item> items = itemDAO.getAll();

        for (Item item : items) {
            itemDto.add(new ItemDto(item.getItem_Id(), item.getItem_description(),item.getQty(),item.getPrice(),item.getSup_id(),
                    item.getSup_name(),item.getMobile()));
        }
        return itemDto;
    }

}
