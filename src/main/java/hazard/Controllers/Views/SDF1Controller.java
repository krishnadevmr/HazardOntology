/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Views;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Kind;
import hazard.Services.DatabaseManager;
import java.net.URL;
import java.util.ResourceBundle;
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
public class SDF1Controller implements Initializable {

    @FXML
    private TableView<Kind> kindTable;

    @FXML
    private TableColumn<Kind, Integer> kindID;

    @FXML
    private TableColumn<Kind, String> kindDescription;

    private ObservableList<Kind> kindList;
    
    

    @FXML
    void OnAddKind(ActionEvent event) {
        DatabaseManager.AddKindorRole(kindTable, kindList, "Kind");
    }

    @FXML
    void OnRemoveKind(ActionEvent event) {
        DatabaseManager.RemoveKindOrRole(kindTable, "Kind");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        PopulateTableFromDatabase();
    }

    public void PopulateTableFromDatabase() {
        kindList = FXCollections.observableArrayList();
        DataBaseConnection.selectAll("kind", kindList);
        kindID.setCellValueFactory(new PropertyValueFactory<>("id"));
        kindDescription.setCellValueFactory(new PropertyValueFactory<>("kind"));
        kindTable.setItems(kindList);
    }
}
