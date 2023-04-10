
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
import lk.ise.posre.bo.custom.ItemBo;
import lk.ise.posre.dao.DataAccessCode;
import lk.ise.posre.db.Database;
import lk.ise.posre.dto.ItemDto;
import lk.ise.posre.entity.Customer;
import lk.ise.posre.entity.Item;
import lk.ise.posre.enums.BoType;
import lk.ise.posre.view.tm.CustomerTM;
import lk.ise.posre.view.tm.ItemTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ItemFormController {

    public JFXButton btnBack;
    public JFXButton btnShowItems;
    public JFXButton btnAddNewItem;
    public JFXTextField txtCode;
    public JFXTextField txtDescription;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtSearch;
    public TableView tblItem;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TableColumn colOption;
    public JFXButton btnCommon;
    public AnchorPane itemFormContext;

    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    public void initialize(){
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        tblItem.getSelectionModel().selectedItemProperty().addListener(((observable,oldValue,newValue)->{

            if(newValue != null){
                setData((ItemTM) newValue);
            }
        }));
    }

    private void setData(ItemTM newValue) {
        txtCode.setText(newValue.getCode());
        txtDescription.setText(newValue.getDescription());
        txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(newValue.getQtyOnHand()));
        btnCommon.setText("Update Item");

    }

    public void backToDashboardOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) itemFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
        stage.centerOnScreen();
    }

    public void showItemsOnAction(ActionEvent actionEvent) throws Exception {
        //loadAll("");
        itemBo.findAllItems();
    }

    public void addNewItemOnAction(ActionEvent actionEvent) {
        clearData();
        btnCommon.setText("Save Item");
    }

    private void clearData() {
        txtCode.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
    }
    public void saveItemOnAction(ActionEvent actionEvent) throws Exception {
        ItemDto i1 = new ItemDto(txtCode.getText(),txtDescription.getText(),Double.parseDouble(txtUnitPrice.getText()),Integer.parseInt(txtQtyOnHand.getText()));
        if(btnCommon.getText().equals("Save Item")){

            if (itemBo.saveItem(i1)){
                new Alert(Alert.AlertType.INFORMATION,"Item saved").show();
                loadAll("");
            }else {
                new Alert(Alert.AlertType.WARNING,"Something went wrong!").show();
            }
        }else{
            if(itemBo.updateItem(i1)){
                new Alert(Alert.AlertType.INFORMATION,"Item updated!");
            }else {
                new Alert(Alert.AlertType.WARNING,"Something went wrong!");
            }
        }
        clearData();

    }
    private void loadAll(String searchText) throws Exception {
        ObservableList<ItemTM> tmList = FXCollections.observableArrayList();

        for (ItemDto i: itemBo.findAllItems()
        ) {
            Button btn =new Button("Delete");
            ItemTM itemTM= new ItemTM(i.getCode(),i.getDescription(),i.getUnitPrice(),i.getQtyOnHand(),btn);

            btn.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?", ButtonType.YES,ButtonType.NO);
                Optional<ButtonType> type = alert.showAndWait();
                if(type.get()==ButtonType.YES){
                    try {
                        if (itemBo.deleteItem(i.getCode())){
                            new Alert(Alert.AlertType.INFORMATION,"Item Deleted").show();
                            loadAll("");
                        }
                        else{
                            new Alert(Alert.AlertType.WARNING,"Something went wrong!");
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

            tmList.add(itemTM);

        }
        tblItem.setItems(tmList);

    }
}


   
   
