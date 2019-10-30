/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Navigation;

import hazard.Constants.StepDescription;
import hazard.Controllers.MainPageController;
import hazard.Controllers.Views.DE_ICHA1Controller;
import hazard.Controllers.Views.DE_IEMSController;
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
public class HazardDescriptionExpansionController extends NavigationInterface {

    public HazardDescriptionExpansionController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    private MainPageController mainPageController;

    @FXML
    private AnchorPane pane;

    /*Hazard and IC*/
    @FXML
    public ToggleButton step1;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    public ToggleButton step2;

    @FXML
    public ToggleButton step3;

    @FXML
    public ToggleButton step4;

    /*Mishap and IE*/
    @FXML
    public ToggleButton SecondStep1;

    @FXML
    public ToggleButton SecondStep2;

    @FXML
    public ToggleButton SecondStep3;

    @FXML
    public ToggleButton SecondStep4;

    private final String phase = "Hazard Description Expansion";

    @FXML
    void onStep1(ActionEvent event) {
        DE_ICHA1Controller controller = new DE_ICHA1Controller(this);
        mainPageController.LoadPaneFromController("/fxml/mainviews/DE_ICHA1.fxml", controller, mainPageController.centerPane,
                phase, "1-4", StepDescription.DEICHASTEP1);
        SetCurrentStep(1);
    }

    @FXML
    void onMishapOrIE(ActionEvent event) {
        DE_IEMSController controller = new DE_IEMSController(this);
        mainPageController.LoadPaneFromController("/fxml/mainviews/DE_IEMS.fxml", controller, mainPageController.centerPane,
                phase, "1-4", StepDescription.DEICHASTEP1);
        SetCurrentStep(2);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UIHelper.getAllButtonsToggle(pane);

        stepMap = CreateMap();
        setStepMap(stepMap);
        setTotalSteps(stepMap.size());
        SetInitialStep(mainPageController.isBackwardsNavigation);
    }

    public Map<Integer, ToggleButton> CreateMap() {
        Map<Integer, ToggleButton> commands = new HashMap<>();
        commands.put(1, step1);
        //SecondStep1
        commands.put(2, SecondStep1);
        
        return commands;
    }

}
