/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Subviews;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardAnalysis.Graph.HazardExpansionGraph;
import hazard.HazardClasses.Hazard2;
import hazard.HazardClasses.HazardExpansion;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author kmoothandas
 */
public class SingleHazardExpansionController implements Initializable {

    private Hazard2 hazard;

    public SingleHazardExpansionController(Hazard2 hazard) {
        this.hazard = hazard;
    }

    public SingleHazardExpansionController() {
    }

    @FXML
    private TableView<HazardExpansion> hazardExpansionTable;

    @FXML
    private TableColumn<HazardExpansion, String> id;

    @FXML
    private TableColumn<HazardExpansion, String> kind1;

    @FXML
    private TableColumn<HazardExpansion, String> role1;

    @FXML
    private TableColumn<HazardExpansion, String> relator;

    @FXML
    private TableColumn<HazardExpansion, String> role2;

    @FXML
    private TableColumn<HazardExpansion, String> kind2;

    @FXML
    private Label label;

    private ObservableList<HazardExpansion> expansionList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SetCellfactories();
        PopulateExpansionTable();
        label.setText("Hazard Expansion for HD" + hazard.getId());
    }
    
    @FXML
    void onShowGraph(ActionEvent event) {
        ShowGraph();
    }

    private void SetCellfactories() {
        this.id.setCellValueFactory(new PropertyValueFactory<>("id"));

        /*Expansion Table*/
        this.kind1.setCellValueFactory((TableColumn.CellDataFeatures<HazardExpansion, String> cellData)
                -> Bindings.concat(cellData.getValue().getRootKind().getKind()));

        this.role1.setCellValueFactory((TableColumn.CellDataFeatures<HazardExpansion, String> cellData)
                -> Bindings.concat(cellData.getValue().getRootRole().getRole()));

        this.relator.setCellValueFactory((TableColumn.CellDataFeatures<HazardExpansion, String> cellData)
                -> Bindings.concat(cellData.getValue().getRelator().getRelator()));

        this.role2.setCellValueFactory((TableColumn.CellDataFeatures<HazardExpansion, String> cellData)
                -> Bindings.concat(cellData.getValue().getLinkedRole().getRole()));

        this.kind2.setCellValueFactory((TableColumn.CellDataFeatures<HazardExpansion, String> cellData)
                -> Bindings.concat(cellData.getValue().getLinkedKind().getKind()));

    }

    private void PopulateExpansionTable() {
        expansionList = FXCollections.observableArrayList();
        DataBaseConnection.selectHazardExpansionByRoleOrID(0, hazard.getId(), expansionList);
        hazardExpansionTable.setItems(expansionList);

    }

    private void ShowGraph() {
        try {
            HazardExpansionGraph frame;
            frame = new HazardExpansionGraph(hazard.getId());
            frame.setResizable(true);
            frame.setSize(1000, 800);
            frame.setVisible(true);
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

}
