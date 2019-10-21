/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Subviews;

import hazard.Controllers.InitializableWithLoad;
import hazard.Controllers.Navigation.HazardDescriptionExpansionController;
import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Hazard2;
import hazard.HazardClasses.HazardExpansion;
import hazard.HazardClasses.Kind;
import hazard.HazardClasses.Role;
import hazard.Services.DatabaseManager;
import java.text.MessageFormat;
import java.util.HashMap;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author kmoothandas
 */
public class DE_Step1 extends InitializableWithLoad {

    public HazardDescriptionExpansionController buttonController;

    public int currentStep;

    /*Hazard Table*/
    @FXML
    public TableView<Hazard2> hazardTable;

    @FXML
    public TableColumn<Hazard2, String> hazardId;

    @FXML
    public TableColumn<Hazard2, String> mishapVictim;

    @FXML
    public TableColumn<Hazard2, String> exposure;

    @FXML
    public TableColumn<Hazard2, String> hazardElement;

    @FXML
    public TableColumn<Hazard2, String> truthmaker;

    @FXML
    public TableColumn<Hazard2, String> hazardDescription;

    @FXML
    public TableColumn<Hazard2, String> isExpanded;

    public ObservableList<Hazard2> hazardList;

    /*Victim Kind Table*/
    @FXML
    public Label kindTableLabel;

    @FXML
    public TableView<Kind> victimKindTable;

    @FXML
    public TableColumn<Kind, String> victimKinds;

    public ObservableList<Kind> victimKindList;

    /*Hazard Kind Table*/
    @FXML
    public TableView<Kind> hazardKindTable;

    @FXML
    public TableColumn<Kind, String> hazardKinds;

    public ObservableList<Kind> hazardKindList;

    /* Role Table*/
    @FXML
    public TableView<Role> roleTable;

    @FXML
    public TableColumn<Role, String> roles;

    public ObservableList<Role> roleList;

    /*Current selection property*/
    public Kind currentKind;

    public HashMap<RadioButton, Integer> categoryMap;

    public void HighlightStep(Integer stepNumber) {

        HashMap<Integer, ToggleButton> buttonMap = GetCurrentButtonMap();
        if (currentStep != stepNumber) {
            buttonMap.get(stepNumber).setDisable(false);
            buttonMap.get(stepNumber).fire();
            if (stepNumber != 1) {
                buttonMap.get(stepNumber).setDisable(true);
            }
            currentStep = stepNumber;
        }
    }

    public HashMap<Integer, ToggleButton> GetCurrentButtonMap() {
        return new HashMap<Integer, ToggleButton>();
    }

