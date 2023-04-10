package lk.ise.posre.control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ise.posre.bo.BoFactory;
import lk.ise.posre.bo.custom.CustomerBo;
import lk.ise.posre.dao.DataAccessCode;
import lk.ise.posre.db.Database;
import lk.ise.posre.dto.CustomerDto;
import lk.ise.posre.entity.Customer;
import lk.ise.posre.enums.BoType;
import lk.ise.posre.view.tm.CustomerTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class CustomerFormController {


    public JFXButton btnBack;
    public JFXButton btnAddNewCustomer;
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtSearch;
    public JFXTextField txtAddress;
    public JFXTextField txtSalary;
    public JFXButton btnCommon;
    public TableView tblCustomer;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colSalary;
    public TableColumn colOption;
    public AnchorPane customerFormContext;
    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    public void initialize() throws Exception {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        tblCustomer.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {

            if (newValue != null){
                setData((CustomerTM) newValue);
            }
        }));
        //loadAll("");
        customerBo.findAllCustomers();
    }

    private void setData(CustomerTM newValue) {
        txtId.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtAddress.setText(newValue.getAddress());
        txtSalary.setText(String.valueOf(newValue.getSalary()));
        btnCommon.setText("Update Customer");

    }

    public void backToDashboardOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) customerFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
        stage.centerOnScreen();
    }

    public void addNewCustomerOnAction(ActionEvent actionEvent) {
        clearData();
        btnCommon.setText("Save Customer");
    }


    public void saveCustomerOnAction(ActionEvent actionEvent) throws Exception {
        CustomerDto c1 = new CustomerDto(txtId.getText(),txtName.getText(),txtAddress.getText(),Double.parseDouble(txtSalary.getText()));

        if(btnCommon.getText().equals("Save Customer")){
            if(customerBo.saveCustomer(c1)){
                new Alert(Alert.AlertType.INFORMATION,"Customer Saved!").show();
                loadAll("");
            }else {
                new Alert(Alert.AlertType.WARNING,"Something went wrong!").show();
            }
            /*Database.customers.add(c1);
            new Alert(Alert.AlertType.INFORMATION,"Customer saved").show();
            loadAll("");*/
        }
        else {///Update customer

            if(customerBo.updateCustomer(c1)){
                new Alert(Alert.AlertType.INFORMATION,"Customer Updated!").show();
                loadAll("");
            }else {
                new Alert(Alert.AlertType.WARNING,"Something went wrong").show();
            }

        }
        clearData();
    }

    private void clearData() {
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();
    }
    private void loadAll(String searchText) throws Exception {
        ObservableList<CustomerTM> tmList = FXCollections.observableArrayList();

        for (CustomerDto c:customerBo.findAllCustomers()
             ) {
            Button btn =new Button("Delete");
            CustomerTM customerTM = new CustomerTM(c.getId(),c.getName(),c.getAddress(),c.getSalary(),btn);

            btn.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?", ButtonType.YES,ButtonType.NO);
                Optional<ButtonType> type = alert.showAndWait();
                if(type.get()==ButtonType.YES){
                    try {
                        if (customerBo.deleteCustomer(c.getId())){
                            new Alert(Alert.AlertType.INFORMATION,"Customer deleted");
                            loadAll("");
                        }else{
                            new Alert(Alert.AlertType.WARNING,"Something went wrong");
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            tmList.add(customerTM);
        }
        tblCustomer.setItems(tmList);
    }


}
