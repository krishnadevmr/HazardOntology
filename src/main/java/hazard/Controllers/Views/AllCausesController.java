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
import java.net.URL;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
public class AllCausesController extends InitializableWithLoad {

    /*Hazard Tablew*/
    @FXML
    private TableView<Hazard2> hazardTable;

    @FXML
    private TableColumn<Hazard2, String> hazardId;

    @FXML
    private TableColumn<Hazard2, String> hazardDescription;

    private ObservableList<Hazard2> hazardList;
    private ObservableList<Hazard2> subHazardList;

    /*Cause Table*/
    @FXML
    private TableView<Cause2> causeTable;

    @FXML
    private Label causeLabel;

    @FXML
    private TableColumn<Cause2, Integer> causeId;

    @FXML
    private TableColumn<Cause2, String> causeRole;

    @FXML
    private TableColumn<Cause2, String> causeDisposition;

    @FXML
    private TableColumn<Cause2, String> causeKind;

    @FXML
    private TableColumn<Cause2, String> causeRelator;

    @FXML
    private TableColumn<Cause2, String> causeEvent;

    private ObservableList<Cause2> causeList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SetCellfactories();
        InitializeDataLists();
        PopulateHazardTable();
    }

    @FXML
    void onHazardClicked(MouseEvent event) {
        int index = hazardTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            Hazard2 hazard = hazardTable.getItems().get(index);
            PopulateCauseTable(hazard);
            causeLabel.setText("All Causes for HD" + hazard.getId());

            if (event.getClickCount() == 2) {
                SingleHazardExpansionController controller = new SingleHazardExpansionController(hazardTable.getItems().get(index));
                LoadController(controller, "/fxml/subviews/SingleHazardExpansion.fxml", "Hazard Expansion");
            }
        }
    }

    private void SetCellfactories() {
        this.hazardId.setCellValueFactory((TableColumn.CellDataFeatures<Hazard2, String> cellData) -> Bindings.concat("HD", cellData.getValue().getId()));
        this.hazardDescription.setCellValueFactory(new PropertyValueFactory<>("hazardDescription"));

        this.causeId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.causeRole.setCellValueFactory((TableColumn.CellDataFeatures<Cause2, String> cellData)
                -> Bindings.concat(cellData.getValue().getRole().getRole() == null ? "" : cellData.getValue().getRole().getRole()));
        this.causeDisposition.setCellValueFactory(new PropertyValueFactory<>("disposition"));
        this.causeKind.setCellValueFactory((TableColumn.CellDataFeatures<Cause2, String> cellData)
                -> Bindings.concat(cellData.getValue().getEnvironmentObject().getKind() == null ? "" : cellData.getValue().getEnvironmentObject().getKind()));
        this.causeEvent.setCellValueFactory(new PropertyValueFactory<>("preInitiationEvent"));
        this.causeRelator.setCellValueFactory((TableColumn.CellDataFeatures<Cause2, String> cellData)
                -> Bindings.concat(cellData.getValue().getRelator().getRelator() == null ? "" : cellData.getValue().getRelator().getRelator()));

    }

    private void InitializeDataLists() {
        hazardList = FXCollections.observableArrayList();
        subHazardList = FXCollections.observableArrayList();
    }

    private void PopulateHazardTable() {
        String sql = "select * from hazard2 where isExpanded = 1";
        DataBaseConnection.sql(sql, "hazard2", hazardList);
        hazardTable.setItems(hazardList);
    }

    private void PopulateCauseTable(Hazard2 hazard) {
        causeList = FXCollections.observableArrayList();
        String sql = MessageFormat.format("select * from cause2 where hazardid = {0} and isComplete = 1", hazard.getId());
        DataBaseConnection.sql(sql, "cause2", causeList);
        causeTable.setItems(causeList);
        UpdateTableHeadersAndColumns(hazard.getCategoryId());
    }

    private void UpdateTableHeadersAndColumns(Integer category) {
        switch (category) {
            case 1:
                causeRole.setText("Hazard Element");
                causeDisposition.setText("Harm TruthMaker");
                causeRelator.setVisible(true);
                causeEvent.setVisible(true);
                break;
            case 2:
                causeRole.setText("Initiating Role");
                causeDisposition.setText("Initiating Factor");
                causeRelator.setVisible(true);
                causeEvent.setVisible(true);
                break;
            case 3:
                causeRole.setText("Initiating Role");
                causeDisposition.setText("Initiating Factor");
                causeRelator.setVisible(false);
                causeEvent.setVisible(false);
                break;
            case 4:
                causeRole.setText("Hazard Element");
                causeDisposition.setText("Harm TruthMaker");
                causeRelator.setVisible(false);
                causeEvent.setVisible(false);
                break;
        }
    }
}
