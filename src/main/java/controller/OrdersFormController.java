package controller;

import BO.BOFactory;
import BO.Custom.CustomerBO;
import BO.Custom.OrderBO;
import BO.Custom.ProductCategoryBO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import dto.CustomerDto;
import dto.OrderDto;
import dto.ProductCategoryDto;
import dto.tm.OrderTm;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import RegExPatterns.RegExPatterns;
import java.util.List;

public class OrdersFormController {
    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colOrderID;

    @FXML
    private TableColumn<?, ?> colProductID;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colTotalPrice;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private Label lblCustomerID;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderID;

    @FXML
    private Label lblProductDesc;

    @FXML
    private Label lblProductID;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private TableView<OrderTm> tblOrders;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSearchCustomerID;

    @FXML
    private TextField txtSearchProductIID;
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    ProductCategoryBO productsBO = (ProductCategoryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRODUCTS);
    OrderBO orderDAO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);
    private ObservableList<OrderTm> obList = FXCollections.observableArrayList();

    public void initialize() throws SQLException {
        setCellValueFactory();
        generateNextOrderID();
        generateNextCustomerID();
        lblOrderDate.setText(String.valueOf(LocalDate.now()));
    }

    private void generateNextCustomerID() {
        try {
            String previousOrderID = lblCustomerID.getText();
            String orderID = customerBO.generateNextCustomerID();
            lblCustomerID.setText(orderID);
            if (orderID != null) {
                lblCustomerID.setText(orderID);
            }
            clearFields();
            if (btnClearPressed){
                lblCustomerID.setText(previousOrderID);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    private void generateNextOrderID() {
        try {
            String previousOrderID = lblOrderID.getText();
            String orderID = OrderBO.generateNextID();
            lblOrderID.setText(orderID);
            if (orderID != null) {
                lblOrderID.setText(orderID);
            }
            clearFields();
            if (btnClearPressed){
                lblOrderID.setText(previousOrderID);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    private boolean btnClearPressed = false;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        generateNextOrderID();
    }

    private void clearFields() {
        lblProductDesc.setText("");
        lblUnitPrice.setText("");
        lblQtyOnHand.setText("");
        lblCustomerName.setText("");
        txtQty.setText("");
    }

    private void setCellValueFactory() {
        colOrderID.setCellValueFactory(new PropertyValueFactory<>("order_Id"));
        colProductID.setCellValueFactory(new PropertyValueFactory<>("product_Id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unit_Price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String order_Id = lblOrderID.getText();
        String product_Id = lblProductID.getText();
        String desc = lblProductDesc.getText();
        double unit_Price = Double.parseDouble(lblUnitPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double total = unit_Price * qty;
        Button btn = new Button("Delete");

        btn.setCursor(Cursor.HAND);

        if (!obList.isEmpty()){
            for (int i = 0; i < tblOrders.getItems().size(); i++) {
                if (colProductID.getCellData(i).equals(product_Id)){
                    int col_qty = (int) colQuantity.getCellData(i);
                    qty += col_qty;
                    total = unit_Price * qty;

                    obList.get(i).setQty(qty);
                    obList.get(i).setTotal(total);

                    calculateTotal();
                    tblOrders.refresh();
                    return;
                }
            }
        }
        var OrderTm = new OrderTm(order_Id,product_Id,desc,unit_Price,qty,total);
        obList.add(OrderTm);

        tblOrders.setItems(obList);
        calculateTotal();
    }

    private void calculateTotal() {
        double total = 0;
        for (int i = 0; i < tblOrders.getItems().size(); i++) {
            total += (double) colTotalPrice.getCellData(i);
        }
    }


    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String orderID = lblOrderID.getText();
        LocalDate date = LocalDate.parse(lblOrderDate.getText());
        String customerID = lblCustomerID.getText();

        List<OrderTm> orderTmList = new ArrayList<>();
        for (int i = 0; i < tblOrders.getItems().size(); i++) {
            OrderTm orderTm = obList.get(i);
            orderTmList.add(orderTm);
        }
        var customerDto = new CustomerDto(customerID,null,null,null);
        var orderDto = new OrderDto(orderID, date ,customerID ,orderTmList);
        try {
            boolean checkCustomerID = customerBO.isValidCustomer(customerDto);
            if (!checkCustomerID){
                customerBO.saveCustomer(customerDto);
            }
            boolean isSuccess = orderDAO.placeOrder(orderDto);
            if (isSuccess){
                new Alert(Alert.AlertType.CONFIRMATION,"Order is Saved").show();
                obList.clear();
                calculateTotal();
                generateNextOrderID();
                generateNextCustomerID();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {

    }

    @FXML
    void txtSearchCustomerOnAction(ActionEvent event) {
        String searchCustomer = txtSearchCustomerID.getText();

        try {
            CustomerDto customerDto;
                customerDto = customerBO.searchCustomer(searchCustomer);

            if (customerDto != null) {
                lblCustomerID.setText(customerDto.getCust_Id());
                lblCustomerName.setText(customerDto.getName());
                txtSearchCustomerID.setText("");
            } else {
                generateNextCustomerID();
                new Alert(Alert.AlertType.INFORMATION, "Customer not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchProductOnAction(ActionEvent event) {

        }
}