    @FXML
    void onShowExpansion(ActionEvent event) {
        int index = hazardTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            SingleHazardExpansionController controller = new SingleHazardExpansionController(hazardTable.getItems().get(index));
            LoadController(controller, "/fxml/subviews/SingleHazardExpansion.fxml", "Hazard Expansion");
        }
    }

    @FXML
    void onHazardClicked(MouseEvent event) {
        int index = hazardTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            Hazard2 hazard = hazardTable.getItems().get(index);
            PopulateKindTable(hazard);
            if (event.getClickCount() == 2) {
                ShowHazard(hazard);
            }
            roleTable.setItems(null);
            HighlightStep(1);
        }
    }

    public void ShowHazard(Hazard2 hazard) {
    }

    @FXML
    void onHazardKindSelected(MouseEvent event) {
        int index = hazardKindTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            Kind kind = hazardKindTable.getItems().get(index);
            currentKind = kind;
            PopulateRoleTable(kind);
            HighlightStep(2);
        }
    }

    @FXML
    void onVictimKindSelected(MouseEvent event) {
        int index = victimKindTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            Kind kind = victimKindTable.getItems().get(index);
            currentKind = kind;
            PopulateRoleTable(kind);
            HighlightStep(2);
        }
    }

    @FXML
    void onIdentifyRelators(ActionEvent event) {

        int index = roleTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            HazardExpansion hazardExpansion = new HazardExpansion();
            hazardExpansion.setRootRole(roleTable.getItems().get(index));
            hazardExpansion.setRootKind(currentKind);
            hazardExpansion.setHazardId(hazardTable.getItems().get(hazardTable.getSelectionModel().selectedIndexProperty().get()).getId());

            DE_ICHA3Controller controller = new DE_ICHA3Controller(hazardExpansion, this);
            LoadController(controller, "/fxml/subviews/DE_ICHA3.fxml", "Identify Relators");

            HighlightStep(3);
        }

    }

    public void PopulateHazardTable(int category) {
        hazardList = FXCollections.observableArrayList();
        DatabaseManager.GetHazardByCategory(category, hazardList);
        //DataBaseConnection.selectAll("hazard2", hazardList);
        hazardTable.setItems(hazardList);
        UpdateHazardTableHeaders(category);
    }

    public void SetCellfactories() {
        this.hazardId.setCellValueFactory((TableColumn.CellDataFeatures<Hazard2, String> cellData) -> Bindings.concat("HD", cellData.getValue().getId()));
        this.mishapVictim.setCellValueFactory(cellData -> Bindings.concat(
                cellData.getValue().getMishapVictim(), System.lineSeparator(),
                "(", cellData.getValue().getVictimKind(), ")"));

        this.exposure.setCellValueFactory(new PropertyValueFactory<>("exposure"));
        this.hazardElement.setCellValueFactory(cellData -> Bindings.concat(
                cellData.getValue().getHazardElement(), System.lineSeparator(),
                "(", cellData.getValue().getHazardKind(), ")"));
        this.truthmaker.setCellValueFactory(new PropertyValueFactory<>("truthmaker"));
        this.hazardDescription.setCellValueFactory(new PropertyValueFactory<>("hazardDescription"));

        this.victimKinds.setCellValueFactory(new PropertyValueFactory<>("kind"));
        this.hazardKinds.setCellValueFactory(new PropertyValueFactory<>("kind"));

        this.roles.setCellValueFactory(new PropertyValueFactory<>("role"));

        this.isExpanded.setCellValueFactory((TableColumn.CellDataFeatures<Hazard2, String> cellData) -> Bindings.concat(cellData.getValue().getIsExpanded() == 1 ? "Yes" : "No"));
    }

    public void PopulateKindTable(Hazard2 hazard) {
        victimKindList = FXCollections.observableArrayList();
        ObservableList<ObservableList<Kind>> hazardKindsList = FXCollections.observableArrayList();
        hazardKindsList = DatabaseManager.GetHazardKinds(hazard, hazardKindsList);

        ObservableList<Kind> victimsKinds = hazardKindsList.get(0);
        ObservableList<Kind> hazardsKinds = hazardKindsList.get(1);

        victimKindTable.setItems(victimsKinds);
        hazardKindTable.setItems(hazardsKinds);

        kindTableLabel.setText("All kinds for Hazard HD" + hazard.getId());
    }

    public void PopulateRoleTable(Kind kind) {
        roleList = FXCollections.observableArrayList();
        String sql = "select * from roletoplay where kindid = " + kind.getId();
        DataBaseConnection.sql(sql, "roletoplay", roleList);
        roleTable.setItems(roleList);
    }

    public void UpdateExpansionStatus(Hazard2 hazard, Boolean isExpanded) {
        int expanded;
        if (isExpanded) {
            expanded = 1;
        } else {
            expanded = 0;
        }

        String sql = MessageFormat.format("UPDATE hazard2 SET isExpanded ={0} WHERE id={1}", expanded, hazard.getId());
        DataBaseConnection.sqlUpdate(sql);
    }

    public void UpdateHazardTableHeaders(int category) {
        switch (category) {
            case 1:
            case 4:
                hazardElement.setText("Hazard Element");
                exposure.setText("Exposure");
                truthmaker.setText("Harm Truthmaker");
                break;
            case 2:
            case 3:
                hazardElement.setText("Initiating Role");
                exposure.setText("Relator");
                truthmaker.setText("Initiating factor");
                break;
        }
    }
}
