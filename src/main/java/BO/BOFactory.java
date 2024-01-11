package BO;

import BO.Custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){}
    public static BOFactory getBoFactory(){
        return (boFactory == null)?boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER,EMPLOYEE,ITEMS,ORDER,PRODUCT_CATEGORY,SUPPLIER
    }
    public SuperBO getBO(BOTypes boTypes) {
        switch (boTypes) {
            case CUSTOMER:
                return new CustomerBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case ITEMS:
                return new ItemBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case PRODUCT_CATEGORY:
                return new ProductCategoryBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            default:
                return null;
        }
    }
}
