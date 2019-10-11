/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Views;

import hazard.Controllers.Navigation.HazardDescriptionExpansionController;
import hazard.Controllers.Subviews.DE_ICHA3Controller;
import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Hazard2;
import hazard.HazardClasses.HazardExpansion;
import hazard.HazardClasses.Kind;
import hazard.HazardClasses.Role;
import hazard.Helpers.UIHelper;
import hazard.Services.DatabaseManager;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kmoothandas
 */
public class DE_ICHA1Controller implements Initializable {

    public HazardDescriptionExpansionController buttonController;

    public DE_ICHA1Controller(HazardDescriptionExpansionController buttonController) {
        this.buttonController = buttonController;
    }

    public DE_ICHA1Controller() {
    }

    /*Hazard Table*/
    @FXML
    private TableView<Hazard2> hazardTable;

    @FXML
    private TableColumn<Hazard2, String> hazardId;

    @FXML
    private TableColumn<Hazard2, String> mishapVictim;

    @FXML
    private TableColumn<Hazard2, String> exposure;

    @FXML
    private TableColumn<Hazard2, String> hazardElement;

    @FXML
    private TableColumn<Hazard2, String> truthmaker;

    @FXML
    private TableColumn<Hazard2, String> hazardDescription;

    @FXML
    private TableColumn<Hazard2, String> isExpanded;

    private ObservableList<Hazard2> hazardList;

    /*Victim Kind Table*/
    @FXML
    private Label kindTableLabel;

    @FXML
    private TableView<Kind> victimKindTable;

    @FXML
    private TableColumn<Kind, String> victimKinds;

    private ObservableList<Kind> victimKindList;

    /*Hazard Kind Table*/
    @FXML
    private TableView<Kind> hazardKindTable;

    @FXML
    private TableColumn<Kind, String> hazardKinds;

    private ObservableList<Kind> hazardKindList;

    /* Role Table*/
    @FXML
    private TableView<Role> roleTable;

    @FXML
    private TableColumn<Role, String> roles;

    private ObservableList<Role> roleList;

    /*Current selection property*/
    private Kind currentKind;

    /*Radio Buttons*/
    @FXML
    private RadioButton hazardRadio;

    @FXML
    private RadioButton initCRadio;

    @FXML
    private ToggleGroup hazardCategoryRadio;

    HashMap<RadioButton, Integer> categoryMap;

