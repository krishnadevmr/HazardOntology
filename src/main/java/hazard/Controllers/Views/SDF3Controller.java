/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Views;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Kind;
import hazard.HazardClasses.Play;
import hazard.HazardClasses.Role;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author kmoothandas
 */
public class SDF3Controller implements Initializable {

    @FXML
    private TableView<Kind> kindTable;

    @FXML
    private TableColumn<Kind, Integer> kindId;

    @FXML
    private TableColumn<Kind, String> kindDescription;

    @FXML
    private TableView<Role> roleTable;

    @FXML
    private TableColumn<Role, Integer> roleID;

    @FXML
    private TableColumn<Role, String> role;

    @FXML
    private TableView<Role> linkTable;

    @FXML
    private TableColumn<Role, Integer> linkId;

    @FXML
    private TableColumn<Role, String> linkRole;

    private ObservableList<Kind> kindList;
    private ObservableList<Role> roleList;
    private ObservableList<Role> mappedRoleList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SetCellfactories();
        PopulateTablefromDatabase();
        roleList = FXCollections.observableArrayList();
        mappedRoleList = FXCollections.observableArrayList();
    }

    private void PopulateTablefromDatabase() {
        kindList = FXCollections.observableArrayList();
        DataBaseConnection.selectAll("kind", kindList);
        //kindId.setCellValueFactory(new PropertyValueFactory<>("id"));
        //kindDescription.setCellValueFactory(new PropertyValueFactory<>("kind"));
        kindTable.setItems(kindList);
    }

    @FXML
    void onKindClicked(MouseEvent event) {
        System.out.println("hazard.Controllers.Views.SDF3Controller.onKindClicked()");
        int index = kindTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            SetRoles(index);
        }
    }

    @FXML
    void OnRoleRemoved(ActionEvent event) {
        RemoveRoleFromKind();
    }

    @FXML
    void onRoleAdded(ActionEvent event) {
        AddRoleToKind();
    }

    private void SetRoles(int index) {
        int id = kindTable.getItems().get(index).getId();
        DataBaseConnection.sql(
                "SELECT * FROM role WHERE NOT EXISTS(SELECT * FROM roletoplay WHERE role.id=roletoplay.roleid AND "
                + id + "=roletoplay.kindid);",
                "role", roleList);
        DataBaseConnection.sql("SELECT * FROM roletoplay WHERE kindid=" + id + ";", "roletoplay",
                mappedRoleList);
        roleTable.setItems(roleList);
        linkTable.setItems(mappedRoleList);
    }

    private void SetCellfactories() {
        kindId.setCellValueFactory(new PropertyValueFactory<>("id"));
        kindDescription.setCellValueFactory(new PropertyValueFactory<>("kind"));

        roleID.setCellValueFactory(new PropertyValueFactory<>("id"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));

        linkId.setCellValueFactory(new PropertyValueFactory<>("id"));
        linkRole.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

    private void AddRoleToKind() {
        int roleIndex = roleTable.getSelectionModel().selectedIndexProperty().get();
        int kindIndex = kindTable.getSelectionModel().selectedIndexProperty().get();
        if (roleIndex >= 0 && kindIndex >= 0) {
            Role r = roleTable.getItems().remove(roleIndex);
            Kind k = kindTable.getItems().get(kindIndex);
            DataBaseConnection.insert("INSERT INTO roletoplay (role,roleid,kind,kindid) VALUES('" + r.getRole()
                    + "'," + r.getId() + ",'" + k.getKind() + "'," + k.getId() + ")");
            mappedRoleList.add(r);
        }
    }

    private void RemoveRoleFromKind() {
        int tbRoleToPlayIndex = linkTable.getSelectionModel().selectedIndexProperty().get();
        int kindIndex = kindTable.getSelectionModel().selectedIndexProperty().get();
        if (tbRoleToPlayIndex >= 0 && kindIndex >= 0) {
            Role r = linkTable.getItems().remove(tbRoleToPlayIndex);
            Kind k = kindTable.getItems().get(kindIndex);
            DataBaseConnection.deleteARoleToPlay("roletoplay", String.valueOf(r.getId()),
                    String.valueOf(k.getId()));
            roleList.add(r);
        }
    }

}
