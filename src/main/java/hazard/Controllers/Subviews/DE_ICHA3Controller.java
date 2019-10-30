/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Subviews;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.HazardExpansion;
import hazard.HazardClasses.Kind;
import hazard.HazardClasses.Relator;
import hazard.HazardClasses.Role;
import hazard.Services.DatabaseManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author kmoothandas
 */
public class DE_ICHA3Controller implements Initializable {

    //private Role primaryRole;
    private HazardExpansion hazardExpansion;

    //private DE_ICHA1Controller DEController;
    
    private DE_Step1 DEController;
    
    private final String relatorLabelTemplate = "Relators for Role: ";

    private final String roleLabelTemplate = "Roles for relator: ";

    public DE_ICHA3Controller(HazardExpansion hazardExpansion, DE_Step1 DEController) {
        this.hazardExpansion = hazardExpansion;
        this.DEController = DEController;
    }

    /*Relator Table*/
    @FXML
    private TableView<Relator> relatorTable;

    @FXML
    private TableColumn<Relator, String> relators;

    @FXML
    private Label relatorLabel;

    private ObservableList<Relator> relatorList;

    /*Role Table*/
    @FXML
    private TableView<Role> allRoleTable;

    @FXML
    private TableColumn<Role, String> allRoles;

    private ObservableList<Role> roleList;

    @FXML
    private Label roleLabel;

    /*Expansion Table*/
    @FXML
    private TableView<HazardExpansion> expansionTable;

    @FXML
    private TableColumn<HazardExpansion, String> rootKind;

    @FXML
    private TableColumn<HazardExpansion, String> rootRole;

    @FXML
    private TableColumn<HazardExpansion, String> expansionRelator;

    @FXML
    private TableColumn<HazardExpansion, String> linkedRole;

    @FXML
    private TableColumn<HazardExpansion, String> linkedKind;

    private ObservableList<HazardExpansion> expansionList;

    @FXML
    private Label expansionLabel;

    @FXML
    private Button addExpansion;

    private ObservableList<Kind> allLinkedKindsList;

    @FXML
    void onAddExpansion(ActionEvent event) {
        int index = allRoleTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            Role role = allRoleTable.getItems().get(index);
            hazardExpansion.setLinkedRole(role);
            InsertIntoExpansionTable();
            PopulateExpansionTable();
            DEController.HighlightStep(4);
        }
    }

    @FXML
    void onRelatorSelected(MouseEvent event) {
        int index = relatorTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            Relator relator = relatorTable.getItems().get(index);
            PopulateRoleTable(relator);
            hazardExpansion.setRelator(relator);
            DEController.HighlightStep(3);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SetCellfactories();
        PopulateRelatorTable();
        relatorLabel.setText(relatorLabelTemplate + hazardExpansion.getRootRole().getRole());
        roleLabel.setText("No relator selected");
        PopulateExpansionTable();

    }

    private void SetCellfactories() {
        this.relators.setCellValueFactory(new PropertyValueFactory<>("relator"));
        this.allRoles.setCellValueFactory(new PropertyValueFactory<>("role"));

        /*Expansion Table*/
        this.rootKind.setCellValueFactory((TableColumn.CellDataFeatures<HazardExpansion, String> cellData)
                -> Bindings.concat(cellData.getValue().getRootKind().getKind()));

        this.rootRole.setCellValueFactory((TableColumn.CellDataFeatures<HazardExpansion, String> cellData)
                -> Bindings.concat(cellData.getValue().getRootRole().getRole()));

        this.expansionRelator.setCellValueFactory((TableColumn.CellDataFeatures<HazardExpansion, String> cellData)
                -> Bindings.concat(cellData.getValue().getRelator().getRelator()));

        this.linkedRole.setCellValueFactory((TableColumn.CellDataFeatures<HazardExpansion, String> cellData)
                -> Bindings.concat(cellData.getValue().getLinkedRole().getRole()));

        this.linkedKind.setCellValueFactory((TableColumn.CellDataFeatures<HazardExpansion, String> cellData)
                -> Bindings.concat(cellData.getValue().getLinkedKind().getKind()));

    }

    private void PopulateRelatorTable() {
        relatorList = FXCollections.observableArrayList();
        DatabaseManager.GetRelatorsOrRole(hazardExpansion.getRootRole().getId(), "roleid", "relatortorole", relatorList);
        relatorTable.setItems(relatorList);
    }

    private void PopulateRoleTable(Relator relator) {
        roleList = FXCollections.observableArrayList();
        DatabaseManager.GetRelatorsOrRole(relator.getId(), "relatorid", "rolefromrelatortorole", roleList);
        roleList.forEach(r -> {
            if(r.getRole().equals(hazardExpansion.getRootRole().getRole())){
            roleList.remove(r);
            }
        });
        
        allRoleTable.setItems(roleList);
        roleLabel.setText(roleLabelTemplate + relator.getRelator());
    }

    private void InsertIntoExpansionTable() {
        allLinkedKindsList = FXCollections.observableArrayList();
        String sql = "select kind, kindid from roletoplay where roleid = " + hazardExpansion.getLinkedRole().getId();
        allLinkedKindsList = DatabaseManager.getAllKindForRole(sql);

        for (Kind kind : allLinkedKindsList) {
            hazardExpansion.setLinkedKind(kind);
            DatabaseManager.InsertIntoHazardExpansion(hazardExpansion);
        }
    }

    private void PopulateExpansionTable() {
        expansionList = FXCollections.observableArrayList();
        DataBaseConnection.selectHazardExpansionByRoleOrID(hazardExpansion.getRootRole().getId(), hazardExpansion.getHazardId(), expansionList);
        expansionTable.setItems(expansionList);

    }

}
