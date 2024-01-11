package BO.Custom.impl;

import BO.Custom.EmployeeBO;
import DAO.DAOFactory;
import DAO.custom.EmployeeDAO;
import Entity.Employee;
import dto.EmployeeDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);

    @Override
    public String generateNextEmployeeID() throws SQLException, ClassNotFoundException {
        return employeeDAO.generateNextID();
    }

    @Override
    public boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(new Employee(dto.getE_Id(),dto.getName(),dto.getEmail(),dto.getMobile(),dto.getPosition()));
    }

    @Override
    public boolean updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(dto.getE_Id(),dto.getName(),dto.getEmail(),dto.getMobile(),dto.getPosition()));
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(id);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDto> employeeDto = new ArrayList<>();
        List<Employee> employees = employeeDAO.getAll();

        for (Employee employee : employees) {
            employeeDto.add(new EmployeeDto(employee.getE_Id(),employee.getName(),employee.getEmail(),employee.getMobile(),employee.getPosition()));
        }
        return employeeDto;
    }

}
