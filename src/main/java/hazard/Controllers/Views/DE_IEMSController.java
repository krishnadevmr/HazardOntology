/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Views;

import hazard.Controllers.Navigation.HazardDescriptionExpansionController;
import hazard.Controllers.Subviews.DE_Step1;
import hazard.HazardClasses.Hazard2;
import hazard.Helpers.UIHelper;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author kmoothandas
 */
public class DE_IEMSController extends DE_Step1 {

    public DE_IEMSController(HazardDescriptionExpansionController buttonController) {
        this.buttonController = buttonController;
    }

    /*Radio Buttons*/
    @FXML
    private RadioButton mishapRadio;

    @FXML
    private RadioButton initERadio;

    @FXML
    private ToggleGroup hazardCategoryRadio;

    HashMap<Integer, ToggleButton> buttonMap;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SetCellfactories();
        hazardCategoryRadio.selectToggle(initERadio);
        UpdateCategoryMap();
        PopulateHazardTable(categoryMap.get(hazardCategoryRadio.getSelectedToggle()));
        currentStep = 1;
        buttonMap = new HashMap<>();
        buttonMap.put(1, buttonController.SecondStep1);
        buttonMap.put(2, buttonController.SecondStep2);
        buttonMap.put(3, buttonController.SecondStep3);
        buttonMap.put(4, buttonController.SecondStep4);
    }

    @Override
    public HashMap<Integer, ToggleButton> GetCurrentButtonMap() {
        return buttonMap;
    }

    @FXML
    void onCategoryClicked(ActionEvent event) {
        PopulateHazardTable(categoryMap.get(hazardCategoryRadio.getSelectedToggle()));
    }

    @Override
    public void ShowHazard(Hazard2 hazard) {
        Boolean isExpandedBefore = hazard.getIsExpanded() == 1;
        Optional<Boolean> isExpandedAfter = UIHelper.ShowHazardDescription("Hazard Description", hazard);

        if (isExpandedAfter.isPresent()) {
            int hasCHanged = Boolean.compare(isExpandedBefore, isExpandedAfter.get());

            if (hasCHanged != 0) {
                UpdateExpansionStatus(hazard, isExpandedAfter.get());
                PopulateHazardTable(categoryMap.get(hazardCategoryRadio.getSelectedToggle()));
            }
        }
    }

    private void UpdateCategoryMap() {
        categoryMap = new HashMap<>();

        categoryMap.put(initERadio, 3);
        categoryMap.put(mishapRadio, 4);
    }
}
