/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Views;

import hazard.Controllers.Subviews.SingleHazardExpansionController;
import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Cause2;
import hazard.HazardClasses.Hazard2;
import hazard.HazardClasses.Role;
import hazard.Helpers.UIHelper;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author kmoothandas
 */
public class CauseExplorationController implements Initializable {

    /*Hazard Table*/
    @FXML
    private TableView<Hazard2> hazardTable;

    @FXML
    private TableColumn<Hazard2, String> hazardId;

    @FXML
    private TableColumn<Hazard2, String> hazardDescription;

    @FXML
    private TableColumn<Hazard2, String> hazardCategory;

    private ObservableList<Hazard2> hazardList;

    /* Role Table*/
    @FXML
    private TableView<Role> roleTable;

    @FXML
    private TableColumn<Role, String> roles;

    private ObservableList<Role> roleList;

    /*Cause Table*/
    @FXML
    private TableView<Cause2> causeTable;

    @FXML
    private TableColumn<Cause2, String> causeRole;

    @FXML
    private TableColumn<Cause2, String> causeDisposition;

    private ObservableList<Cause2> causeList;

    /*Trackers*/
    private Hazard2 currentHazard;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SetCellfactories();
        PopulateHazardTable();
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
        System.out.println("hazard.Controllers.Views.CE_ICHA1Controller.onRoleSelected(): " + index);
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

    private void SetCellfactories() {
        this.hazardId.setCellValueFactory((TableColumn.CellDataFeatures<Hazard2, String> cellData) -> Bindings.concat("HD", cellData.getValue().getId()));
        this.hazardDescription.setCellValueFactory(new PropertyValueFactory<>("hazardDescription"));
        this.hazardCategory.setCellValueFactory(new PropertyValueFactory<>("category"));

        this.roles.setCellValueFactory(new PropertyValueFactory<>("role"));

        this.causeRole.setCellValueFactory((TableColumn.CellDataFeatures<Cause2, String> cellData)
                -> Bindings.concat(cellData.getValue().getRole().getRole()));
        this.causeDisposition.setCellValueFactory(new PropertyValueFactory<>("disposition"));
    }

    private void PopulateHazardTable() {
        hazardList = FXCollections.observableArrayList();
        DataBaseConnection.selectAll("hazard2", hazardList);
        hazardTable.setItems(hazardList);
    }

    private void PopulateRoleTable(Hazard2 hazard) {
        roleList = FXCollections.observableArrayList();
        String sql = MessageFormat.format("select * from role where id in "
                + "(SELECT rootroleid FROM hazardexpansion where hazardid = {0} "
                + "UNION SELECT linkedroleid FROM hazardexpansion where hazardid = {0})", hazard.getId());
        DataBaseConnection.sql(sql, "role", roleList);
        roleTable.setItems(roleList);
        UpdateTableHeaders(hazard);
    }

    private void PopulateCauseTable(Role role) {
        causeList = FXCollections.observableArrayList();
        //String sql = MessageFormat.format("select * from hazard2 where hazardroleid = {0} and id = {1}", role.getId(), currentHazard.getId());
        String sql = MessageFormat.format("select * from cause2 where roleid = {0} and hazardid = {1}", role.getId(), currentHazard.getId());
        System.out.println("hazard.Controllers.Views.CE_ICHA1Controller.PopulateCauseTable(): " + sql);
        DataBaseConnection.sql(sql, "cause2", causeList);
        causeTable.setItems(causeList);
    }

    private void UpdateTableHeaders(Hazard2 hazard) {
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

    private void LoadController(Initializable controller, String url, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            loader.setController(controller);
            AnchorPane pane = loader.load();

            Stage stage = new Stage();
            //stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(pane));
            stage.show();
        } catch (IOException | NullPointerException ex) {
            System.err.println(ex);
        }
    }

    private void AddCause(Role role) {

        Optional<String> disposition = UIHelper.CreateAddDialog("Disposition");

        if (disposition.isPresent() && disposition.get().trim().length() > 0) {
            //Insert into cause2
            String sql = MessageFormat.format("Insert into cause2 (hazardid, roleid, role, disposition, isComplete) "
                    + "values ({0}, {1}, ''{2}'', ''{3}'', 0)", currentHazard.getId(), role.getId(), role.getRole(), disposition.get());
            DataBaseConnection.insert(sql);
        }
    }

    private void SetCauseTableHeaders() {
        if (currentHazard.getCategoryId() == 1) {
            causeRole.setText("Hazard Element");
            causeDisposition.setText("Harm TruthMaker");
        } else {
            causeRole.setText("Initiating Role");
            causeDisposition.setText("Initiating Factor");
        }
    }

}
