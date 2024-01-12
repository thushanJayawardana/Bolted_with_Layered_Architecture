package DAO.custom.impl;

import DAO.SQLUtil;
import DAO.custom.ItemDAO;
import Entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    private String splitItemID(String currentItemID){
        if (currentItemID != null){
            String [] split = currentItemID.split("[I]");

            int id = Integer.parseInt(split[1]);
            id++;
            return String.format("I%03d",id);
        }else {
            return "I001";
        }
    }
    public String generateNextID() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT item_Id FROM Item ORDER BY item_id DESC LIMIT 1");
        if (resultSet.next()){
            return splitItemID(resultSet.getString(1));
        }
        return splitItemID(null);
    }

    public boolean save(Item entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Item VALUES (?,?,?,?,?,?,?)",entity.getItem_Id(),entity.getItem_description(),
                entity.getQty(), entity.getPrice(),entity.getSup_id(),entity.getSup_name(),entity.getMobile());
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM Item WHERE item_Id = ?",id);
    }

    public boolean update(Item entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Item SET item_description = ?, qty = ?, price = ?, sup_id = ?, sup_name = ?, mobile = ? WHERE item_Id = ?",
                entity.getItem_description(),entity.getQty(),entity.getPrice(),entity.getSup_id(),entity.getSup_name(),entity.getMobile(),
                entity.getItem_Id());
    }

    @Override
    public Item search(String searchId) throws SQLException, ClassNotFoundException {
        return null;
    }

    public List<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Item");

        ArrayList<Item> entityList = new ArrayList<>();

        while (resultSet.next()){
            entityList.add(
                    new Item(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getDouble(3),
                            resultSet.getDouble(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getInt(7)
                    )
            );
        }
        return entityList;
    }

    @Override
    public String getTotal() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT count(*) FROM Item");
        resultSet.next();
        return resultSet.getString(1);
    }
}
