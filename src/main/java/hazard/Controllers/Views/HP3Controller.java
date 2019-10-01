
package hazard.Controllers.Views;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Cause;
import hazard.HazardClasses.Hazard;
import hazard.Helpers.UIHelper;
import java.net.URL;
import java.util.List;
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
 * @author oandersson
 */
public class HP3Controller implements Initializable 
{
    /*
    @FXML
    private Button btnAddSeverityAndProbability;
    @FXML
    private TableColumn<?, ?> id;
    @FXML
    private TableColumn<?, ?> hazard;
    @FXML
    private TableColumn<?, ?> hazardDescription;
    @FXML
    private TableColumn<?, ?> probability;
    @FXML
    private TableColumn<?, ?> severity;
    @FXML
    private TableColumn<?, ?> riskevaluation;
    */
    
    /* Hazard table */    
    @FXML
    private TableView<Hazard> hazardTableView;

    @FXML
    private TableColumn<Hazard, Integer> hazardId;  

    @FXML
    private TableColumn<Hazard, String> hazard;  
    
    @FXML
    private TableColumn<Hazard, String> hazardDescription;
    
    private ObservableList<Hazard> hazardList; 
    
    
    
    /* Cause table */
    @FXML
    private TableView<Cause> causeTableView;
    
    @FXML
    private TableColumn<Cause, String> cause;                 

    @FXML
    private TableColumn<Cause, Double> severity;    

    @FXML
    private TableColumn<Cause, Double> probability;    

    @FXML
    private TableColumn<Cause, Double> riskevaluation; 
    
    private ObservableList<Cause> causeList;    

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        SetCellfactories();
        
        // Load data from database        
        PopulateHazardTableFromDatabase();        
    }  
        
    
    /**
     * Method read hazard data from database. Update hazard table with this information
     */
    private void PopulateHazardTableFromDatabase()
    {
        // Rensa listan
        this.hazardList = FXCollections.observableArrayList();        
        DataBaseConnection.sql("SELECT * FROM hazard;", "hazard", this.hazardList);        
        this.hazardTableView.setItems(this.hazardList);        
    }

    
    /**
     *  Method set CellValueFactory value for all table
     */
    private void SetCellfactories()
    {
        /* Hazard table */
        this.hazardId.setCellValueFactory(new PropertyValueFactory<>("id"));        
        this.hazard.setCellValueFactory(new PropertyValueFactory<>("hazard"));        
        this.hazardDescription.setCellValueFactory(new PropertyValueFactory<>("hazardDescription"));   
                
        /* Cause table */                
        this.cause.setCellValueFactory(new PropertyValueFactory<Cause, String>("cause"));
        this.severity.setCellValueFactory(new PropertyValueFactory<Cause, Double>("severity"));
        this.probability.setCellValueFactory(new PropertyValueFactory<Cause, Double>("probability"));
        this.riskevaluation.setCellValueFactory(new PropertyValueFactory<Cause, Double>("riskevaluation"));
    }
    
    
    
    /**
     * Method read caues for a hazard from database.
     * Updates cause table with this information
     * @param hazardId 
     */
    private void PopulateHazardEventTableFromDatabase(int hazardId)
    {
        // Rensa listan             
        this.causeList = FXCollections.observableArrayList();          
        DataBaseConnection.sql("SELECT * FROM cause WHERE cause.hazardid=" + hazardId + ";", "cause", this.causeList);          
        this.causeTableView.setItems(this.causeList);        
    }     
    
    
    /* MouseEvents */   
    
    /**
     * Event that is called when a user press the mouse on a row in table for hazards
     * hazardTableView
     * Adds data to the hazard events table
     * @param event 
     */
    @FXML
    protected void onHazardClicked(MouseEvent event)
    {       
        int index = this.hazardTableView.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) 
        {// Rensa listan            
            Hazard hazard = hazardTableView.getItems().get(index);
            if(hazard != null)
            {
              this.causeList = FXCollections.observableArrayList();
              DataBaseConnection.sql("SELECT * FROM cause WHERE cause.hazardid=" + hazard.getId() + ";", "cause", this.causeList);
              this.causeTableView.setItems(this.causeList);
            } 
        }        
    }    
    
    
    /*   
    @FXML
    protected void onCauseClicked(MouseEvent event)
    {                
        int index = this.causeTableView.getSelectionModel().selectedIndexProperty().get();
        if(index != -1)
        {
            if(event.getClickCount() == 2)
                onBtnEditEvent(null);
        }
    }*/
    /* ActionEvents */
    
    /**
     * Event is called when user press button to create severity and probability
     * @param event ActionEvent
     */
    @FXML
    protected void onBtnAddSeverityAndProbability(ActionEvent event)
    {
        int index = this.hazardTableView.getSelectionModel().selectedIndexProperty().get();
        if(index != -1)
        {
            Hazard hazard = hazardTableView.getItems().get(index);
            if(hazard != null)
            {
                Optional<List<String>> result = UIHelper.CreateAddSeverityAndProbabilityDialog("Add Severity And Probability", "Enter the Severity of the Cause And the Probability of it happening", hazard.getId());
                
                if(result.isPresent())
                {
                    
                }
            }
        }
        
    }        
}
