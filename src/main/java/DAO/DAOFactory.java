package DAO;

import DAO.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        CUSTOMER,EMPLOYEE,ITEMS,QUERY,ORDER,ORDER_DETAILS,PRODUCT_CATEGORY,SUPPLIERS
    }

    public SuperDAO getDAO (DAOTypes daoTypes) {
        switch (daoTypes) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case ITEMS:
                return new ItemDAOImpl();
            case QUERY:
                return new JoinQueryDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDER_DETAILS:
                return new OrderDetailDAOImpl();
            case PRODUCT_CATEGORY:
                return new ProductCategoryDAOImpl();
            case SUPPLIERS:
                return new SupplierDAOImpl();
            default:
                return null;
        }
    }
}
