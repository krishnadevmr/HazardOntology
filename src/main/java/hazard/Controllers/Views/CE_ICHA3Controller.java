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
import hazard.HazardClasses.HazardExpansion;
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
public class CE_ICHA3Controller extends InitializableWithLoad {

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

    /*Relator Table*/
    @FXML
    private TableView<HazardExpansion> relatorTable;

    @FXML
    private TableColumn<HazardExpansion, String> possibleRelator;

    private ObservableList<HazardExpansion> relatorList;

    /*Cause Table*/
    @FXML
    private TableView<Cause2> causeTable;

    @FXML
    private TableColumn<Cause2, String> causeRelator;

    @FXML
    private TableColumn<Cause2, String> causeEvent;

    private ObservableList<Cause2> causeList;

    /*Radios*/
    @FXML
    private ToggleGroup radioCategory;

    @FXML
    private RadioButton iCRadio;
    @FXML
    private RadioButton hazardRadio;

    /*Trackers*/
    private Hazard2 currentHazard;

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
        onHazardCategory(null);
        hazardRadio.setSelected(true);
    }

    @FXML
    void onRelatorClicked(MouseEvent event) {
        int index = relatorTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            HazardExpansion hazardExpansion = relatorTable.getItems().get(index);
            PopulateCauseTable(hazardExpansion.getRelator().getId());
        }
    }

    @FXML
    void onAddCause(ActionEvent event) {
        int index = relatorTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            HazardExpansion hazardExpansion = relatorTable.getItems().get(index);
            Optional<String> result = UIHelper.CreateAddDialog("Pre Initiating Event");
            if (result.isPresent()) {
                String sql = MessageFormat.format("Insert into cause2 (hazardid , relatorid, relator, preinitevent, isComplete) "
                        + "values ({0}, {1}, ''{2}'', ''{3}'' , 0)", currentHazard.getId(),
                        hazardExpansion.getRelator().getId(), hazardExpansion.getRelator().getRelator(), result.get());
                DataBaseConnection.insert(sql);
                PopulateCauseTable(hazardExpansion.getRelator().getId());
            }
        }
    }

    @FXML
    void onEditCause(ActionEvent event) {
        int index = causeTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            Cause2 cause = causeTable.getItems().get(index);
            Optional<String> result = UIHelper.CreateAddDialog("Pre Initiating Event");
            if (result.isPresent()) {
                String sql = MessageFormat.format("Update cause2 set preinitevent = ''{0}'' "
                        + "where id = {1}", result.get(), cause.getId());
                DataBaseConnection.sqlUpdate(sql);
                PopulateCauseTable(cause.getId());
            }
        }
    }

    @FXML
    void onHazardCategory(ActionEvent event) {
        Helper.FilterHazardByCategory(hazardList, subHazardList, 1);
        hazardTable.setItems(subHazardList);
        relatorTable.setItems(null);
    }

    @FXML
    void onICCategory(ActionEvent event) {
        Helper.FilterHazardByCategory(hazardList, subHazardList, 2);
        hazardTable.setItems(subHazardList);
        relatorTable.setItems(null);
    }

    private void SetCellfactories() {
        this.hazardId.setCellValueFactory((TableColumn.CellDataFeatures<Hazard2, String> cellData) -> Bindings.concat("HD", cellData.getValue().getId()));
        this.hazardDescription.setCellValueFactory(new PropertyValueFactory<>("hazardDescription"));
        this.hazardCategory.setCellValueFactory(new PropertyValueFactory<>("category"));

        this.possibleRelator.setCellValueFactory((TableColumn.CellDataFeatures<HazardExpansion, String> cellData)
                -> Bindings.concat(cellData.getValue().getRelator().getRelator()));

        this.causeRelator.setCellValueFactory((TableColumn.CellDataFeatures<Cause2, String> cellData)
                -> Bindings.concat(cellData.getValue().getRelator().getRelator()));
        this.causeEvent.setCellValueFactory(new PropertyValueFactory<>("preInitiationEvent"));
    }

    @FXML
    void onHazardClicked(MouseEvent event) {
        int index = hazardTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            Hazard2 hazard = hazardTable.getItems().get(index);
            currentHazard = hazard;
            PopulateRelatorTable(hazard);
        }
    }

    private void PopulateHazardTable() {
        hazardList = FXCollections.observableArrayList();
        DataBaseConnection.selectAll("hazard2", hazardList);
        hazardTable.setItems(hazardList);
    }

    private void InitializeDataLists() {
        hazardList = FXCollections.observableArrayList();
        subHazardList = FXCollections.observableArrayList();
    }

    private void PopulateRelatorTable(Hazard2 hazard) {
        relatorList = FXCollections.observableArrayList();
        String sql = MessageFormat.format("select * from hazardexpansion where hazardid = {0} group by relator", hazard.getId());
        DataBaseConnection.sql(sql, "hazardexpansion", relatorList);
        relatorTable.setItems(relatorList);
    }

    private void PopulateCauseTable(Integer relatorId) {
        causeList = FXCollections.observableArrayList();
        String sql = MessageFormat.format("select * from cause2 where hazardid = {0} and relatorid = {1}",
                currentHazard.getId(), relatorId);
        DataBaseConnection.sql(sql, "cause2", causeList);
        causeTable.setItems(causeList);
    }

}
