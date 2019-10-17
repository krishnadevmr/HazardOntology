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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author kmoothandas
 */
public class CausesExplorationController implements Initializable {

    public CausesExplorationController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    private MainPageController mainPageController;

    @FXML
    private AnchorPane pane;

    @FXML
    private ToggleButton step1;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private ToggleButton step2;

    @FXML
    private ToggleButton step3;

    @FXML
    private ToggleButton step4;

    @FXML
    private ToggleButton step6;

    @FXML
    private ToggleButton step7;

    private final String phase = "Causes Exploration";

    @FXML
    void onStep1(ActionEvent event) {
        mainPageController.LoadPaneFromController("/fxml/mainviews/CauseExploration.fxml", mainPageController.centerPane,
                phase, "1", StepDescription.CEICHASTEP1);
    }

    @FXML
    void onStep2(ActionEvent event) {
        mainPageController.LoadPaneFromController("/fxml/mainviews/CE_ICHA2.fxml", mainPageController.centerPane,
                phase, "2", StepDescription.CEICHASTEP1);
    }

    @FXML
    void onStep3(ActionEvent event) {
        mainPageController.LoadPaneFromController("/fxml/mainviews/CE_ICHA3.fxml", mainPageController.centerPane,
                phase, "3", StepDescription.CEICHASTEP1);
    }

    @FXML
    void onStep4(ActionEvent event) {
        mainPageController.LoadPaneFromController("/fxml/mainviews/CE_ICHA4.fxml", mainPageController.centerPane,
                phase, "4", StepDescription.CEICHASTEP1);
    }

    @FXML
    void onStep6(ActionEvent event) {

    }

    @FXML
    void onStep7(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UIHelper.getAllButtonsToggle(pane);
    }

}
