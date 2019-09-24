/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Views;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Role;
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
public class SDF2Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Role> roleTable;

    @FXML
    private TableColumn<Role, Integer> roleID;

    @FXML
    private TableColumn<Role, String> roleDescription;

    private ObservableList<Role> roleList;

    @FXML
    void onAddRole(ActionEvent event) {
        DatabaseManager.AddKindorRole(roleTable, roleList, "Role");
    }

    @FXML
    void onRemoveRole(ActionEvent event) {
        DatabaseManager.RemoveKindOrRole(roleTable, "Role");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PopulateTableFromDatabase();
    }

    public void PopulateTableFromDatabase() {
        roleList = FXCollections.observableArrayList();
        DataBaseConnection.selectAll("role", roleList);
        roleID.setCellValueFactory(new PropertyValueFactory<>("id"));
        roleDescription.setCellValueFactory(new PropertyValueFactory<>("role"));
        roleTable.setItems(roleList);
    }

}
