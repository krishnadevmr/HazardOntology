/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Views;

import hazard.Helpers.Helper;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author kmoothandas
 */
public class CE_ICHA1Controller extends CE_Step1Controller {

    /*Radio Controllers for Hazard table*/
    @FXML
    private RadioButton hazardRadio;

    @FXML
    private ToggleGroup radioCategory;

    @FXML
    private RadioButton iCRadio;

    @FXML
    private RadioButton iERadio;

    @FXML
    private RadioButton mishapRadio;

    @FXML
    void onHazardCategory(ActionEvent event) {
        Helper.FilterHazardByCategory(hazardList, subHazardList, 1);
        hazardTable.setItems(subHazardList);
    }

    @FXML
    void onICCategory(ActionEvent event) {
        Helper.FilterHazardByCategory(hazardList, subHazardList, 2);
        hazardTable.setItems(subHazardList);
    }

    @Override
    void SetCategory() {
        onHazardCategory(null);
        hazardRadio.setSelected(true);
    }
}
