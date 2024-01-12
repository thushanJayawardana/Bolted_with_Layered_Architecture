package DAO.custom.impl;

import DAO.SQLUtil;
import DAO.custom.EmployeeDAO;
import Entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    private String splitEmployeeID(String currentEmployeeID){
        if (currentEmployeeID != null){
            String [] split = currentEmployeeID.split("[E]");

            int id = Integer.parseInt(split[1]);
            id++;
            return String.format("E%03d",id);
        }else {
            return "E001";
        }
    }

    @Override
    public String generateNextID() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT e_Id FROM Employee ORDER BY e_Id DESC LIMIT 1");
        if (resultSet.next()){
            return splitEmployeeID(resultSet.getString(1));
        }
        return splitEmployeeID(null);
    }

    @Override
    public boolean save(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Employee VALUES (?,?,?,?,?)",entity.getE_Id(),entity.getName(),
                entity.getEmail(),entity.getMobile(),entity.getPosition());
    }

    @Override
    public boolean update(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Employee SET name = ?,email = ?,mobile = ?,position = ? WHERE employee_id = ?",
                entity.getName(),entity.getEmail(),entity.getMobile(),entity.getPosition(),entity.getE_Id());
    }

    @Override
    public Employee search(String searchId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM Employee WHERE e_Id = ?",id);
    }

    @Override
    public List<Employee> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Employee");

        ArrayList<Employee> entityList = new ArrayList<>();

        while (resultSet.next()){
            entityList.add(
                    new Employee(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5)
                    )
            );
        }
        return entityList;
    }
}
