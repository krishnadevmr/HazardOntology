/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Views;

import hazard.Helpers.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author kmoothandas
 */
public class CE_IEMS1Controller extends CE_Step1Controller {

    /*Radio Controllers for Hazard table*/
    @FXML
    private RadioButton iERadio;

    @FXML
    private ToggleGroup radioCategory;

    @FXML
    private RadioButton mishapRadio;

    @FXML
    void onIECategory(ActionEvent event) {
        Helper.FilterHazardByCategory(hazardList, subHazardList, 3);
        hazardTable.setItems(subHazardList);
    }

    @FXML
    void onMishapCategory(ActionEvent event) {
        Helper.FilterHazardByCategory(hazardList, subHazardList, 4);
        hazardTable.setItems(subHazardList);
    }

    @Override
    void SetCategory() {
        onIECategory(null);
        iERadio.setSelected(true);
    }

}
