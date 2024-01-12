package BO.Custom.impl;

import BO.Custom.OrderBO;
import DAO.DAOFactory;
import DAO.custom.OrderDAO;
import DAO.custom.OrderDetailDAO;
import DAO.custom.ProductCategoryDAO;
import Entity.Order;
import dto.OrderDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class OrderBOImpl implements OrderBO {
    ProductCategoryDAO productDAO = (ProductCategoryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRODUCT);

    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);

    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);

    @Override
    public String generateNextID() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNextID();
    }

    @Override
    public boolean placeOrder(OrderDto orderDto) throws SQLException {
        String orderId = orderDto.getOrder_Id();
        LocalDate date = orderDto.getDate();
        String customerId = orderDto.getCustomer_Id();

        Connection connection = null;
        try {
            connection = lk.ijse.db.DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSaved = orderDAO.save(new Order(orderId,date,customerId));
            if (isOrderSaved){
                boolean isUpdated = productDAO.updateProduct(orderDto.getOrderTmList());
                if (isUpdated){
                    boolean isOrderDetailSaved = orderDetailDAO.save(orderDto.getOrder_Id(),orderDto.getOrderTmList());
                    if (isOrderDetailSaved){
                        connection.commit();
                    }
                }
            }
            connection.rollback();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }
        return true;
    }
}
