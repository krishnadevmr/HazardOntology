
package hazard.Controllers.Views;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Hazard;
import hazard.HazardClasses.HazardElement;
import hazard.HazardClasses.MishapVictim;
import hazard.HazardClasses.Play;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 */
public class HP1Controller implements Initializable 
{   
    /* Mishap Victims table */    
    @FXML    
    private TableView<MishapVictim> mishapVictimsTableView;
        
    @FXML
    private TableColumn<MishapVictim, Integer> mishapVictimsId;
    
    @FXML
    private TableColumn<MishapVictim, String> mishapVictimsRole;  
    
    @FXML
    private TableColumn<MishapVictim, String> mishapVictimsKind;

    @FXML
    private TableColumn<MishapVictim, String> mishapVictimsRelator;
        
    private ObservableList<MishapVictim> mishapVictimsList;
    
    
    /* Hazard Elements table */ 
    @FXML
    private TableView<HazardElement> hazardElementTableView;
    
    @FXML
    private TableColumn<HazardElement, String> hazardElementRole;  
    
    @FXML
    private TableColumn<HazardElement, String> hazardElementKind;  
    
    private ObservableList<HazardElement> hazardElementList;
    
    
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
            
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {        
        SetCellfactories();    
        
        // Load data from database
        PopulateMishapVictimsTableFromDatabase(); 
        
        PopulateHazardTableFromDatabase();        
        
        this.hazardElementList = FXCollections.observableArrayList();
    }    
    
    
    /**
     * Method reads all mishapvictim from database
     * Adds data to mishapVictimsTableView
     */
    private void PopulateMishapVictimsTableFromDatabase()
    {
        // Rensa listan
        this.mishapVictimsList = FXCollections.observableArrayList();
        DataBaseConnection.sql("Select * from mishapvictim", "mishapvictim", this.mishapVictimsList);        
        this.mishapVictimsTableView.setItems(this.mishapVictimsList);        
    }
    
    
    /**
     * Method reads all hazard from database
     * Adds data to hazardTableView
     */
    private void PopulateHazardTableFromDatabase()
    {
        // Rensa listan
        this.hazardList = FXCollections.observableArrayList();        
        DataBaseConnection.sql("SELECT * FROM hazard;", "hazard", this.hazardList);        
        this.hazardTableView.setItems(this.hazardList);
    }
    
    
    /**
     * Method reads information about hazard elements from database
     * Adds data to hazardElementTableView
     * @param index Index for selected row in table mishapVictimsTableView
     */
    private void SetHazardElement(int index)
    {
        // Rensa listan
        this.hazardElementList = FXCollections.observableArrayList();
        
        int relatorId = mishapVictimsTableView.getItems().get(index).getRelatorID();       
        
        DataBaseConnection.sql("SELECT roletoplay.role,roletoplay.kind FROM relatortorole, roletoplay WHERE relatortorole.relatorid ="
                + relatorId + " AND relatortorole.roleid = roletoplay.roleid;",
                "hazardelement", this.hazardElementList);        
                
        this.hazardElementTableView.setItems(this.hazardElementList);
    }   
    
    
    /**
     * Method set CellValueFactory value for all table
     */
    private void SetCellfactories()
    {
        /* Mishap Victims table */
        this.mishapVictimsId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.mishapVictimsRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        this.mishapVictimsKind.setCellValueFactory(new PropertyValueFactory<>("kind"));
        this.mishapVictimsRelator.setCellValueFactory(new PropertyValueFactory<>("relator"));
                
        /* Hazard Elements table */     
        this.hazardElementRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        this.hazardElementKind.setCellValueFactory(new PropertyValueFactory<>("kind"));
        
        /* Hazard table */
        this.hazardId.setCellValueFactory(new PropertyValueFactory<>("id"));        
        this.hazard.setCellValueFactory(new PropertyValueFactory<>("hazard"));        
        this.hazardDescription.setCellValueFactory(new PropertyValueFactory<>("hazardDescription"));       
    }  
    
    
    /* MouseEvents */
    
