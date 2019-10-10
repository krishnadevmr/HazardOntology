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
public class HazardDescriptionCharacterizationController implements Initializable {

    public HazardDescriptionCharacterizationController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    private MainPageController mainPageController;

    @FXML
    private AnchorPane pane;
    
    @FXML
    private ToggleButton step1;

    @FXML
    private ToggleGroup toggleGroup;
    
    private final String phase = "Hazard Description Categorization";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UIHelper.getAllButtonsToggle(pane);
    }
    
    @FXML
    void onStep1(ActionEvent event) {
        mainPageController.LoadPaneFromController("/fxml/mainviews/CHD.fxml", mainPageController.centerPane,
                phase, "1", StepDescription.CHDSTEP);
    }

}
