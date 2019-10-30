/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Views;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Play;
import hazard.HazardClasses.Relator;
import hazard.HazardClasses.Role;
import hazard.Services.DatabaseManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author kmoothandas
 */
public class SDF4Controller implements Initializable {

    @FXML
    private TableView<Role> roleTable;

    @FXML
    private TableColumn<Role, Integer> roleId;

    @FXML
    private TableColumn<Role, String> roleDescription;

    @FXML
    private TableView<Relator> relatorTable;

    @FXML
    private TableColumn<Relator, Integer> relatorId;

    @FXML
    private TableColumn<Relator, String> relatorDescription;

    @FXML
    private TableView<Relator> mappedRelatorTable;

    @FXML
    private TableColumn<Relator, Integer> linkRelatorId;

    @FXML
    private TableColumn<Relator, String> linkRelator;

    @FXML
    void OnRelatorAdded(ActionEvent event) {
        DatabaseManager.AddRelator(relatorTable, relatorList, "relator");
        relatorTable.setItems(relatorList);
    }

    @FXML
    void OnRelatorDelinked(ActionEvent event) {
        RemoveRelatorLinkToRole();
    }

    @FXML
    void OnRelatorLinked(ActionEvent event) {
        AddRelatorLinkToRole();
    }

    @FXML
    void OnRelatorRemoved(ActionEvent event) {
        RemoveRelatorfromRole();
    }

    @FXML
    void OnRoleSelected(MouseEvent event) {
        int index = roleTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            SetRelators(index);
        }
    }

    private ObservableList<Role> roleList;
    private ObservableList<Relator> relatorList;
    private ObservableList<Relator> mappedRelatorList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SetCellfactories();
        PopulateTablefromDatabase();
        roleList = FXCollections.observableArrayList();
        relatorList = FXCollections.observableArrayList();
        mappedRelatorList = FXCollections.observableArrayList();
    }

    private void SetCellfactories() {
        roleId.setCellValueFactory(new PropertyValueFactory<>("id"));
        roleDescription.setCellValueFactory(new PropertyValueFactory<>("role"));

        relatorId.setCellValueFactory(new PropertyValueFactory<>("id"));
        relatorDescription.setCellValueFactory(new PropertyValueFactory<>("relator"));

        linkRelatorId.setCellValueFactory(new PropertyValueFactory<>("id"));
        linkRelator.setCellValueFactory(new PropertyValueFactory<>("relator"));
    }

    private void PopulateTablefromDatabase() {
        roleList = FXCollections.observableArrayList();
        DataBaseConnection.selectAll("role", roleList);
        roleTable.setItems(roleList);
    }

    private void SetRelators(int index) {
        int id = roleTable.getItems().get(index).getId();
        DataBaseConnection.sql(
                "SELECT * FROM relator WHERE NOT EXISTS(SELECT * FROM relatortorole WHERE relator.id=relatortorole.relatorid AND "
                + id + "=relatortorole.roleid);",
                "relator", relatorList);
        DataBaseConnection.sql("SELECT * FROM relatortorole WHERE roleid=" + id + ";", "relatortorole",
                mappedRelatorList);

        relatorTable.setItems(relatorList);
        mappedRelatorTable.setItems(mappedRelatorList);
    }

    private void RemoveRelatorfromRole() {
        if (!relatorTable.getItems().isEmpty()) {
            int index = relatorTable.getSelectionModel().selectedIndexProperty().get();
            if (index != -1) {
                Play o = relatorTable.getItems().remove(index);
                DataBaseConnection.delete("relator", o.getId());
            }
        }
    }

    private void AddRelatorLinkToRole() {
        int relatorIndex = relatorTable.getSelectionModel().selectedIndexProperty().get();
        int roleIndex = roleTable.getSelectionModel().selectedIndexProperty().get();
        if (roleIndex >= 0 && relatorIndex >= 0) {
            Relator rel = relatorTable.getItems().remove(relatorIndex);
            Role rol = roleTable.getItems().get(roleIndex);
            DataBaseConnection.insert("INSERT INTO relatortorole (relator,relatorid,role,roleid) VALUES('"
                    + rel.getRelator() + "'," + rel.getId() + ",'" + rol.getRole() + "'," + rol.getId() + ");");
            mappedRelatorList.add(rel);
        }
    }

    private void RemoveRelatorLinkToRole() {
        int tbRelatorToRoleIndex = mappedRelatorTable.getSelectionModel().selectedIndexProperty().get();
        int roleIndex = roleTable.getSelectionModel().selectedIndexProperty().get();
        if (tbRelatorToRoleIndex >= 0 && roleIndex >= 0) {
            Relator rel = mappedRelatorTable.getItems().remove(tbRelatorToRoleIndex);
            Role role = roleTable.getItems().get(roleIndex);
            DataBaseConnection.deleteRelatorToRole("relatortorole", String.valueOf(role.getId()),
                    String.valueOf(rel.getId()));
            relatorList.add(rel);
        }
    }

}
