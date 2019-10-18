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

    private final MainPageController mainPageController;

    @FXML
    private AnchorPane pane;

    private final String phase = "Causes Exploration";

    @FXML
    void onICHAStep1(ActionEvent event) {
        mainPageController.LoadPaneFromController("/fxml/mainviews/CE_ICHA1.fxml", mainPageController.centerPane,
                phase, "1", StepDescription.CEICHASTEP1);
    }

    @FXML
    void onICHAStep2(ActionEvent event) {
        mainPageController.LoadPaneFromController("/fxml/mainviews/CE_ICHA2.fxml", mainPageController.centerPane,
                phase, "2", StepDescription.CEICHASTEP1);
    }

    @FXML
    void onICHAStep3(ActionEvent event) {
        mainPageController.LoadPaneFromController("/fxml/mainviews/CE_ICHA3.fxml", mainPageController.centerPane,
                phase, "3", StepDescription.CEICHASTEP1);
    }

    @FXML
    void onICHAStep4(ActionEvent event) {
        mainPageController.LoadPaneFromController("/fxml/mainviews/CE_ICHA4.fxml", mainPageController.centerPane,
                phase, "4", StepDescription.CEICHASTEP1);
    }

    @FXML
    void onIEMSStep1(ActionEvent event) {
        mainPageController.LoadPaneFromController("/fxml/mainviews/CE_IEMS1.fxml", mainPageController.centerPane,
                phase, "1", StepDescription.CEICHASTEP1);
    }

    @FXML
    void onIEMSStep2(ActionEvent event) {
        mainPageController.LoadPaneFromController("/fxml/mainviews/CE_IEMS2.fxml", mainPageController.centerPane,
                phase, "2", StepDescription.CEICHASTEP1);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UIHelper.getAllButtonsToggle(pane);
    }

}
