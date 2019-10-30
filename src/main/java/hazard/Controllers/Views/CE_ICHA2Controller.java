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
import hazard.Helpers.Helper;
import hazard.Helpers.UIHelper;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author kmoothandas
 */
public class CE_ICHA2Controller extends InitializableWithLoad {

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
    private ObservableList<Hazard2> subHazardList;

    /*Radio Buttons*/
    @FXML
    private RadioButton hazardRadio;

    @FXML
    private ToggleGroup categoryRadio;

    @FXML
    private RadioButton iCRadio;

    /*Possible Cause Table*/
    @FXML
    private TableView<Cause2> possibleCauseTable;

    @FXML
    private TableColumn<Cause2, String> possibleCauseRole;

    @FXML
    private TableColumn<Cause2, String> possibleCauseDisposition;

    @FXML
    private TableColumn<Cause2, String> possibleCauseKind;

    @FXML
    private TableColumn<Cause2, String> causeEvent;

    @FXML
    private TableColumn<Cause2, String> causeComplete;

    private ObservableList<Cause2> possibleCauseList;

    /*Tracking Members*/
    private Hazard2 currentHazard;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SetCellfactories();
        InitializeDataLists();
        Helper.PopulateHazardTable(hazardList, hazardTable);
        onHazardCategory(null);
        hazardRadio.setSelected(true);
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
            currentHazard = hazard;
            PopulatePossibleCauseTable();
        }
    }

    @FXML
    void onAddEvent(ActionEvent event) {
        int index = possibleCauseTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            Cause2 possibleCause = possibleCauseTable.getItems().get(index);
            Optional<String> result = UIHelper.CreateAddDialog("Pre Initiating Event");
            if (result.isPresent()) {
                String sql = MessageFormat.format("Update cause2 set preinitevent = ''{0}'' , isComplete = 1 "
                        + "where id = {1}", result.get(), possibleCause.getId());
                DataBaseConnection.sqlUpdate(sql);
            }
        }
    }

    @FXML
    void onHazardCategory(ActionEvent event) {
        Helper.FilterHazardByCategory(hazardList, subHazardList, 1);
        hazardTable.setItems(subHazardList);
        possibleCauseTable.setItems(null);
        SetCauseTableHeaders(1);
    }

    @FXML
    void onICCategory(ActionEvent event) {
        Helper.FilterHazardByCategory(hazardList, subHazardList, 2);
        hazardTable.setItems(subHazardList);
        possibleCauseTable.setItems(null);
        SetCauseTableHeaders(2);
    }

    private void SetCellfactories() {
        this.hazardId.setCellValueFactory((TableColumn.CellDataFeatures<Hazard2, String> cellData) -> Bindings.concat("HD", cellData.getValue().getId()));
        this.hazardDescription.setCellValueFactory(new PropertyValueFactory<>("hazardDescription"));
        this.hazardCategory.setCellValueFactory(new PropertyValueFactory<>("category"));

        this.possibleCauseRole.setCellValueFactory((TableColumn.CellDataFeatures<Cause2, String> cellData)
                -> Bindings.concat(cellData.getValue().getRole().getRole()));
        this.possibleCauseDisposition.setCellValueFactory(new PropertyValueFactory<>("disposition"));
        this.possibleCauseKind.setCellValueFactory((TableColumn.CellDataFeatures<Cause2, String> cellData)
                -> Bindings.concat(cellData.getValue().getEnvironmentObject().getKind()));
        this.causeEvent.setCellValueFactory(new PropertyValueFactory<>("preInitiationEvent"));
        this.causeComplete.setCellValueFactory((TableColumn.CellDataFeatures<Cause2, String> cellData) -> Bindings.concat(cellData.getValue().getIsComplete() == 1 ? "Yes" : "No"));
    }

    private void InitializeDataLists() {
        hazardList = FXCollections.observableArrayList();
        subHazardList = FXCollections.observableArrayList();
    }

    private void PopulateHazardTable() {
        hazardList = FXCollections.observableArrayList();
        DataBaseConnection.selectAll("hazard2", hazardList);
        hazardTable.setItems(hazardList);
    }

    private void PopulatePossibleCauseTable() {
        possibleCauseList = FXCollections.observableArrayList();
        String sql = MessageFormat.format("select * from cause2 where hazardid = {0} and roleid is not null", currentHazard.getId());
        DataBaseConnection.sql(sql, "cause2", possibleCauseList);
        possibleCauseTable.setItems(possibleCauseList);
    }

    private void SetCauseTableHeaders(Integer category) {
        if (category == 1) {
            possibleCauseRole.setText("Hazard Element");
            possibleCauseDisposition.setText("Harm TruthMaker");
        } else {
            possibleCauseRole.setText("Initiating Role");
            possibleCauseDisposition.setText("Initiating Factor");
        }
    }

}
