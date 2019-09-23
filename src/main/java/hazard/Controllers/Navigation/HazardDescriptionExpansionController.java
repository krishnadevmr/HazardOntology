/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Navigation;

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
public class HazardDescriptionExpansionController implements Initializable {

    public HazardDescriptionExpansionController(MainPageController mainPageController) {
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
    private ToggleButton step5;

    @FXML
    private ToggleButton step6;

    @FXML
    private ToggleButton step7;

    @FXML
    void onStep1(ActionEvent event) {

    }

    @FXML
    void onStep2(ActionEvent event) {

    }

    @FXML
    void onStep3(ActionEvent event) {

    }

    @FXML
    void onStep4(ActionEvent event) {

    }

    @FXML
    void onStep5(ActionEvent event) {

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
