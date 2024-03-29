package controller;

import BO.BOFactory;
import BO.Custom.ItemBO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import dto.ItemDto;
import dto.SupplierDto;
import dto.tm.ItemTm;
import BO.Custom.SupplierBO;
import RegExPatterns.RegExPatterns;


import java.sql.SQLException;
import java.util.List;

public class ItemFormController {
    @FXML
    private TableColumn<?, ?> colItemDesc;

    @FXML
    private TableColumn<?, ?> colItemID;

    @FXML
    private TableColumn<?, ?> colItemPrice;

    @FXML
    private TableColumn<?, ?> colItemQty;

    @FXML
    private TableColumn<?, ?> colSupplierID;

    @FXML
    private TableColumn<?, ?> colSupplierName;

    @FXML
    private Label lblItemID;

    @FXML
    private Label lblSupID;

    @FXML
    private Label lblSupplierMobileNo;

    @FXML
    private Label lblSupplierName;

    @FXML
    private TableView<ItemTm> tblItem;

    @FXML
    private TextField txtItemDescription;

    @FXML
    private TextField txtItemPrice;

    @FXML
    private TextField txtItemQuantity;

    @FXML
    private TextField txtItemSearch;

    @FXML
    private TextField txtSearch;

    @FXML
    private AnchorPane rootNode;

    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEMS);
    SupplierBO suppliersBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);
    private ObservableList<ItemTm> obList = FXCollections.observableArrayList();
    public void initialize(){
        generateNextItemID();
        loadAllItems();
        setValueFactory();
    }
    private void generateNextItemID() {
        try {
            String previousItemID = lblItemID.getText();
            String itemID = itemBO.generateNextID();
            lblItemID.setText(itemID);
            if (btnClearPressed){
                lblItemID.setText(previousItemID);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean btnClearPressed = false;

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
        generateNextItemID();
    }
    private void clearFields(){
        txtItemDescription.setText("");
        txtItemQuantity.setText("");
        txtItemPrice.setText("");
        txtSearch.setText("");
        txtItemSearch.setText("");
        lblSupID.setText("");
        lblSupplierName.setText("");
        lblSupplierMobileNo.setText("");
    }

    private void setValueFactory() {
        colItemID.setCellValueFactory(new PropertyValueFactory<>("item_Id"));
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("sup_id"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("sup_name"));
        colItemDesc.setCellValueFactory(new PropertyValueFactory<>("item_description"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colItemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    private void loadAllItems() {
        ObservableList<ItemTm> obList = FXCollections.observableArrayList();
        try {
            List<ItemDto> dtoList = itemBO.getAll();
            for (ItemDto dto : dtoList){
                obList.add(
                        new ItemTm(
                                dto.getItem_Id(),
                                dto.getSup_id(),
                                dto.getSup_name(),
                                dto.getItem_description(),
                                dto.getQty(),
                                dto.getPrice()
                        )
                );
            }
            tblItem.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = lblItemID.getText();
        String product_desc = txtItemDescription.getText();
        String qtyText = txtItemQuantity.getText();
        String PriceText = txtItemPrice.getText();

        boolean isValidName = RegExPatterns.getValidName().matcher(product_desc).matches();
        boolean isValidQty = RegExPatterns.getValidDouble().matcher(qtyText).matches();
        boolean isValidPrice = RegExPatterns.getValidDouble().matcher(PriceText).matches();


        if (!isValidName){
            new Alert(Alert.AlertType.ERROR,"Product Name Cn not be Empty").showAndWait();
            return;
        }if (!isValidQty){
            new Alert(Alert.AlertType.ERROR,"Not a Valid Quantity").showAndWait();
        }if (!isValidPrice){
            new Alert(Alert.AlertType.ERROR,"Not a Valid Price").showAndWait();
        }
        else {
            try {
                boolean isDeleted = itemBO.delete(id);
                if (isDeleted){
                    new Alert(Alert.AlertType.CONFIRMATION,"Item is Deleted").show();
                    clearFields();
                    generateNextItemID();
                    loadAllItems();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Item is not Deleted").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = lblItemID.getText();
        String product_desc = txtItemDescription.getText();
        String qtyText = txtItemQuantity.getText();
        String priceText = txtItemPrice.getText();
        String sup_id = lblSupID.getText();
        String name = lblSupplierName.getText();
        int mobile = Integer.parseInt(lblSupplierMobileNo.getText());

        boolean isValidName = RegExPatterns.getValidName().matcher(product_desc).matches();
        boolean isValidQty = RegExPatterns.getValidDouble().matcher(qtyText).matches();
        boolean isValidPrice = RegExPatterns.getValidDouble().matcher(priceText).matches();

        if (!isValidName){
            new Alert(Alert.AlertType.ERROR,"Not a Valid Product Name").showAndWait();
            return;
        }if (!isValidQty){
            new Alert(Alert.AlertType.ERROR,"Not a Valid Quantity").showAndWait();
        }
        if (!isValidPrice){
            new Alert(Alert.AlertType.ERROR,"Not a Valid Price").showAndWait();
        }
        else {
            try {
                double qty = Double.parseDouble(qtyText);
                double price = Double.parseDouble(qtyText);

                var dto = new ItemDto(id,product_desc,qty,price,sup_id,name,mobile);
                try {
                    boolean isSaved = itemBO.save(dto);
                    if (!isSaved){
                        new Alert(Alert.AlertType.CONFIRMATION,"Item is Saved").show();
                        clearFields();
                        generateNextItemID();
                        loadAllItems();
                    }else {
                        new Alert(Alert.AlertType.ERROR,"Item is not Saved").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.CONFIRMATION,"Item is Saved").show();
                }
            }catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = lblItemID.getText();
        String product_desc = txtItemDescription.getText();
        String qtyText = txtItemQuantity.getText();
        String priceText = txtItemPrice.getText();
        String s_id = lblSupID.getText();
        String name = lblSupplierName.getText();
        int mobile = Integer.parseInt(lblSupplierMobileNo.getText());

        boolean isValidName = RegExPatterns.getValidName().matcher(product_desc).matches();
        boolean isValidQty = RegExPatterns.getValidDouble().matcher(qtyText).matches();
        boolean isValidPrice = RegExPatterns.getValidDouble().matcher(priceText).matches();


        if (!isValidName){
            new Alert(Alert.AlertType.ERROR,"Not a Valid Product Name").showAndWait();
            return;
        }if (!isValidQty){
            new Alert(Alert.AlertType.ERROR,"Not a Valid Quantity").showAndWait();
        }if (!isValidPrice){
            new Alert(Alert.AlertType.ERROR,"Not a Valid Price").showAndWait();
        }
        else {
            try {
                double qty = Double.parseDouble(qtyText);
                double price = Double.parseDouble(qtyText);

                var dto = new ItemDto(id,product_desc,qty,price,s_id,name,mobile);
                try {
                    boolean isUpdated = itemBO.update(dto);
                    if (isUpdated){
                        new Alert(Alert.AlertType.CONFIRMATION,"Item is Updated").show();
                        clearFields();
                        generateNextItemID();
                        loadAllItems();
                    }else {
                        new Alert(Alert.AlertType.ERROR,"Item is not Updated").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
                }
            }catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    @FXML
    void txtItemDescriptionOnAction(ActionEvent event) {

    }

    @FXML
    void txtItemPriceOnAction(ActionEvent event) {

    }

    @FXML
    void txtItemQuantityOnAction(ActionEvent event) {txtItemQuantity.requestFocus();}

    @FXML
    void txtItemSearchOnAction(ActionEvent event) {
        String searchInput= txtItemSearch.getText();
        try {
            ItemDto itemDto;
                itemDto = itemBO.searchProductById(searchInput);
            if (itemDto != null ){
                lblItemID.setText(itemDto.getItem_Id());
                txtItemDescription.setText(itemDto.getItem_description());
                txtItemQuantity.setText(String.valueOf(itemDto.getQty()));
                txtItemPrice.setText(String.valueOf(itemDto.getQty()));
                lblSupID.setText(itemDto.getSup_id());
                lblSupplierName.setText(itemDto.getSup_name());
                lblSupplierMobileNo.setText(String.valueOf(itemDto.getMobile()));
            }else {
                lblItemID.setText("");
                generateNextItemID();
                new Alert(Alert.AlertType.CONFIRMATION,"Product Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void txtSupplierSearchOnAction(ActionEvent event) {
        String searchInput = txtSearch.getText();
        try {
            SupplierDto supplierDto;
                supplierDto = suppliersBO.searchSupplier(searchInput);
            if (supplierDto != null){
                lblSupID.setText(supplierDto.getSup_Id());
                lblSupplierName.setText(supplierDto.getName());
                lblSupplierMobileNo.setText(supplierDto.getMobile());
                txtSearch.setText("");
            } else {
                lblSupID.setText("");
                lblSupplierName.setText("");
                lblSupplierMobileNo.setText("");
                new Alert(Alert.AlertType.INFORMATION,"Supplier Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
}
