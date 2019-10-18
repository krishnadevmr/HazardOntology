/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Subviews;

import hazard.Controllers.InitializableWithLoad;
import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Cause2;
import hazard.HazardClasses.Kind;
import hazard.HazardClasses.Role;
import hazard.Helpers.UIHelper;
import hazard.Services.DatabaseManager;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author kmoothandas
 */
public class KindsThatPlayCauseRoleController extends InitializableWithLoad {

    private Cause2 possibleCause;

    private ObservableList<Kind> kindList;

    @FXML
    private TextArea preInitiatingText;

    @FXML
    private TableView<Kind> kindTable;

    @FXML
    private TableColumn<Kind, String> kind;

    /*Trackers*/
    Integer updateKindCount;

    public KindsThatPlayCauseRoleController(Cause2 possibleCause) {
        this.possibleCause = possibleCause;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SetCellFactories();
        PopulateKindTable();
        updateKindCount = 0;
    }

    @FXML
    void onUpdateCause(ActionEvent event) {

        if (preInitiatingText.getText().trim().length() == 0) {
            UIHelper.Warn("Warning", "Please Enter valid text in the pre initiating event box");
            return;
        }

        int index = kindTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            Kind selectedKind = kindTable.getItems().get(index);
            if (updateKindCount == 0) {
                String sql = MessageFormat.format("Update cause2 set environmentobjectid = {0}, environmentobject = ''{1}'',"
                        + "preinitevent = ''{2}'' , isComplete = 1"
                        + "where id = {3}", selectedKind.getId(), selectedKind.getKind(), preInitiatingText.getText(), possibleCause.getId());
                DataBaseConnection.sqlUpdate(sql);
                //updateKindCount = 1;
            } else {
                String sql = MessageFormat.format("Insert into cause2 (hazardid, roleid, role, disposition,"
                        + "environmentobjectid, environmentobject, preinitevent, isComplete) "
                        + "values ({0}, {1}, ''{2}'', ''{3}'', {4}, ''{5}'', ''{6}'', 1)", possibleCause.getHazard().getId(),
                        possibleCause.getRole().getId(), possibleCause.getRole().getRole(), possibleCause.getDisposition(),
                        selectedKind.getId(), selectedKind.getKind(), preInitiatingText.getText());
                DataBaseConnection.insert(sql);

            }
        }

    }

    private void SetCellFactories() {
        this.kind.setCellValueFactory(new PropertyValueFactory<>("kind"));
    }

    private void PopulateKindTable() {
        Role role = possibleCause.getRole();
        String sql = "select * from roletoplay where roleid =" + role.getId();
        kindList = DatabaseManager.getAllKindForRole(sql);
        kindTable.setItems(kindList);
    }

}
