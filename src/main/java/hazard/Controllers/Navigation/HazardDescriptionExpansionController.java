/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Navigation;

import hazard.Constants.StepDescription;
import hazard.Controllers.MainPageController;
import hazard.Controllers.Views.DE_ICHA1Controller;
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
public class HazardDescriptionExpansionController implements Initializable {

    public HazardDescriptionExpansionController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    private MainPageController mainPageController;

    @FXML
    private AnchorPane pane;

    /*Hazard and IC*/
    @FXML
    private ToggleButton step1;

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
    private ToggleButton SecondStep1;

    @FXML
    private ToggleButton SecondStep2;

    @FXML
    private ToggleButton SecondStep3;

    @FXML
    private ToggleButton SecondStep4;

    private final String phase = "Hazard Description Expansion";

    @FXML
    void onStep1(ActionEvent event) {
        DE_ICHA1Controller controller = new DE_ICHA1Controller(this);
        mainPageController.LoadPaneFromController("/fxml/mainviews/DE_ICHA1.fxml", controller, mainPageController.centerPane,
                phase, "1", StepDescription.DEICHASTEP1);
    }

    @FXML
    void onMishapOrIE(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UIHelper.getAllButtonsToggle(pane);
    }

}
