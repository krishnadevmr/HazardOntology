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
public class DE_ICHA1Controller extends DE_Step1 {

    public DE_ICHA1Controller(HazardDescriptionExpansionController buttonController) {
        this.buttonController = buttonController;
    }

    public DE_ICHA1Controller() {
    }

    /*Radio Buttons*/
    @FXML
    private RadioButton hazardRadio;

    @FXML
    private RadioButton initCRadio;

    @FXML
    private ToggleGroup hazardCategoryRadio;

    public HashMap<Integer, ToggleButton> buttonMap;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SetCellfactories();
        hazardCategoryRadio.selectToggle(hazardRadio);
        UpdateCategoryMap();
        PopulateHazardTable(categoryMap.get(hazardCategoryRadio.getSelectedToggle()));
        currentStep = 1;
        buttonMap = new HashMap<>();
        buttonMap.put(1, buttonController.step1);
        buttonMap.put(2, buttonController.step2);
        buttonMap.put(3, buttonController.step3);
        buttonMap.put(4, buttonController.step4);
    }

    @FXML
    void onCategoryClicked(ActionEvent event) {
        PopulateHazardTable(categoryMap.get(hazardCategoryRadio.getSelectedToggle()));
    }

    @Override
    public HashMap<Integer, ToggleButton> GetCurrentButtonMap() {
        return buttonMap;
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
        categoryMap.put(hazardRadio, 1);
        categoryMap.put(initCRadio, 2);
    }

}
