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
public class MishapVictimIdentificationController extends NavigationInterface {

    public MishapVictimIdentificationController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    private MainPageController mainPageController;

    @FXML
    private AnchorPane pane;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private ToggleButton step1;

    @FXML
    private ToggleButton step2;

    private String phase = "Mishap Victim Identification";

    @FXML
    void onMishapVictimIdentification1(ActionEvent event) {
        mainPageController.LoadPaneFromController("/fxml/mainviews/MVI.fxml", mainPageController.centerPane,
                phase, "1", StepDescription.MVISTEP);
        SetCurrentStep(1);
        mainPageController.SetNavigationButton(false, false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UIHelper.getAllButtonsToggle(pane);
        //step1.fire();
        //SetCurrentStep(1);
        stepMap = CreateMap();
        setStepMap(stepMap);
        setTotalSteps(stepMap.size());
        SetInitialStep(mainPageController.isBackwardsNavigation);
    }

    public Map<Integer, ToggleButton> CreateMap() {
        Map<Integer, ToggleButton> commands = new HashMap<>();
        commands.put(1, step1);
        return commands;
    }
}