    /*Track Button*/
    public int currentStep;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SetCellfactories();
        hazardCategoryRadio.selectToggle(hazardRadio);
        categoryMap = new HashMap<>();
        categoryMap.put(hazardRadio, 1);
        categoryMap.put(initCRadio, 2);
        PopulateHazardTable(categoryMap.get(hazardCategoryRadio.getSelectedToggle()));
        currentStep = 1;
    }

    @FXML
    void onCategoryClicked(ActionEvent event) {
        PopulateHazardTable(categoryMap.get(hazardCategoryRadio.getSelectedToggle()));
    }

    @FXML
    void onHazardClicked(MouseEvent event) {
        int index = hazardTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            Hazard2 hazard = hazardTable.getItems().get(index);
            PopulateKindTable(hazard);
            if (event.getClickCount() == 2) {
                ShowHazard(hazard);
            }
        }
    }

    @FXML
    void onHazardKindSelected(MouseEvent event) {
        int index = hazardKindTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            Kind kind = hazardKindTable.getItems().get(index);
            currentKind = kind;
            PopulateRoleTable(kind);
            HighlightStep(2, buttonController.step2);
        }
    }

    @FXML
    void onVictimKindSelected(MouseEvent event) {
        int index = victimKindTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            Kind kind = victimKindTable.getItems().get(index);
            currentKind = kind;
            PopulateRoleTable(kind);
            HighlightStep(2, buttonController.step2);
        }
    }

    @FXML
    void onIdentifyRelators(ActionEvent event) {

        int index = roleTable.getSelectionModel().selectedIndexProperty().get();
        if (index != -1) {
            HazardExpansion hazardExpansion = new HazardExpansion();
            hazardExpansion.setRootRole(roleTable.getItems().get(index));
            hazardExpansion.setRootKind(currentKind);
            hazardExpansion.setHazardId(hazardTable.getItems().get(hazardTable.getSelectionModel().selectedIndexProperty().get()).getId());

            DE_ICHA3Controller controller = new DE_ICHA3Controller(hazardExpansion, this);
            LoadController(controller, "/fxml/subviews/DE_ICHA3.fxml");

            HighlightStep(3, buttonController.step3);

        }

    }

    private void PopulateHazardTable(int category) {
        hazardList = FXCollections.observableArrayList();
        DatabaseManager.GetHazardByCategory(category, hazardList);
        //DataBaseConnection.selectAll("hazard2", hazardList);
        hazardTable.setItems(hazardList);
    }

    private void SetCellfactories() {
        this.hazardId.setCellValueFactory((TableColumn.CellDataFeatures<Hazard2, String> cellData) -> Bindings.concat("HD", cellData.getValue().getId()));
        this.mishapVictim.setCellValueFactory(cellData -> Bindings.concat(
                cellData.getValue().getMishapVictim(), System.lineSeparator(),
                "(", cellData.getValue().getVictimKind(), ")"));

        this.exposure.setCellValueFactory(new PropertyValueFactory<>("exposure"));
        this.hazardElement.setCellValueFactory(cellData -> Bindings.concat(
                cellData.getValue().getHazardElement(), System.lineSeparator(),
                "(", cellData.getValue().getHazardKind(), ")"));
        this.truthmaker.setCellValueFactory(new PropertyValueFactory<>("truthmaker"));
        this.hazardDescription.setCellValueFactory(new PropertyValueFactory<>("hazardDescription"));

        this.victimKinds.setCellValueFactory(new PropertyValueFactory<>("kind"));
        this.hazardKinds.setCellValueFactory(new PropertyValueFactory<>("kind"));

        this.roles.setCellValueFactory(new PropertyValueFactory<>("role"));

        this.isExpanded.setCellValueFactory((TableColumn.CellDataFeatures<Hazard2, String> cellData) -> Bindings.concat(cellData.getValue().getIsExpanded() == 1 ? "Yes" : "No"));
        //this.isExpanded.setCellValueFactory((TableColumn.CellDataFeatures<Hazard2, String> cellData) -> Bindings.concat(cellData.getValue().getIsExpanded()));

    }

    private void PopulateKindTable(Hazard2 hazard) {
        //int hazardTableIndex = hazardTable.getSelectionModel().selectedIndexProperty().get();

        //Hazard2 hazard = hazardTable.getItems().get(hazardTableIndex);
        victimKindList = FXCollections.observableArrayList();
        ObservableList<ObservableList<Kind>> hazardKindsList = FXCollections.observableArrayList();
        hazardKindsList = DatabaseManager.GetHazardKinds(hazard, hazardKindsList);

        ObservableList<Kind> victimsKinds = hazardKindsList.get(0);
        ObservableList<Kind> hazardsKinds = hazardKindsList.get(1);

        victimKindTable.setItems(victimsKinds);
        hazardKindTable.setItems(hazardsKinds);

        kindTableLabel.setText("All kinds for Hazard HD" + hazard.getId());

    }

    private void PopulateRoleTable(Kind kind) {
        roleList = FXCollections.observableArrayList();
        String sql = "select * from roletoplay where kindid = " + kind.getId();
        DataBaseConnection.sql(sql, "roletoplay", roleList);
        roleTable.setItems(roleList);
    }

    private void LoadController(Initializable controller, String url) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            loader.setController(controller);
            AnchorPane pane = loader.load();

            Stage stage = new Stage();
            //stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Identify Relators");
            stage.setScene(new Scene(pane));
            stage.show();
            stage.setOnCloseRequest(() -> {HighlightStep(3, buttonController.step3);
            });
        } catch (IOException | NullPointerException ex) {
            System.err.println(ex);
        }
    }

    private void ShowHazard(Hazard2 hazard) {
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

    private void UpdateExpansionStatus(Hazard2 hazard, Boolean isExpanded) {
        int expanded;
        if (isExpanded) {
            expanded = 1;
        } else {
            expanded = 0;
        }

        String sql = MessageFormat.format("UPDATE hazard2 SET isExpanded ={0} WHERE id={1}", expanded, hazard.getId());
        DataBaseConnection.sqlUpdate(sql);
    }

    public void HighlightStep(Integer stepNumber, ToggleButton button) {
        if (currentStep != stepNumber) {
            button.fire();
            currentStep = stepNumber;
        }
    }

}
