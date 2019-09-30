/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Views;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardAnalysis.Graph.RelatorGraph;
import hazard.HazardAnalysis.Graph.SystemGraph;
import hazard.HazardClasses.MishapVictim;
import hazard.HazardClasses.Play;
import hazard.HazardClasses.PossibleVictim;
import java.awt.Frame;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
public class MVI1Controller implements Initializable {

    @FXML
    private TableView<PossibleVictim> possibleVictimsTable;
    @FXML
    private TableColumn<PossibleVictim, String> possibleRole;
    @FXML
    private TableColumn<PossibleVictim, String> possibleKind;
    @FXML
    private TableColumn<PossibleVictim, String> possibleRelator;

    @FXML
    private TableView<MishapVictim> victimsTable;
    @FXML
    private TableColumn<MishapVictim, Integer> victimId;
    @FXML
    private TableColumn<MishapVictim, String> role;
    @FXML
    private TableColumn<MishapVictim, String> kind;
    @FXML
    private TableColumn<MishapVictim, String> relator;

    private ObservableList<PossibleVictim> possibleVictimList;
    private ObservableList<MishapVictim> victimList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SetCellValueFactories();
        victimList = FXCollections.observableArrayList();
        possibleVictimList = FXCollections.observableArrayList();
        PopulatePossibleVictimsTable();
        PopulateVictimList();
    }

    @FXML
    void OnPossibleVictimSelected(MouseEvent event) {
        GenerateGraph(event);
    }

    @FXML
    void OnVictimAdded(ActionEvent event) {
        int index = possibleVictimsTable.getSelectionModel().getSelectedIndex();
        AddVictim(index);
    }

    @FXML
    void OnVictimRemoved(ActionEvent event) {
        int index = victimsTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            RemoveVictim(index);
        }
    }

    @FXML
    void OnGenerateSystemDiagram(ActionEvent event) {
        GenerateSystemDiagram();
    }

    private void SetCellValueFactories() {
        possibleRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        possibleKind.setCellValueFactory(new PropertyValueFactory<>("kind"));
        possibleRelator.setCellValueFactory(new PropertyValueFactory<>("relator"));

        victimId.setCellValueFactory(new PropertyValueFactory<>("id"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
        kind.setCellValueFactory(new PropertyValueFactory<>("kind"));
        relator.setCellValueFactory(new PropertyValueFactory<>("relator"));
    }

    private void PopulatePossibleVictimsTable() {
        updatePossibleVictimList();
        possibleVictimsTable.setItems(possibleVictimList);
    }

    public void updatePossibleVictimList() {
        DataBaseConnection.sql(
                "select roletoplay.role,roletoplay.kind,relatortorole.relator,roletoplay.roleid,roletoplay.kindid,relatortorole.relatorid from roletoplay, relatortorole where relatortorole.roleid = roletoplay.roleid",
                "possiblevictim", possibleVictimList);
    }

    private void AddVictim(int index) {

        PossibleVictim pv = possibleVictimsTable.getItems().get(index);
        DataBaseConnection.insertMishapVictim(pv.getRoleID(), pv.getRole(), pv.getKindID(), pv.getKind(),
                pv.getRelatorID(), pv.getRelator());
        updateVictimList();
        //victimsTable.setItems(victimList);
    }

    private void RemoveVictim(int index) {
        if (!victimsTable.getItems().isEmpty()) {

            Play o = victimsTable.getItems().remove(index);
            DataBaseConnection.delete("mishapvictim", o.getId());
            updateVictimList();
        }
    }

    private void GenerateSystemDiagram() {
        SystemGraph frame;
        try {
            frame = new SystemGraph();
            frame.setResizable(true);
            frame.setSize(500, 250);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private void GenerateGraph(MouseEvent event) {
        if (event.getClickCount() == 2) {
            int index = possibleVictimsTable.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                PossibleVictim pv = possibleVictimsTable.getItems().get(index);
                RelatorGraph frame;
                try {
                    frame = new RelatorGraph(pv.getRelator());
                    frame.setResizable(true);
                    frame.setSize(800, 400);
                    frame.setVisible(true);
                } catch (SQLException e) {
                    System.err.println(e);
                }
            }
        }
    }

    private void PopulateVictimList() {
        updateVictimList();
        //victimsTable.setItems(victimList);
    }

    public void updateVictimList() {
        DataBaseConnection.sql("Select * from mishapvictim", "mishapvictim", victimList);
        victimsTable.setItems(victimList);
    }

}
