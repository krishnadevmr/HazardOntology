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
public class SystemDescriptionFormalizationController implements Initializable, NavigationController {

    public SystemDescriptionFormalizationController() {
    }

    public SystemDescriptionFormalizationController(MainPageController mainController) {
        this.mainController = mainController;
    }

    private MainPageController mainController;

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

    @FXML
    void onStep1(ActionEvent event) {
        mainController.LoadPaneFromController("/fxml/mainviews/SDF1.fxml", mainController.centerPane);
    }

    @FXML
    void onStep2(ActionEvent event) {
        mainController.LoadPaneFromController("/fxml/mainviews/SDF2.fxml", mainController.centerPane);
    }

    @FXML
    void onStep3(ActionEvent event) {
        mainController.LoadPaneFromController("/fxml/mainviews/SDF3.fxml", mainController.centerPane);
    }

    @FXML
    void onStep4(ActionEvent event) {
        mainController.LoadPaneFromController("/fxml/mainviews/SDF4.fxml", mainController.centerPane);
    }

    @FXML
    void onStep5(ActionEvent event) {
        mainController.LoadPaneFromController("/fxml/mainviews/SDF5.fxml", mainController.centerPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UIHelper.getAllButtonsToggle(pane);
    }

    //@Override
    public AnchorPane LoadPane() {
        AnchorPane pane = new AnchorPane();
        try {
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SecondaryNavigationOHI1.fxml"));
            pane = FXMLLoader.load(getClass().getResource("/fxml/SecondaryNavigationOHI1.fxml"));
            //SecondaryNavigationController sc = new SystemDescriptionFormalizationController(mainController);
            //loader.setController(sc);
            //pane = loader.load();
        } catch (IOException ex) {
        }

        return pane;
    }

    //@Override
    public AnchorPane LoadPane(MainPageController controller, NavigationController secondaryController) {
        AnchorPane pane = new AnchorPane();
        //SecondaryNavigationController secondaryController = new SystemDescriptionFormalizationController(controller);
        try {
            pane = FXMLLoader.load(getClass().getResource("/fxml/SecondaryNavigationOHI1.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SecondaryNavigationOHI1.fxml"));
            loader.setController(secondaryController);
            pane = loader.load();
        } catch (IOException ex) {
        }

        return pane;
    }

}
