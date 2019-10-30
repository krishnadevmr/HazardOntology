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
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author kmoothandas
 */
public class CausesExplorationController extends NavigationInterface {

    public CausesExplorationController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    private final MainPageController mainPageController;

    @FXML
    private AnchorPane pane;

    @FXML
    private ToggleButton step1;

    @FXML
    private ToggleButton step2;

    @FXML
    private ToggleButton step4;

    @FXML
    private ToggleButton step5;

    @FXML
    private ToggleButton step6;

    private final String phase = "Causes Exploration";

    @FXML
    void onICHAStep1(ActionEvent event) {
        mainPageController.LoadPaneFromController("/fxml/mainviews/CE_ICHA1.fxml", mainPageController.centerPane,
                phase, "1", StepDescription.CESTEP1);
        SetCurrentStep(1);
        mainPageController.SetNavigationButton(false, false);
    }

    @FXML
    void onICHAStep2(ActionEvent event) {
        mainPageController.LoadPaneFromController("/fxml/mainviews/CE_ICHA2.fxml", mainPageController.centerPane,
                phase, "2", StepDescription.CEICHASTEP2);
        SetCurrentStep(2);
        mainPageController.SetNavigationButton(false, false);
    }

    @FXML
    void onICHAStep3(ActionEvent event) {
        //Does Nothing. Require Clarity from Paper
    }

    @FXML
    void onICHAStep4(ActionEvent event) {
        mainPageController.LoadPaneFromController("/fxml/mainviews/CE_ICHA4.fxml", mainPageController.centerPane,
                phase, "4", StepDescription.CEICHASTEP4);
        SetCurrentStep(3);
        mainPageController.SetNavigationButton(false, false);
    }

    @FXML
    void onIEMSStep1(ActionEvent event) {
        mainPageController.LoadPaneFromController("/fxml/mainviews/CE_IEMS1.fxml", mainPageController.centerPane,
                phase, "1", StepDescription.CESTEP1);
        SetCurrentStep(4);
        mainPageController.SetNavigationButton(false, false);
    }

    @FXML
    void onIEMSStep2(ActionEvent event) {
        mainPageController.LoadPaneFromController("/fxml/mainviews/CE_IEMS2.fxml", mainPageController.centerPane,
                phase, "2", StepDescription.CEIEMSSTEP2);
        SetCurrentStep(5);
        mainPageController.SetNavigationButton(false, true);
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
        commands.put(2, step2);
        commands.put(3, step4);
        commands.put(4, step5);
        commands.put(5, step6);
        return commands;
    }

}
