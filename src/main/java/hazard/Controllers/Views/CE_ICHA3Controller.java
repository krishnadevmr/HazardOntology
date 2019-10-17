/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Views;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Hazard2;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author kmoothandas
 */
public class CE_ICHA3Controller implements Initializable {

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

    @FXML
    void onShowExpansion(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SetCellfactories();
        PopulateHazardTable();
    }

    private void SetCellfactories() {
        this.hazardId.setCellValueFactory((TableColumn.CellDataFeatures<Hazard2, String> cellData) -> Bindings.concat("HD", cellData.getValue().getId()));
        this.hazardDescription.setCellValueFactory(new PropertyValueFactory<>("hazardDescription"));
        this.hazardCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
    }

    private void PopulateHazardTable() {
        hazardList = FXCollections.observableArrayList();
        DataBaseConnection.selectAll("hazard2", hazardList);
        hazardTable.setItems(hazardList);
    }
    
}
