/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Views;

import hazard.Controllers.InitializableWithLoad;
import hazard.Controllers.Subviews.SingleHazardExpansionController;
import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Cause2;
import hazard.HazardClasses.Hazard2;
import hazard.HazardClasses.Kind;
import hazard.HazardClasses.Role;
import hazard.Helpers.Helper;
import hazard.Helpers.UIHelper;
import hazard.Services.DatabaseManager;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author kmoothandas
 */
public class CE_Step1Controller extends InitializableWithLoad {

    /*Hazard Table*/
    @FXML
    TableView<Hazard2> hazardTable;

    @FXML
    TableColumn<Hazard2, String> hazardId;

    @FXML
    TableColumn<Hazard2, String> hazardDescription;

    @FXML
    TableColumn<Hazard2, String> hazardCategory;

    ObservableList<Hazard2> hazardList;
    ObservableList<Hazard2> subHazardList;

    /* Role Table*/
    @FXML
    TableView<Role> roleTable;

    @FXML
    TableColumn<Role, String> roles;

    ObservableList<Role> roleList;

    /*Cause Table*/
    @FXML
    TableView<Cause2> causeTable;

    @FXML
    TableColumn<Cause2, String> causeRole;

    @FXML
    TableColumn<Cause2, String> causeDisposition;

    ObservableList<Cause2> causeList;

    /*Trackers*/
    Hazard2 currentHazard;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SetCellfactories();
        InitializeDataLists();
        Helper.PopulateHazardTable(hazardList, hazardTable);
        SetCategory();
    }

    @FXML
    void onHazardClicked(MouseEvent event) {
        int index = hazardTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            Hazard2 hazard = hazardTable.getItems().get(index);
            PopulateRoleTable(hazard);
            currentHazard = hazard;
            causeTable.setItems(null);
            SetCauseTableHeaders();
        }
    }

    @FXML
    void onRoleSelected(MouseEvent event) {
        int index = roleTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            Role role = roleTable.getItems().get(index);
            PopulateCauseTable(role);
        }
    }

    @FXML
    void onAddDisposition(ActionEvent event) {
        int index = roleTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            Role role = roleTable.getItems().get(index);
            AddCause(role);
            PopulateCauseTable(role);
        }
    }

    @FXML
    void onShowExpansion(ActionEvent event) {
        int index = hazardTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            SingleHazardExpansionController controller = new SingleHazardExpansionController(hazardTable.getItems().get(index));
            LoadController(controller, "/fxml/subviews/SingleHazardExpansion.fxml", "Hazard Expansion");
        }
    }

    void SetCellfactories() {
        this.hazardId.setCellValueFactory((TableColumn.CellDataFeatures<Hazard2, String> cellData) -> Bindings.concat("HD", cellData.getValue().getId()));
        this.hazardDescription.setCellValueFactory(new PropertyValueFactory<>("hazardDescription"));
        this.hazardCategory.setCellValueFactory(new PropertyValueFactory<>("category"));

        this.roles.setCellValueFactory(new PropertyValueFactory<>("role"));

        this.causeRole.setCellValueFactory((TableColumn.CellDataFeatures<Cause2, String> cellData)
                -> Bindings.concat(cellData.getValue().getRole().getRole()));
        this.causeDisposition.setCellValueFactory(new PropertyValueFactory<>("disposition"));
    }

    void InitializeDataLists() {
        hazardList = FXCollections.observableArrayList();
        subHazardList = FXCollections.observableArrayList();
    }

    void PopulateRoleTable(Hazard2 hazard) {
        roleList = FXCollections.observableArrayList();
        String sql = MessageFormat.format("select * from role where id in "
                + "(SELECT rootroleid FROM hazardexpansion where hazardid = {0} "
                + "UNION SELECT linkedroleid FROM hazardexpansion where hazardid = {0})", hazard.getId());
        DataBaseConnection.sql(sql, "role", roleList);
        roleTable.setItems(roleList);
        UpdateTableHeaders(hazard);
    }

    void PopulateCauseTable(Role role) {
        causeList = FXCollections.observableArrayList();
        String sql = MessageFormat.format("select * from cause2 where roleid = {0} and hazardid = {1} group by role, disposition", role.getId(), currentHazard.getId());
        DataBaseConnection.sql(sql, "cause2", causeList);
        causeTable.setItems(causeList);
    }

    void UpdateTableHeaders(Hazard2 hazard) {
        switch (hazard.getCategoryId()) {
            case 1:
            case 4:
                roles.setText("Possible Hazard Elements");
                break;
            case 2:
            case 3:
                roles.setText("Possible Initiating Roles");
                break;
        }
    }

    void AddCause(Role role) {

        Optional<String> disposition = UIHelper.CreateAddDialog("Disposition");

        if (disposition.isPresent() && disposition.get().trim().length() > 0) {
            //Insert into cause2
            String kindSql = "select * from roletoplay where roleid =" + role.getId();
            ObservableList<Kind> kindList = DatabaseManager.getAllKindForRole(kindSql);

            kindList.forEach(k -> {
                String sql = MessageFormat.format("Insert into cause2 (hazardid, roleid, role, disposition,"
                        + "environmentobjectid, environmentobject, isComplete) "
                        + "values ({0}, {1}, ''{2}'', ''{3}'', {4} , ''{5}'', 0)", currentHazard.getId(), role.getId(), role.getRole(),
                        disposition.get(), k.getId(), k.getKind());
                DataBaseConnection.insert(sql);
            });
        }
    }

    void SetCauseTableHeaders() {
        if (currentHazard.getCategoryId() == 1 || currentHazard.getCategoryId() == 4) {
            causeRole.setText("Hazard Element");
            causeDisposition.setText("Harm TruthMaker");
        } else {
            causeRole.setText("Initiating Role");
            causeDisposition.setText("Initiating Factor");
        }
    }
    
    void SetCategory(){
    }
}
