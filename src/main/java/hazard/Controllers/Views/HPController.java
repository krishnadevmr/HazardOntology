
package hazard.Controllers.Views;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Hazard2;
import hazard.HazardClasses.MishapVictim2;
import hazard.HazardClasses.PossibleHazardRelator;
import hazard.Helpers.UIHelper;
import hazard.Services.DatabaseManager;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author kmoothandas
 */
public class HPController implements Initializable {

    /* table victimTable */
    @FXML
    private TableView<MishapVictim2> victimTable;

    @FXML
    private TableColumn<MishapVictim2, String> possibleVictim;

    @FXML
    private TableColumn<MishapVictim2, String> possibleHarm;

    private ObservableList<MishapVictim2> mishapVictimsList;

    
    /* table possibleHazardRelatorTable */
    @FXML
    private TableView<PossibleHazardRelator> possibleHazardRelatorTable;

    @FXML
    private TableColumn<PossibleHazardRelator, String> possibleExposure;

    @FXML
    private TableColumn<PossibleHazardRelator, String> possibleHazard;

    private ObservableList<PossibleHazardRelator> possibleHazardRelatorsList;

    
    /* table hazardTable */
    @FXML
    private TableView<Hazard2> hazardTable;

    @FXML
    private TableColumn<Hazard2, String> hazardId;

    @FXML
    private TableColumn<Hazard2, String> mishapVictim;

    @FXML
    private TableColumn<Hazard2, String> exposure;

    @FXML
    private TableColumn<Hazard2, String> hazardElement;

    @FXML
    private TableColumn<Hazard2, String> truthmaker;

    @FXML
    private TableColumn<Hazard2, String> hazardDescription;

    private ObservableList<Hazard2> hazardList;

    
    /* Mouse events */
    
    /**
     * Event called when a user select a row in the table with Mishap victims
     * It will populate table for possible hazard relator with information
     * @param event MouseEvent
     */
    @FXML
    void onVictimSelected(MouseEvent event) {
        int index = victimTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            GetPossibleHazardRelators(index);
        }
    }
    
    /* ActionEvent */
    
    /**
     * Event is called when a user want to add information about possible harm 
     * @param event ActionEvent
     */
    @FXML
    void onAddHazard(ActionEvent event) {
        AddHazard();
    }

    /**
     * Event is called when user want to remove a hazard
     * @param event ActionEvent
     */
    @FXML
    void onRemoveHazard(ActionEvent event) {
        RemoveHazard();
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SetCellfactories();

        PopulateMishapVictimsTable();

        PopulateHazardTable();

        possibleHazardRelatorsList = FXCollections.observableArrayList();
    }

    /**
     * Method get possible hazard relators connected to a selected Mishap victim
     * Updates table with that information
     * @param index Index for selected row in the Mishap victim table
     */
    private void GetPossibleHazardRelators(int index) {
        MishapVictim2 victim = victimTable.getItems().get(index);

        int roleId = victim.getRoleId();
        String sql = "select relator, role, roleid, relatorid from relatortorole where "
                + "relatorid in(SELECT relatorid from relatortorole where roleid = " + roleId + ") ";
        
        possibleHazardRelatorsList.clear();
        DatabaseManager.GetPossibleHazardRelators(sql, possibleHazardRelatorsList);
        
        //String sql2 = "select kind, kindid from roletoplay where roleid = "+roleId;

        possibleHazardRelatorTable.setItems(possibleHazardRelatorsList);
    }
    
    private void GetKinds(){
        String sql = "select kind, kindid from roletoplay where roleid = 3";
    }

    
    /**
     * Method read information about mishap victims from database and populate table with information
     */
    private void PopulateMishapVictimsTable() {
        mishapVictimsList = FXCollections.observableArrayList();
        DataBaseConnection.sql("select * from mishapvictim2 ", "mishapvictim2", mishapVictimsList);
        victimTable.setItems(mishapVictimsList);
    }

    /**
     * Method read information about hazard from database and populate table with that information
     */
    private void PopulateHazardTable() 
    {
        hazardList = FXCollections.observableArrayList();
        //DataBaseConnection.sql("select * from hazard2", "hazard2", hazardList);
        DataBaseConnection.selectAll("hazard2", hazardList);
        hazardTable.setItems(hazardList);
    }

    /**
     * Method set CellValueFactory value for all table
     */
    private void SetCellfactories() {
        /* victimTable table */
        this.possibleVictim.setCellValueFactory(new PropertyValueFactory<>("role"));
        this.possibleHarm.setCellValueFactory(new PropertyValueFactory<>("harm"));

        this.possibleExposure.setCellValueFactory(new PropertyValueFactory<>("exposure"));
        this.possibleHazard.setCellValueFactory(new PropertyValueFactory<>("hazardElement"));
        
        //this.hazardId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.hazardId.setCellValueFactory((TableColumn.CellDataFeatures<Hazard2, String> cellData) -> Bindings.concat("HD",cellData.getValue().getId()));
        
        //this.mishapVictim.setCellValueFactory(new PropertyValueFactory<>("mishapVictim"));
        this.mishapVictim.setCellValueFactory(cellData -> Bindings.concat(
                cellData.getValue().getMishapVictim(), System.lineSeparator() ,
                "(", cellData.getValue().getVictimKind(), ")"));
        
        this.exposure.setCellValueFactory(new PropertyValueFactory<>("exposure"));
        //this.hazardElement.setCellValueFactory(new PropertyValueFactory<>("hazardElement"));
        this.hazardElement.setCellValueFactory(cellData -> Bindings.concat(
                cellData.getValue().getHazardElement(), System.lineSeparator() ,
                "(", cellData.getValue().getHazardKind(), ")"));
        this.truthmaker.setCellValueFactory(new PropertyValueFactory<>("truthmaker"));
        this.hazardDescription.setCellValueFactory(new PropertyValueFactory<>("hazardDescription"));
        
    }
    
    
    /**
     * Method show a dialogbox where a user can add harm and a description of the harm
     * It also save new data to database
     */
    private void AddHazard()
    {   
        int victimTableIndex = victimTable.getSelectionModel().selectedIndexProperty().get();
        int possibleHazardRelatorTableIndex = possibleHazardRelatorTable.getSelectionModel().selectedIndexProperty().get();
        
        if(victimTableIndex != -1 && possibleHazardRelatorTableIndex != -1)
        {
            Optional<List<String>> result = UIHelper.AddHarmDialog("Add hazard");

            if(result.isPresent())
            { 
                MishapVictim2 mishapVictim2 = victimTable.getItems().get(victimTableIndex);

                PossibleHazardRelator possibleHazardRelator = possibleHazardRelatorTable.getItems().get(possibleHazardRelatorTableIndex);

                Hazard2 hazard = new Hazard2(mishapVictim2.getRole(), mishapVictim2.getRoleId(),
                        possibleHazardRelator.getExposure(), possibleHazardRelator.getExposureId(), 
                        possibleHazardRelator.getHazardElement(), possibleHazardRelator.getHazardElementId(), 
                        result.get().get(0), result.get().get(1),0);

                DatabaseManager.InsertHazard(hazard);

                PopulateHazardTable();
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("You have to select Mishap victim, exposure and hazard element!");
            alert.showAndWait();           
        }
    }

    
    /**
     * Method remove a selected hazard
     */
    private void RemoveHazard() 
    {
        int index = hazardTable.getSelectionModel().selectedIndexProperty().get();
        if(index != -1)
        {
            Hazard2 hazard = hazardTable.getItems().remove(index);
            
            DataBaseConnection.delete("hazard2", hazard.getId());
        }        
    }
}
