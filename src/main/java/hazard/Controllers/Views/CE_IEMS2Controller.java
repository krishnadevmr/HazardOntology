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
import java.net.URL;
import java.text.MessageFormat;
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
public class CE_IEMS2Controller extends InitializableWithLoad {

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

    /*Hazard Table Radios*/
    @FXML
    private RadioButton iERadio;

    @FXML
    private ToggleGroup categoryRadio;

    @FXML
    private RadioButton mishapRadio;

    /*Possible Cause Table*/
    @FXML
    private TableView<Cause2> possibleCauseTable;

    @FXML
    private TableColumn<Cause2, String> possibleCauseRole;

    @FXML
    private TableColumn<Cause2, String> possibleCauseDisposition;

    @FXML
    private TableColumn<Cause2, String> possibleCauseKind;

    private ObservableList<Cause2> possibleCauseList;

    /*Tracking Members*/
    private Hazard2 currentHazard;

    @FXML
    void onAddCause(ActionEvent event) {
        int index = possibleCauseTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            Cause2 possibleCause = possibleCauseTable.getItems().get(index);

            String sql = MessageFormat.format("Update cause2 set isComplete = 1 "
                    + "where id = {0}", possibleCause.getId());
            DataBaseConnection.sqlUpdate(sql);
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
    void onIECategory(ActionEvent event) {
        Helper.FilterHazardByCategory(hazardList, subHazardList, 3);
        hazardTable.setItems(subHazardList);
        possibleCauseTable.setItems(null);
        SetCauseTableHeaders(3);
    }

    @FXML
    void onMishapCategory(ActionEvent event) {
        Helper.FilterHazardByCategory(hazardList, subHazardList, 4);
        hazardTable.setItems(subHazardList);
        possibleCauseTable.setItems(null);
        SetCauseTableHeaders(4);
    }

    @FXML
    void onShowExpansion(ActionEvent event) {
        int index = hazardTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            SingleHazardExpansionController controller = new SingleHazardExpansionController(hazardTable.getItems().get(index));
            LoadController(controller, "/fxml/subviews/SingleHazardExpansion.fxml", "Hazard Expansion");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SetCellfactories();
        InitializeDataLists();
        Helper.PopulateHazardTable(hazardList, hazardTable);
        onIECategory(null);
        iERadio.setSelected(true);
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
    }

    private void InitializeDataLists() {
        hazardList = FXCollections.observableArrayList();
        subHazardList = FXCollections.observableArrayList();
    }

    private void PopulateHazardTable() {
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
        if (category == 4) {
            possibleCauseRole.setText("Hazard Element");
            possibleCauseDisposition.setText("Harm TruthMaker");
        } else {
            possibleCauseRole.setText("Initiating Role");
            possibleCauseDisposition.setText("Initiating Factor");
        }
    }

}
