package DAO.custom;

import java.sql.SQLException;

public interface RegistrationDAO {
    boolean saveUser(RegistrationDto dto) throws SQLException, ClassNotFoundException;

    boolean isValidUser(String userName, String pw) throws SQLException, ClassNotFoundException;

    RegistrationDto getUserInfo(String userName) throws SQLException, ClassNotFoundException;

    boolean check(String userName, String pw) throws SQLException, ClassNotFoundException;
}
