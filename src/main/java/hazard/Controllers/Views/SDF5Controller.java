/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Views;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Kind;
import hazard.HazardClasses.Role;
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
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author kmoothandas
 */
public class SDF5Controller implements Initializable {

    @FXML
    private TableView<Role> roleTable;
    @FXML
    private TableColumn<Role, Integer> roleId;
    @FXML
    private TableColumn<Role, String> role;
    @FXML
    private TableView<Kind> kindTable;
    @FXML
    private TableColumn<Kind, Integer> kindId;
    @FXML
    private TableColumn<Kind, String> kind;
    @FXML
    private TableView<Kind> mappedKindTable;
    @FXML
    private TableColumn<Kind, Integer> mappedKindId;
    @FXML
    private TableColumn<Kind, String> mappedKind;

    private ObservableList<Role> roleList;
    private ObservableList<Kind> kindList;
    private ObservableList<Kind> mappedkindList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SetCellfactories();
        roleList = FXCollections.observableArrayList();
        kindList = FXCollections.observableArrayList();
        mappedkindList = FXCollections.observableArrayList();
        PopulateTablefromDatabase();

    }

    @FXML
    private void OnKindAdded(ActionEvent event) {
        AddKindToRole();
    }

    @FXML
    private void OnKindRemoved(ActionEvent event) {
        RemoveKindFromRole();
    }

    @FXML
    private void OnRoleSelected(MouseEvent event) {
        int index = roleTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            SetKinds(index);
        }
    }

    private void SetCellfactories() {
        roleId.setCellValueFactory(new PropertyValueFactory<>("id"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));

        kindId.setCellValueFactory(new PropertyValueFactory<>("id"));
        kind.setCellValueFactory(new PropertyValueFactory<>("kind"));

        mappedKindId.setCellValueFactory(new PropertyValueFactory<>("id"));
        mappedKind.setCellValueFactory(new PropertyValueFactory<>("kind"));
    }

    private void PopulateTablefromDatabase() {
        DataBaseConnection.selectAll("role", roleList);
        roleTable.setItems(roleList);
    }

    private void SetKinds(int index) {
        if (index > -1) {
            int id = roleTable.getItems().get(index).getId();
            DataBaseConnection.sql(
                    "SELECT * FROM kind WHERE NOT EXISTS(SELECT * FROM roletoplay WHERE kind.id=roletoplay.kindid AND "
                    + id + "=roletoplay.roleid);",
                    "kind", kindList);
            DataBaseConnection.sql("SELECT * FROM roletoplay WHERE roleid=" + id + ";", "kindtorole",
                    mappedkindList);

            kindTable.setItems(kindList);
            mappedKindTable.setItems(mappedkindList);
        }
    }

    private void AddKindToRole() {
        int roleIndex = roleTable.getSelectionModel().selectedIndexProperty().get();
        int kindIndex = kindTable.getSelectionModel().selectedIndexProperty().get();
        if (roleIndex >= 0 && kindIndex >= 0) {
            Role r = roleTable.getItems().get(roleIndex);
            Kind k = kindTable.getItems().remove(kindIndex);
            DataBaseConnection.insert("INSERT INTO roletoplay (role,roleid,kind,kindid) VALUES('" + r.getRole()
                    + "'," + r.getId() + ",'" + k.getKind() + "'," + k.getId() + ")");
            mappedkindList.add(k);
        }
    }

    private void RemoveKindFromRole() {
        int tbRoleToPlayIndex = mappedKindTable.getSelectionModel().selectedIndexProperty().get();
        int tbRoleIndex = roleTable.getSelectionModel().selectedIndexProperty().get();
        if (tbRoleToPlayIndex >= 0 && tbRoleIndex >= 0) {
            Kind k = mappedKindTable.getItems().remove(tbRoleToPlayIndex);
            Role r = roleTable.getItems().get(tbRoleIndex);
            DataBaseConnection.deleteARoleToPlay("roletoplay", String.valueOf(r.getId()),
                    String.valueOf(k.getId()));
            kindList.add(k);
        }
    }

}
