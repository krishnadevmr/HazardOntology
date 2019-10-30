/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers;

import hazard.Controllers.Navigation.MishapVictimIdentificationController;
import hazard.Controllers.Navigation.HazardPopulationController;
import hazard.Controllers.Navigation.SystemDescriptionFormalizationController;
import hazard.Constants.StringConstants;
import hazard.Controllers.Navigation.CausesExplorationController;
import hazard.Controllers.Navigation.HazardDescriptionCharacterizationController;
import hazard.Controllers.Navigation.HazardDescriptionExpansionController;
import hazard.Controllers.Navigation.NavigationInterface;
import hazard.Helpers.UIHelper;
import hazard.Services.DatabaseManager;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kmoothandas
 */
public class MainPageController extends InitializableWithLoad {

    @FXML
    private VBox navBox;

    @FXML
    void onOhi1(ActionEvent event) {

        SystemDescriptionFormalizationController controller = new SystemDescriptionFormalizationController(this);
        LoadPaneFromController("/fxml/navigation/SystemDescriptionFormalization.fxml", controller, secondPane);
        currentPhase = 1;
        navigationController = controller;
    }

    @FXML
    void onOhi2(ActionEvent event) {
        MishapVictimIdentificationController controller = new MishapVictimIdentificationController(this);
        LoadPaneFromController("/fxml/navigation/MishapVictimIdentification.fxml", controller, secondPane);
        navigationController = controller;
        currentPhase = 2;
    }

    @FXML
    void onOhi3(ActionEvent event) {
        HazardPopulationController controller = new HazardPopulationController(this);
        LoadPaneFromController("/fxml/navigation/HazardPopulation.fxml", controller, secondPane);
        currentPhase = 3;
        navigationController = controller;
    }

    @FXML
    void onOch1(ActionEvent event) {
        HazardDescriptionCharacterizationController controller = new HazardDescriptionCharacterizationController(this);
        LoadPaneFromController("/fxml/navigation/HazardDescriptionCharacterization.fxml", controller, secondPane);
        currentPhase = 4;
        navigationController = controller;
    }

    @FXML
    void onOch2(ActionEvent event) {
        HazardDescriptionExpansionController controller = new HazardDescriptionExpansionController(this);
        LoadPaneFromController("/fxml/navigation/HazardDescriptionExpansion.fxml", controller, secondPane);
        currentPhase = 5;
        navigationController = controller;
    }

    @FXML
    void onOch3(ActionEvent event) {
        CausesExplorationController controller = new CausesExplorationController(this);
        LoadPaneFromController("/fxml/navigation/CausesExploration.fxml", controller, secondPane);
        navigationController = controller;
        currentPhase = 6;
    }

    @FXML
    void onSare1(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Not Implemented");
        alert.setContentText("This is not implemented yet");

        alert.showAndWait();
        ResetUI();
    }

    @FXML
    void onSare2(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Not Implemented");
        alert.setContentText("This is not implemented yet");

        alert.showAndWait();
        ResetUI();
    }

    @FXML
    void onSare3(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Not Implemented");
        alert.setContentText("This is not implemented yet");

        alert.showAndWait();
        ResetUI();
    }

    @FXML
    void loadSession(ActionEvent event) {
        DatabaseManager.LoadDatabaseSession((Stage) navBox.getScene().getWindow());
        navBox.setVisible(true);
        descriptionBox.setVisible(true);
        ResetUI();
        ohiButton1.fire();
    }

    @FXML
    void newSession(ActionEvent event) {
        DatabaseManager.NewDatabaseSession((Stage) navBox.getScene().getWindow());
        navBox.setVisible(true);
        descriptionBox.setVisible(true);
        ohiButton1.fire();
        //onOhi1(null);
    }

