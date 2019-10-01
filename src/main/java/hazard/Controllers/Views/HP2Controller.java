
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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


/**
 * FXML Controller class
 *
 */
public class HP2Controller implements Initializable 
{
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
        
        this.causeList = FXCollections.observableArrayList();        
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
        this.cause.setCellValueFactory(new PropertyValueFactory<>("cause"));                
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
     * @param event MouseEvent
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
    
    
    /**
     * Event that is called when a user press the mouse on a row in cause table
     * If it is a double click we will open a dialog to update information about cause
     * @param event MouseEvent
     */
    @FXML
    protected void onCauseClicked(MouseEvent event)
    {                
        int index = this.causeTableView.getSelectionModel().selectedIndexProperty().get();
        if(index != -1)
        {
            if(event.getClickCount() == 2)
                onBtnEditEvent(null);
        }
    }
    
    
    /* ActionEvents */
    
    /**
     * Event is called when user press button to create a new cause
     * @param event ActionEvent
     */
    @FXML
    protected void onBtnAddEvent(ActionEvent event)
    { 
        boolean bError = false;
        String newCause = "";
        int hazardId = -1;
        
        int hazardSelectedIndex = this.hazardTableView.getSelectionModel().getSelectedIndex();
        
        if(hazardSelectedIndex >= 0)
        {
            Hazard hazard = this.hazardTableView.getItems().get(hazardSelectedIndex);        
            Optional<List<String>> result = UIHelper.CreateAddCauseDialog("Add cause", hazard, "Description of the cause");
            
            if(result.isPresent())
            {
                List<String> lsResult = result.orElse(null);
                if(lsResult != null && lsResult.size() == 2)
                {
                    String value1 = lsResult.get(0);                    
                    String strHazardId = lsResult.get(1);
                
                    if(value1 != null && value1.trim().length() > 0)
                    {
                        try
                        {
                            hazardId = Integer.parseInt(strHazardId);                                                                           
                        }
                        catch(NumberFormatException nfe)
                        {
                            return;
                        }
                        
                        // Update database
                        DataBaseConnection.insertCause(value1, hazardId); 
                        
                        // Update table with causes
                        PopulateHazardEventTableFromDatabase(hazardId);
                    }  
                    else
                    {
                        bError = true;
                    }
                }
                else
                {
                    bError = true;
                }
                    
                
                if(bError)
                {// User hasent typed in information about new cause
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("You have to type in information about the new hazard!");
                    alert.showAndWait();                
                }                    
            }   
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("You have to select a Hazard!");
            alert.showAndWait();
            return;            
        }
    }
    
    
    /**
     * Event is called when user press button to edit a cause
     * @param event ActionEvent
     */
    @FXML
    protected void onBtnEditEvent(ActionEvent event)
    {
        int hazardId = -1;
        int causeId = -1;
        String updatedCause = "";
        String updatedCauseId = "";
        
        // Get selected rows in tables
        int hazardSelectedIndex = this.hazardTableView.getSelectionModel().getSelectedIndex();        
        int causeSelectedIndex = this.causeTableView.getSelectionModel().getSelectedIndex();
        
        if(hazardSelectedIndex >= 0 && causeSelectedIndex >= 0)
        {
            // Get items from selected rows            
            Hazard hazard = this.hazardTableView.getItems().get(hazardSelectedIndex);  
            Cause cause = this.causeTableView.getItems().get(causeSelectedIndex);            
            
            if(hazard != null && cause != null)
            {
                Optional<List<String>> result = UIHelper.CreateUpdateCauseDialog("Edit cause", cause, "Description of the cause", hazard.getId());
                
                if(result.isPresent())
                {// We have a result. Get it and updates to database
                    List<String> lsResult = result.orElse(null);
                    if(lsResult != null && lsResult.size() == 3)
                    {
                        String value1 = lsResult.get(0);
                        String strCauseId = lsResult.get(1);
                        String strHazardId = lsResult.get(2);                        
                        
                        if(value1 != null && value1.trim().length() > 0 && strCauseId != null && strCauseId.trim().length() > 0 && strHazardId != null && strHazardId.trim().length() > 0)
                        {// User has updated in formation about cause
                            // Save to database and update tables with new information from database
                            updatedCause = value1.trim();
                            updatedCauseId = strCauseId.trim();

                            try
                            {
                                hazardId = Integer.parseInt(strHazardId.trim());
                                causeId = Integer.parseInt(updatedCauseId);
                            }
                            catch(NumberFormatException nfe)
                            {
                                return;
                            }
                            
                            // Update database
                            DataBaseConnection.sqlUpdateCause(updatedCause, causeId);
                            
                            // Update table with causes
                            PopulateHazardEventTableFromDatabase(hazardId);
                        }
                    }                    
                }                
            }
        } 
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("You have to select a Hazard and a Cause!");
            alert.showAndWait();
            return;             
        }
    }

    
    /**
     * Event is called when user press button to remove a cause
     * @param event ActionEvent
     */
    @FXML
    protected void onBtnRemoveEvent(ActionEvent event)
    {
        // Get selected rows in tables
        int hazardSelectedIndex = this.hazardTableView.getSelectionModel().getSelectedIndex();        
        int causeSelectedIndex = this.causeTableView.getSelectionModel().getSelectedIndex();
        
        if(hazardSelectedIndex >= 0 && causeSelectedIndex >= 0)
        {
            // Get items from selected rows            
            Hazard hazard = this.hazardTableView.getItems().get(hazardSelectedIndex);  
            Cause cause = this.causeTableView.getItems().get(causeSelectedIndex);
            
            if(hazard != null && cause != null)
            {
                // Remove cause from database
                DataBaseConnection.delete("cause", cause.getId());
               
                // Update table with cause
                PopulateHazardEventTableFromDatabase(hazard.getId());
            }
        }        
    }    
}