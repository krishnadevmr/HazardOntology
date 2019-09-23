/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers;

import hazard.Controllers.Navigation.MishapVictimIdentificationController;
import hazard.Controllers.Navigation.HazardPopulationController;
import hazard.Controllers.Navigation.SystemDescriptionFormalizationController;
import hazard.Constants.StringConstants;
import hazard.Controllers.Navigation.CausesExplorationController;
import hazard.Controllers.Navigation.HazardDescriptionCharacterizationController;
import hazard.Controllers.Navigation.HazardDescriptionExpansionController;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author kmoothandas
 */
public class MainPageController implements Initializable {

    @FXML
    private VBox navBox;

    @FXML
    void onOhi1(ActionEvent event) {

        SystemDescriptionFormalizationController controller = new SystemDescriptionFormalizationController(this);
        LoadPaneFromController("/fxml/navigation/SystemDescriptionFormalization.fxml", controller, secondPane);
    }

    @FXML
    void onOhi2(ActionEvent event) {
        MishapVictimIdentificationController controller = new MishapVictimIdentificationController(this);
        LoadPaneFromController("/fxml/navigation/MishapVictimIdentification.fxml", controller, secondPane);
    }

    @FXML
    void onOhi3(ActionEvent event) {
        HazardPopulationController controller = new HazardPopulationController(this);
        LoadPaneFromController("/fxml/navigation/HazardPopulation.fxml", controller, secondPane);
    }

    @FXML
    void onOch1(ActionEvent event) {
        HazardDescriptionCharacterizationController controller = new HazardDescriptionCharacterizationController(this);
        LoadPaneFromController("/fxml/navigation/HazardDescriptionCharacterization.fxml", controller, secondPane);
    }

    @FXML
    void onOch2(ActionEvent event) {
        HazardDescriptionExpansionController controller = new HazardDescriptionExpansionController(this);
        LoadPaneFromController("/fxml/navigation/HazardDescriptionExpansion.fxml", controller, secondPane);
    }

    @FXML
    void onOch3(ActionEvent event) {
        CausesExplorationController controller = new CausesExplorationController(this);
        LoadPaneFromController("/fxml/navigation/CausesExploration.fxml", controller, secondPane);
    }

    @FXML
    void onSare1(ActionEvent event) {

    }

    @FXML
    void onSare2(ActionEvent event) {

    }

    @FXML
    void onSare3(ActionEvent event) {

    }

    @FXML
    void loadSession(ActionEvent event) {
        navBox.setVisible(true);
        descriptionBox.setVisible(true);
    }

    @FXML
    void newSession(ActionEvent event) {

    }

    @FXML
    private AnchorPane firstPane;

    @FXML
    private ToggleButton ohiButton1;

    @FXML
    private ToggleButton ohiButton2;

    @FXML
    private ToggleButton ohiButton3;

    @FXML
    private ToggleButton ochButton1;

    @FXML
    private ToggleButton ochButton2;

    @FXML
    private ToggleButton ochButton3;

    @FXML
    private ToggleButton sareButton1;

    @FXML
    private ToggleButton sareButton2;

    @FXML
    private ToggleButton sareButton3;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private AnchorPane secondPane;

    @FXML
    public AnchorPane centerPane;

    @FXML
    private AnchorPane descriptionBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setButtonTexts();
        UIHelper.getAllButtonsToggle(firstPane);
        //descriptionBox.setVisible(false);
        //navBox.setVisible(false);
        //LoadCenterIntoMain(new SDF1Controller());
    }

    private void setButtonTexts() {
        ohiButton1.setText(StringConstants.MAIN_HEADING_STEP_OHI_1);
        ohiButton2.setText(StringConstants.MAIN_HEADING_STEP_OHI_2);
        ohiButton3.setText(StringConstants.MAIN_HEADING_STEP_OHI_3);
        ochButton1.setText(StringConstants.MAIN_HEADING_STEP_OCH_1);
        ochButton2.setText(StringConstants.MAIN_HEADING_STEP_OCH_2);
        ochButton3.setText(StringConstants.MAIN_HEADING_STEP_OCH_3);
        sareButton1.setText(StringConstants.MAIN_HEADING_SARE_ACT_1);
        sareButton2.setText(StringConstants.MAIN_HEADING_SARE_ACT_2);
        sareButton3.setText(StringConstants.MAIN_HEADING_SARE_ACT_3);
    }

    //Not used
    /*private AnchorPane LoadPaneIntoMain(NavigationController controller) {
        AnchorPane pane = controller.LoadPane(this, controller);
        secondPane.getChildren().clear();
        secondPane.getChildren().add(pane);
        UIHelper.getAllButtonsToggle(secondPane);
        return pane;
    }*/
    //Not used
    /*public void LoadCenterIntoMain(NavigationController controller) {
        AnchorPane pane = controller.LoadPane();;
        centerPane.getChildren().clear();
        centerPane.getChildren().add(pane);
    }*/
    public void LoadPaneFromController(String url, Initializable controller, AnchorPane parent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            loader.setController(controller);
            parent.getChildren().clear();
            parent.getChildren().add(loader.load());
            //UIHelper.getAllButtonsToggle(parent);
        } catch (IOException | NullPointerException ex) {
            System.err.println(ex);
        }
    }



    public void LoadPaneFromController(String url, AnchorPane parent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            AnchorPane pane = loader.load();
            //pane.set
            parent.getChildren().clear();
            parent.getChildren().add(pane);
            AnchorPane.setRightAnchor(pane, 5.0);
            AnchorPane.setLeftAnchor(pane, 5.0);
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 15.0);
        } catch (IOException | NullPointerException ex) {
            System.err.println(ex);
        }
    }
    
}