    @FXML
    void OnExit(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void OnExport(ActionEvent event) {
        DatabaseManager.ExportToExcel((Stage) navBox.getScene().getWindow(), progressIndicator);
    }

    @FXML
    void onShowCauses(ActionEvent event) {
        LoadController("/fxml/mainviews/AllCauses.fxml", "All Hazards and Causes");
    }

    @FXML
    void onNextStep(ActionEvent event) {
        if (!navigationController.NextStep()) {
            if (currentPhase < totalPhases) {
                currentPhase += 1;
                isBackwardsNavigation = false;
                phaseMap.get(currentPhase).fire();
            }
        }
    }

    @FXML
    void onPrevStep(ActionEvent event) {
        if (!navigationController.PreviousStep()) {
            if (currentPhase > 1) {
                currentPhase -= 1;
                isBackwardsNavigation = true;
                phaseMap.get(currentPhase).fire();
            }
        }
    }

    /*Step Tracking*/
    private Integer currentPhase;
    private NavigationInterface navigationController;
    private Map<Integer, ToggleButton> phaseMap;
    private Integer totalPhases;
    public Boolean isBackwardsNavigation;

    @FXML
    private AnchorPane firstPane;

    @FXML
    private ToggleButton ohiButton1;

    @FXML
    private ToggleButton ohiButton2;

    @FXML
    private ToggleButton ohiButton3;

    @FXML
    private ToggleButton ochButton1;

    @FXML
    private ToggleButton ochButton2;

    @FXML
    private ToggleButton ochButton3;

    @FXML
    private ToggleButton sareButton1;

    @FXML
    private ToggleButton sareButton2;

    @FXML
    private ToggleButton sareButton3;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private AnchorPane secondPane;

    @FXML
    public AnchorPane centerPane;

    @FXML
    public AnchorPane descriptionBox;

    @FXML
    public Label phaseID;

    @FXML
    public Label stepID;

    @FXML
    public Text stepDescription;

    @FXML
    public Text phase;

    @FXML
    public Text stepNumber;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    public Button previousButton;

    @FXML
    public Button nextButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setButtonTexts();
        UIHelper.getAllButtonsToggle(firstPane);
        descriptionBox.setVisible(false);
        navBox.setVisible(false);
        
        currentPhase = 0;
        phaseMap = CreateMap();
        totalPhases = 6;
        isBackwardsNavigation = false;
    }

    private void setButtonTexts() {
        ohiButton1.setText(StringConstants.MAIN_HEADING_STEP_OHI_1);
        ohiButton2.setText(StringConstants.MAIN_HEADING_STEP_OHI_2);
        ohiButton3.setText(StringConstants.MAIN_HEADING_STEP_OHI_3);
        ochButton1.setText(StringConstants.MAIN_HEADING_STEP_OCH_1);
        ochButton2.setText(StringConstants.MAIN_HEADING_STEP_OCH_2);
        ochButton3.setText(StringConstants.MAIN_HEADING_STEP_OCH_3);
        sareButton1.setText(StringConstants.MAIN_HEADING_SARE_ACT_1);
        sareButton2.setText(StringConstants.MAIN_HEADING_SARE_ACT_2);
        sareButton3.setText(StringConstants.MAIN_HEADING_SARE_ACT_3);
    }

    public Map<Integer, ToggleButton> CreateMap() {
        Map<Integer, ToggleButton> commands = new HashMap<>();
        commands.put(1, ohiButton1);
        commands.put(2, ohiButton2);
        commands.put(3, ohiButton3);
        commands.put(4, ochButton1);
        commands.put(5, ochButton2);
        commands.put(6, ochButton3);
        return commands;
    }

    public void LoadPaneFromController(String url, Initializable controller, AnchorPane parent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            loader.setController(controller);
            AnchorPane pane = loader.load();
            
            parent.getChildren().clear();
            parent.getChildren().add(pane);
            AnchorPane.setRightAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
        } catch (IOException | NullPointerException ex) {
            System.err.println(ex);
        }
    }

    public void LoadPaneFromController(String url, AnchorPane parent, String phaseDescription, String Step, String stepText) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            AnchorPane pane = loader.load();
            
            parent.getChildren().clear();
            parent.getChildren().add(pane);
            AnchorPane.setRightAnchor(pane, 5.0);
            AnchorPane.setLeftAnchor(pane, 5.0);
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 15.0);

            this.phase.setText(phaseDescription);
            this.stepDescription.setText(stepText);
            this.stepNumber.setText(Step);

        } catch (IOException | NullPointerException ex) {
            System.err.println(ex);
        }
    }

    public void LoadPaneFromController(String url, Initializable controller, AnchorPane parent, String phaseDescription, String Step, String stepText) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            loader.setController(controller);
            AnchorPane pane = loader.load();
            
            parent.getChildren().clear();
            parent.getChildren().add(pane);
            AnchorPane.setRightAnchor(pane, 5.0);
            AnchorPane.setLeftAnchor(pane, 5.0);
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 15.0);

            this.phase.setText(phaseDescription);
            this.stepDescription.setText(stepText);
            this.stepNumber.setText(Step);

        } catch (IOException | NullPointerException ex) {
            System.err.println(ex);
        }
    }
/*
    public void LoadPaneFromController(String url, AnchorPane parent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            AnchorPane pane = loader.load();
            //pane.set
            parent.getChildren().clear();
            parent.getChildren().add(pane);
            AnchorPane.setRightAnchor(pane, 5.0);
            AnchorPane.setLeftAnchor(pane, 5.0);
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 15.0);

        } catch (IOException | NullPointerException ex) {
            System.err.println(ex);
        }
    }*/

    void ResetUI() {
        stepDescription.setText("");
        phase.setText("");
        stepNumber.setText("");
        secondPane.getChildren().clear();
        centerPane.getChildren().clear();
    }
    
    public void SetNavigationButton(Boolean isFirst, Boolean isLast){
            previousButton.setVisible(!isFirst);
            nextButton.setVisible(!isLast);
    }
}
