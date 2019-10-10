/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Views;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Hazard2;
import hazard.Helpers.UIHelper;
import hazard.Services.DatabaseManager;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author kmoothandas
 */
public class CHDController implements Initializable {

    @FXML
    private TableView<Hazard2> hazardTable;

    @FXML
    private TableColumn<Hazard2, Integer> hazardId;

    @FXML
    private TableColumn<Hazard2, String> hazardDescription;

    @FXML
    private TableColumn<Hazard2, String> hazardCategory;

    private ObservableList<Hazard2> hazardList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SetCellFactories();
        PopulateTable();
    }
    
    @FXML
    void onHazardClicked(MouseEvent event) {
        if (event.getClickCount() == 2){
            AddCategory();
        }
    }

    private void PopulateTable() {
        hazardList = FXCollections.observableArrayList();
        DataBaseConnection.selectAll("hazard2", hazardList);

        hazardTable.setItems(hazardList);
    }

    private void SetCellFactories() {
        hazardId.setCellValueFactory(new PropertyValueFactory<>("id"));
        hazardDescription.setCellValueFactory(new PropertyValueFactory<>("hazardDescription"));
        hazardCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
    }
    
    private void AddCategory() {
        int tableIndex = hazardTable.getSelectionModel().selectedIndexProperty().get();
        if (tableIndex != -1){
            Hazard2 hazard = hazardTable.getItems().get(tableIndex);
            Optional<Integer> category = UIHelper.AddHazardCategoryDialog("Add Category", hazard.getHazardDescription());
            
            if(category.isPresent()){
                DatabaseManager.UpdateHazardCategory(category.get(), hazard.getId());
                PopulateTable();
            }
        }
    }

}
