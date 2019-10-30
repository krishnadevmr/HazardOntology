/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Views;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.MishapVictim2;
import hazard.HazardClasses.Role;
import hazard.Helpers.UIHelper;
import java.net.URL;
import java.util.Optional;
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
public class MVIController implements Initializable {

    @FXML
    private TableView<Role> roleTable;

    @FXML
    private TableColumn<Role, Integer> roleId;

    @FXML
    private TableColumn<Role, String> role;

    private ObservableList<Role> roleList;

    private ObservableList<MishapVictim2> victimList;

    @FXML
    private TableView<MishapVictim2> victimTable;

    @FXML
    private TableColumn<MishapVictim2, String> victimRole;

    @FXML
    private TableColumn<MishapVictim2, String> possibleHarm;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SetCellFactories();
        PopulateRoleTable();
        PopulatePossibleVictims();
    }

    @FXML
    void onVictimAdded(ActionEvent event) {
        int index = roleTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            AddVictim(index);
        }
    }

    @FXML
    void onVictimRemoved(ActionEvent event) {
        int index = victimTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            RemoveVictim(index);
        }
    }

    @FXML
    void onUpdate(ActionEvent event) {
        int index = victimTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            UpdateMishapVictim(index);
        }
    }

    @FXML
    void onVictimsClicked(MouseEvent event) {
        if (event.getClickCount() == 2){
            onUpdate(null);
        }
    }

    private void PopulateRoleTable() {
        roleList = FXCollections.observableArrayList();
        DataBaseConnection.selectAll("role", roleList);

        roleTable.setItems(roleList);
    }

    private void SetCellFactories() {
        roleId.setCellValueFactory(new PropertyValueFactory<>("id"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));

        victimRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        possibleHarm.setCellValueFactory(new PropertyValueFactory<>("harm"));
    }

    private void AddVictim(int roleIndex) {

        Role r = roleTable.getItems().get(roleIndex);

        Optional<String> harm = UIHelper.CreateAddVictimDialog("Add Victim", "harm");
        String possibleHarmText = "";

        if (harm.isPresent() && !harm.get().isEmpty() && harm.get().trim().length() > 0) {
            possibleHarmText = harm.get();

        }
        DataBaseConnection.insertMishapVictim2(r.getId(), r.getRole(), possibleHarmText);
        updatePossibleVictimList();
    }

    private void updatePossibleVictimList() {
        DataBaseConnection.sql(
                "select * from mishapvictim2 ",
                "mishapvictim2", victimList);
        victimTable.setItems(victimList);
    }

    private void PopulatePossibleVictims() {
        victimList = FXCollections.observableArrayList();
        DataBaseConnection.selectAll("mishapvictim2", victimList);

        victimTable.setItems(victimList);
    }

    private void RemoveVictim(int index) {
        MishapVictim2 mishapVictim = victimTable.getItems().remove(index);
        DataBaseConnection.delete("mishapvictim2", mishapVictim.getId());
        victimList.remove(mishapVictim);
    }

    private void UpdateMishapVictim(int index) {
        MishapVictim2 mishapVictim = victimTable.getItems().get(index);

        String possibleHarmText = mishapVictim.getHarm();

        Optional<String> harm = UIHelper.UpdateVictimDialog("Update Victim", "Harm", possibleHarmText);

        if (harm.isPresent()) {
            possibleHarmText = harm.get();
        }

        String sql = "update mishapvictim2 set possibleharm = '" + possibleHarmText + "' where id = " + mishapVictim.getId();
        DataBaseConnection.sqlUpdate(sql);
        PopulatePossibleVictims();
    }

}