    /**
     * Event that is called when a user press the mouse on a row in table for Mishap victims
     * mishapVictimsTableView
     * Adds data to hazardElementTableView
     * @param event MouseEvent
     */
    @FXML
    protected void onMishapVictimsClicked(MouseEvent event)
    {
        System.out.println("hazard.Controllers.Views.HP1Controller.onMishapVictimsClicked()");
        
        int index = mishapVictimsTableView.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) 
        {
            if(event.getClickCount() == 2)
            {
                // TODO dubbelklick i mishapVictimsTableView
                
                System.out.println("hazard.Controllers.Views.HP1Controller.onMishapVictimsClicked(). 2 click");
            }
            else
            {
                System.out.println("hazard.Controllers.Views.HP1Controller.onMishapVictimsClicked(). 1 click");
                SetHazardElement(index);
            }                        
        }        
    }
    
    
    /**
     * Event that is called when a user press the mouse on a row in table for hazards
     * When we detect a double click with the mouse we open the edit dialog for the hazard
     * @param event MouseEvent
     */
    @FXML
    protected void onHazardClicked(MouseEvent event)
    {
        int index = this.hazardTableView.getSelectionModel().selectedIndexProperty().get();
        if(index != -1)
        {
            if(event.getClickCount() == 2)
                onBtnEditHazard(null);
        }
    }

    
    /* ActionEvents */
    
    /**
     * Event is called when a user press button to add a new hazard
     * @param event ActionEvent
     */
    @FXML
    protected void onBtnAddHazard(ActionEvent event)
    {
        boolean bError = false;
        String newHazard = "";
        String newHarm = "";
        
        // Get index of selected rows in tables
        // User must have selected hazard elements and hazard
        int victimsSelectedIndex = this.mishapVictimsTableView.getSelectionModel().getSelectedIndex();
        int hazardElementSelectedIndex = this.hazardElementTableView.getSelectionModel().getSelectedIndex();

        if(victimsSelectedIndex < 0 || hazardElementSelectedIndex < 0)
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("You have to select Hazard element and Hazard!");
            alert.showAndWait();
            return;
        }        
        
        // Get objects from tables
        MishapVictim mishapVictim = mishapVictimsTableView.getItems().get(victimsSelectedIndex);
        HazardElement hazardElement = this.hazardElementTableView.getItems().get(hazardElementSelectedIndex);
                
        StringBuilder strBuild = new StringBuilder();
        strBuild.append(mishapVictim.getRelator());
        strBuild.append("(");
        strBuild.append(mishapVictim.getRole());
        strBuild.append(":");
        strBuild.append(mishapVictim.getKind());
        strBuild.append(")(");
        strBuild.append(hazardElement.getRole());
        strBuild.append("<%s>");    // This, %s, will be updated with text from textfield
        strBuild.append(":");
        strBuild.append(hazardElement.getKind());
        strBuild.append(")");     
        
        String descriptionFormat = strBuild.toString();
                
        Optional<List<String>> result = UIHelper.CreateAddHazardDialog("Add hazard", "HarmTruthMaker", "Description of the HarmTruthMaker", descriptionFormat);
        
        if(result.isPresent())
        {// We have a result. Get it and save to database
            List<String> lsResult = result.orElse(null);
            if(lsResult != null && lsResult.size() == 2)
            {
                String key = lsResult.get(0);
                String value =  lsResult.get(1);
            
                if(key != null && key.trim().length() > 0 && value != null & value.trim().length() > 0)
                {// User has typed in information about new hazard
                    // Save to database and update tables with new information from database
                    newHazard = String.format(descriptionFormat, key);
                    newHarm = value;                        
                    DataBaseConnection.insertHazard(newHazard, newHarm);

                    // Update table with hazard's
                    PopulateHazardTableFromDatabase();                   
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
            {// User hasent typed in information about new hazard
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("You have to type in information about the updated hazard!");
                alert.showAndWait();                 
            }
        }            
    }
    
    
    /**
     * Event is called when a user press button to edit a hazard
     * @param event ActionEvent
     */
    @FXML
    protected void onBtnEditHazard(ActionEvent event)
    {  
        boolean bError = false;
        String updatedHazard = "";
        String updatedHarm = "";
        Integer updateId = null;
          
        int hazardSelectedIndex = this.hazardTableView.getSelectionModel().getSelectedIndex();

        if(hazardSelectedIndex < 0)
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("You have to select a hazard!");
            alert.showAndWait();
            return;
        } 
        
        // Get objects from tables
        Hazard hazard = this.hazardTableView.getItems().get(hazardSelectedIndex);        
               
        Optional<List<String>> result = UIHelper.CreateUpdateHazardDialog("Update hazard", "HarmTruthMaker", "Description of the HarmTruthMaker", 
                hazard.getHazard(), hazard.getHazardDescription(), hazard.getId());
        
        if(result.isPresent())
        {// We have a result. Get it and save to database
            List<String> lsResult = result.orElse(null);
            if(lsResult != null && lsResult.size() == 3)
            {
                String value1 = lsResult.get(0);
                String value2 =  lsResult.get(1);
                String strId = lsResult.get(2);
            
                if(value1 != null && value1.trim().length() > 0 && value2 != null & value2.trim().length() > 0 && strId != null && strId.trim().length() > 0)
                {// User has typed in information about new hazard
                    // Save to database and update tables with new information from database
                    updatedHazard = value1.trim();
                    updatedHarm = value2.trim();
                            
                    try
                    {
                        updateId = Integer.parseInt(strId.trim());
                    }
                    catch(NumberFormatException nfe)
                    {
                        return;
                    }
                    
                    // Update database
                    DataBaseConnection.updateHazard(updatedHazard, updatedHarm, updateId);

                    // Update table with hazard's                   
                    PopulateHazardTableFromDatabase();                   
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
            {// User hasent typed in information about new hazard
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("You have to type in information about the updated hazard!");
                alert.showAndWait();                 
            }            
        }                  
    }
    
    
    /**
     * Even is called when a user press button to remove a hazard
     * @param event ActionEvent
     */
    @FXML
    protected void onBtnRemoveHazard(ActionEvent event)
    {        
        if(!this.hazardTableView.getItems().isEmpty())
        {
            int index = hazardTableView.getSelectionModel().selectedIndexProperty().get();
            if(index != -1)
            {                        
                Play obj = hazardTableView.getItems().remove(index);
                if(obj != null)
                    DataBaseConnection.delete("hazard", obj.getId());
            }
        }
    }
}
