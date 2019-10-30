/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Navigation;

import hazard.Constants.StepDescription;
import hazard.Controllers.MainPageController;
import hazard.Helpers.UIHelper;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author kmoothandas
 */
public class SystemDescriptionFormalizationController extends NavigationInterface {

    public SystemDescriptionFormalizationController() {
    }

    public SystemDescriptionFormalizationController(MainPageController mainController) {
        this.mainController = mainController;
    }

    private MainPageController mainController;

    public String phase = "System Description Formalization";

    //public Integer currentStep;
    
    //Map<Integer, ToggleButton> stepMap;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private AnchorPane pane;

    @FXML
    private ToggleButton step1;

    @FXML
    private ToggleButton step2;

    @FXML
    private ToggleButton step3;

    @FXML
    private ToggleButton step4;

    @FXML
    private ToggleButton step5;

    //public String step1 =
    @FXML
    void onStep1(ActionEvent event) {
        mainController.LoadPaneFromController("/fxml/mainviews/SDF1.fxml", mainController.centerPane,
                phase, "1", StepDescription.SDFSTEP1);
        SetCurrentStep(1);
    }

    @FXML
    void onStep2(ActionEvent event) {
        mainController.LoadPaneFromController("/fxml/mainviews/SDF2.fxml", mainController.centerPane,
                phase, "2", StepDescription.SDFSTEP2);
        SetCurrentStep(2);
    }

    @FXML
    void onStep3(ActionEvent event) {
        mainController.LoadPaneFromController("/fxml/mainviews/SDF3.fxml", mainController.centerPane,
                phase, "3", StepDescription.SDFSTEP3);
        SetCurrentStep(3);
    }

    @FXML
    void onStep4(ActionEvent event) {
        mainController.LoadPaneFromController("/fxml/mainviews/SDF4.fxml", mainController.centerPane,
                phase, "4", StepDescription.SDFSTEP4);
        SetCurrentStep(4);
    }

    @FXML
    void onStep5(ActionEvent event) {
        mainController.LoadPaneFromController("/fxml/mainviews/SDF5.fxml", mainController.centerPane,
                phase, "5", StepDescription.SDFSTEP5);
        SetCurrentStep(5);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UIHelper.getAllButtonsToggle(pane);
        stepMap = CreateMap();
        setStepMap(stepMap);
        setTotalSteps(stepMap.size());
        SetInitialStep(mainController.isBackwardsNavigation);
    }

    /*@Override
    public void SetCurrentStep(Integer stepNumber) {
        this.currentStep = stepNumber;
    }*/
/*
    @Override
    public void NextStep() {
        Integer nextStep = currentStep + 1;
        stepMap.get(nextStep).fire();
    }

    @Override
    public void PreviousStep() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    
    public Map<Integer, ToggleButton> CreateMap(){
        Map<Integer, ToggleButton> commands = new HashMap<>();
        commands.put(1, step1);
        commands.put(2, step2);
        commands.put(3, step3);
        commands.put(4, step4);
        commands.put(5, step5);
        return commands;
    }
/*
    public void SetInitialStep(){
        if (mainController != null && mainController.isBackwardsNavigation){
            SetCurrentStep(totalSteps);
            step5.fire();
        } else {
            SetCurrentStep(1);
            step1.fire();
        }
    }*/
}
