package DAO.custom;

import DAO.SuperDAO;
import dto.DetailsDto;

import java.sql.SQLException;
import java.util.List;

public interface JoingQueryDAO  extends SuperDAO {
    List<DetailsDto> getAllDetails() throws SQLException, ClassNotFoundException;

}
