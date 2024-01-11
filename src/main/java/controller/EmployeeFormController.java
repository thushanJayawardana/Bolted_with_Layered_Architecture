package controller;

import BO.BOFactory;
import BO.Custom.EmployeeBO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import dto.EmployeeDto;
import dto.tm.EmployeeTm;
import RegExPatterns.RegExPatterns;


import java.sql.SQLException;
import java.util.List;

public class EmployeeFormController {
    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colMobile;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPosition;

    @FXML
    private Label lblEmployeeID;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<EmployeeTm> tblEmployees;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtPosition;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtmobile;

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);
    private ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();
    public void initialize(){
        generateNextEmployeeID();
        loadAllEmployees();
        setCellValueFactory();
    }

    public void generateNextEmployeeID(){
        try {
            String previousEmployeeID = lblEmployeeID.getId();
            String employeeID = employeeBO.generateNextEmployeeID();
            lblEmployeeID.setText(employeeID);
            if (btnClearPressed){
                lblEmployeeID.setText(previousEmployeeID);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private boolean btnClearPressed = false;
    public void loadAllEmployees(){
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();
        try {
            List<EmployeeDto> dtoList = employeeBO.getAllEmployees();
            for (EmployeeDto dto: dtoList ) {
                obList.add(
                        new EmployeeTm(
                                dto.getE_Id(),
                                dto.getName(),
                                dto.getEmail(),
                                dto.getMobile(),
                                dto.getPosition()
                        )
                );
            }
            tblEmployees.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("e_Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        generateNextEmployeeID();
    }

    public void clearFields(){
        txtEmployeeName.setText("");
        txtemail.setText("");
        txtmobile.setText("");;
        txtPosition.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = lblEmployeeID.getText();
        String name = txtEmployeeName.getText();
        String email = txtemail.getText();
        String mobile = txtmobile.getText();
        String position = txtPosition.getText();

        boolean isValidFirstName = RegExPatterns.getValidName().matcher(name).matches();
        boolean isValidEmail = RegExPatterns.getValidEmail().matcher(email).matches();
        boolean isValidMobile = RegExPatterns.getValidMobile().matcher(mobile).matches();
        boolean isValidPosition = RegExPatterns.getValidDescription().matcher(position).matches();

        if (!isValidFirstName){
            new Alert(Alert.AlertType.ERROR,"Cannot Delete Employee.First Name is empty").showAndWait();
            return;
        }if (!isValidEmail){
            new Alert(Alert.AlertType.ERROR,"Cannot Delete Employee.Last Name is empty").showAndWait();
            return;
        }if (!isValidMobile){
            new Alert(Alert.AlertType.ERROR,"Cannot Delete Employee.Address is empty").showAndWait();
            return;
        }if (!isValidPosition){
            new Alert(Alert.AlertType.ERROR,"Cannot Delete Employee.Phone Number is empty").showAndWait();
        } else {
            try {
                boolean isDeleted = employeeBO.deleteEmployee(id);
                if (isDeleted){
                    new Alert(Alert.AlertType.CONFIRMATION,"Employee is Deleted").show();
                    clearFields();
                    generateNextEmployeeID();
                    loadAllEmployees();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Employee is Not Deleted").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = lblEmployeeID.getText();
        String name = txtEmployeeName.getText();
        String email = txtemail.getText();
        String mobileText = txtmobile.getText();
        String position = txtPosition.getText();

        boolean isValidName = RegExPatterns.getValidName().matcher(name).matches();
        boolean isValidEmail = RegExPatterns.getValidEmail().matcher(email).matches();
        boolean isValidMobile = RegExPatterns.getValidMobile().matcher(mobileText).matches();
        boolean isValidPosition = RegExPatterns.getValidName().matcher(position).matches();

        if (!isValidName){
            new Alert(Alert.AlertType.ERROR,"Cannot Save Employee.Name is empty").showAndWait();
            return;
        }if (!isValidEmail){
            new Alert(Alert.AlertType.ERROR,"Cannot Save Employee.E-mail is empty").showAndWait();
            return;
        }if (!isValidMobile){
            new Alert(Alert.AlertType.ERROR,"Cannot Save Employee.Mobile number is empty").showAndWait();
            return;
        }if (!isValidPosition){
            new Alert(Alert.AlertType.ERROR,"Cannot Save Employee.Position is empty").showAndWait();
        }else {
            int mobile = Integer.parseInt(mobileText);
            var dto = new EmployeeDto(id, name, email, mobile ,position);
            try {
                boolean isSaved = employeeBO.saveEmployee(dto);
                if (isSaved){
                    new Alert(Alert.AlertType.CONFIRMATION,"Employee is Saved").show();
                    clearFields();
                    generateNextEmployeeID();
                    loadAllEmployees();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Employee is not Saved").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = lblEmployeeID.getText();
        String name = txtEmployeeName.getText();
        String email = txtemail.getText();
        String mobileText = txtmobile.getText();
        String position = txtPosition.getText();

        boolean isValidFirstName = RegExPatterns.getValidName().matcher(name).matches();
        boolean isValidLastName = RegExPatterns.getValidEmail().matcher(email).matches();
        boolean isValidMobile = RegExPatterns.getValidMobile().matcher(mobileText).matches();
        boolean isVaidPosition = RegExPatterns.getValidDescription().matcher(position).matches();

        if (!isValidFirstName){
            new Alert(Alert.AlertType.ERROR,"Cannot Update Employee.First Name is empty").showAndWait();
            return;
        }if (!isValidLastName){
            new Alert(Alert.AlertType.ERROR,"Cannot Update Employee.Last Name is empty").showAndWait();
            return;
        }if (!isValidMobile){
            new Alert(Alert.AlertType.ERROR,"Cannot Update Employee.Address is empty").showAndWait();
            return;
        }if (!isVaidPosition){
            new Alert(Alert.AlertType.ERROR,"Cannot Update Employee.Phone Number is empty").showAndWait();
        } else {
            int mobile = Integer.parseInt(mobileText);
            var dto = new EmployeeDto(id,name,email,mobile,position);
            try {
                boolean isUpdated = employeeBO.updateEmployee(dto);
                if (isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION,"Employee is Updated").show();
                    clearFields();
                    generateNextEmployeeID();
                    loadAllEmployees();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Employee is not Updated").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    @FXML
    void txtEmployeeNameOnAction(ActionEvent event) {

    }

    @FXML
    void txtPositionOnAction(ActionEvent event) {

    }

    @FXML
    void txtSearchOnAction(KeyEvent event) {
        searchTableFilter();
    }
    private void searchTableFilter() {
        FilteredList<EmployeeTm> filterEmployeeTbl = new FilteredList<>(obList, b -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterEmployeeTbl.setPredicate(employeeTm -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String search = newValue.toLowerCase();

                if (employeeTm.getE_Id().toLowerCase().contains(search)) {
                    return true;
                } else if (employeeTm.getE_Id().toLowerCase().contains(search)) {
                    return true;
                } else if (employeeTm.getName().toLowerCase().contains(search)) {
                    return true;
                } else if (employeeTm.getName().toLowerCase().contains(search)) {
                    return true;
                } else if (employeeTm.getEmail().toLowerCase().contains(search)) {
                    return true;
                } else if (employeeTm.getEmail().toLowerCase().contains(search)) {
                    return true;
                } else if (employeeTm.getMobile().toLowerCase().contains(search)) {
                    return true;
                } else if (employeeTm.getMobile().toLowerCase().contains(search)) {
                    return true;
                } else if (employeeTm.getPosition().toLowerCase().contains(search)) {
                    return true;
                } else if (employeeTm.getPosition().toLowerCase().contains(search)) {
                    return true;
                }else
                    return false;
            });
        });
        SortedList<EmployeeTm> sortCustomerTbl = new SortedList<>(filterEmployeeTbl);
        sortCustomerTbl.comparatorProperty().bind(tblEmployees.comparatorProperty());
        tblEmployees.setItems(sortCustomerTbl);
    }

    @FXML
    void txtemailOnAction(ActionEvent event) {

    }

    @FXML
    void txtmobileOnAction(ActionEvent event) {

    }

}
