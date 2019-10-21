/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Views;

import hazard.Controllers.InitializableWithLoad;
import hazard.Controllers.Navigation.HazardDescriptionExpansionController;
import hazard.Controllers.Subviews.DE_ICHA3Controller;
import hazard.Controllers.Subviews.DE_Step1;
import hazard.Controllers.Subviews.SingleHazardExpansionController;
import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Hazard2;
import hazard.HazardClasses.HazardExpansion;
import hazard.HazardClasses.Kind;
import hazard.HazardClasses.Role;
import hazard.Helpers.UIHelper;
import hazard.Services.DatabaseManager;
import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author kmoothandas
 */
public class DE_ICHA1Controller extends DE_Step1 {

    //public HazardDescriptionExpansionController buttonController;
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
        System.out.println("hazard.Controllers.Views.DE_ICHA1Controller.initialize()" + hazardCategoryRadio.getSelectedToggle().toString());
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
