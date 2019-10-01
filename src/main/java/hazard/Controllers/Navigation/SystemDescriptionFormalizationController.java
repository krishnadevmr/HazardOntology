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
public class SystemDescriptionFormalizationController implements Initializable {

    public SystemDescriptionFormalizationController() {
    }

    public SystemDescriptionFormalizationController(MainPageController mainController) {
        this.mainController = mainController;
    }

    private MainPageController mainController;
    
    public String phase = "System Description Formalization";

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
                phase, "1", StepDescription.SDFSTEP1 );
    }

    @FXML
    void onStep2(ActionEvent event) {
        mainController.LoadPaneFromController("/fxml/mainviews/SDF2.fxml", mainController.centerPane,
                phase, "2", StepDescription.SDFSTEP2);

    }

    @FXML
    void onStep3(ActionEvent event) {
        mainController.LoadPaneFromController("/fxml/mainviews/SDF3.fxml", mainController.centerPane,
                phase, "3", StepDescription.SDFSTEP3);

    }

    @FXML
    void onStep4(ActionEvent event) {
        mainController.LoadPaneFromController("/fxml/mainviews/SDF4.fxml", mainController.centerPane,
                phase, "4", StepDescription.SDFSTEP4);
    }

    @FXML
    void onStep5(ActionEvent event) {
        mainController.LoadPaneFromController("/fxml/mainviews/SDF5.fxml", mainController.centerPane,
                phase, "5", StepDescription.SDFSTEP5);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UIHelper.getAllButtonsToggle(pane);
    }





}
