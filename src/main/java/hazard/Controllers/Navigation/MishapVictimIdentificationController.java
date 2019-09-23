/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Navigation;

import hazard.Controllers.MainPageController;
import hazard.Controllers.NavigationController;
import hazard.Helpers.UIHelper;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author kmoothandas
 */
public class MishapVictimIdentificationController implements Initializable, NavigationController {

    public MishapVictimIdentificationController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    private MainPageController mainPageController;

    @FXML
    private AnchorPane pane;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private ToggleButton step1;

    @FXML
    private ToggleButton step2;

    @FXML
    void onMishapVictimIdentification1(ActionEvent event) {
        mainPageController.LoadPaneFromController("/fxml/mainviews/MVI1.fxml", mainPageController.centerPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UIHelper.getAllButtonsToggle(pane);
    }

    //@Override
    public AnchorPane LoadPane() {
        AnchorPane pane = new AnchorPane();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SecondaryNavigationOHI2.fxml"));
            pane = loader.load();
        } catch (IOException ex) {
        }
        return pane;
    }

    //@Override
    public AnchorPane LoadPane(MainPageController controller, NavigationController secondaryController) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
