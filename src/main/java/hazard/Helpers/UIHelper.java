package hazard.Helpers;

import hazard.HazardClasses.Cause;
import hazard.HazardClasses.Hazard;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author kmoothandas
 */
public class UIHelper {

    public static void getAllButtonsToggle(AnchorPane pane) {
        ObservableList<Node> it2 = pane.getChildrenUnmodifiable();
        it2.forEach((node) -> {
            if (node instanceof ToggleButton) {
                setButtonToggles((ToggleButton) node);
            }
        });
    }

    private static void setButtonToggles(ToggleButton button) {
        button.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue) {
                button.setStyle(
                        "-fx-background-color: red;"
                        + "-fx-text-fill: white");
            } else {
                button.setStyle("-fx-background-color: transparent;");
            }
        }));

    }

    public static Optional<String> CreateAddDialog(String type) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add");
        dialog.setHeaderText("Enter a new " + type);
        //dialog.setContentText("Please enter the role:");
        Optional<String> result = dialog.showAndWait();
        return result;
    }

    /**
     * Method create a dialog box with 2 input fields. On Textfield and one
     * Textarea
     *
     * @param titel Titel on the dialog box
     * @param textFieldPromptText Placeholder text for the textfield. Also text
     * in the tooltip
     * @param descriptionAreaPromptText Placeholder text for textarea. Also text
     * in the tooltip
     * @param descriptionFormat
     * @return Text in the inputfields. First text in textfield. Second is text
     * in textarea
     */
    public static Optional<List<String>> CreateAddHazardDialog(String titel, String textFieldPromptText, String descriptionAreaPromptText, String descriptionFormat) {
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle(titel);
        String strDescription = String.format(descriptionFormat, textFieldPromptText);

        dialog.setHeaderText(strDescription);

        // Create buttontypes        
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));

        TextField txtField1 = new TextField();
        if (textFieldPromptText != null && textFieldPromptText.trim().length() > 0) {
            txtField1.setPromptText(textFieldPromptText);
            txtField1.setTooltip(new Tooltip(textFieldPromptText));
        }

        // Event called when text in textfield is changed. Updates the header text for the dialgo.
        // Uppdates the descriptionFormat with text at %s
        txtField1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String strNewDescription = String.format(descriptionFormat, newValue);
                dialog.setHeaderText(strNewDescription);
            }
        });

        grid.add(txtField1, 0, 0);

        TextArea txtAreaDescription = new TextArea();
        if (descriptionAreaPromptText != null && descriptionAreaPromptText.trim().length() > 0) {
            txtAreaDescription.setPromptText(descriptionAreaPromptText);
            txtAreaDescription.setTooltip(new Tooltip(descriptionAreaPromptText));
        }

        grid.add(txtAreaDescription, 0, 1);

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> txtField1.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                List<String> lsResult = new ArrayList<String>(2);
                lsResult.add(txtField1.getText());
                lsResult.add(txtAreaDescription.getText());

                return lsResult;
            }

            return null;
        });

        Optional<List<String>> result = dialog.showAndWait();
        return result;
    }

    /**
     * Method create a dialog box with 2 input fields. On textfield and one
     * textarea
     *
     * @param titel Titel for the dialog box
     * @param textFieldPromptText Tooltip text for textfield
     * @param descriptionAreaPromptText Tooltip text for textarea
     * @param hazard Hazard
     * @param hazardDescription Hazard description
     * @param hazardId Hazard id
     * @return Text in the inputfields. 1 text in textfield. 2 text in textarea,
     * 3 text is the id for the hazard in the database
     */
    public static Optional<List<String>> CreateUpdateHazardDialog(String titel,
            String textFieldPromptText, String descriptionAreaPromptText,
            String hazard, String hazardDescription, int hazardId) {

        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle(titel);

        dialog.setHeaderText(hazard);

        // Create buttontypes        
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));

        TextField txtField1 = new TextField();
        txtField1.setText(hazard);

        if (textFieldPromptText != null && textFieldPromptText.trim().length() > 0) {
            txtField1.setTooltip(new Tooltip(textFieldPromptText));
        }

        grid.add(txtField1, 0, 0);

        TextArea txtAreaDescription = new TextArea();
        txtAreaDescription.setText(hazardDescription);

        if (descriptionAreaPromptText != null && descriptionAreaPromptText.trim().length() > 0) {
            txtAreaDescription.setTooltip(new Tooltip(descriptionAreaPromptText));
        }

        grid.add(txtAreaDescription, 0, 1);

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> txtField1.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                List<String> lsResult = new ArrayList<String>(3);
                lsResult.add(txtField1.getText());
                lsResult.add(txtAreaDescription.getText());
                lsResult.add("" + hazardId);
                return lsResult;
            }

            return null;
        });

        Optional<List<String>> result = dialog.showAndWait();
        return result;
    }

    /**
     * Method create a add dialog for cause
     *
     * @param title Title of the dialog box
     * @param hazard Hazard
     * @param descriptionAreaPromptText Tooltip text for textarea
     * @return List of 2 texts. 1 text is the cause of a hazard. 2 is the hazard
     * id
     */
    public static Optional<List<String>> CreateAddCauseDialog(String title, Hazard hazard, String descriptionAreaPromptText) {
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle(title);

        dialog.setHeaderText("Enter a new cause to the Hazard:" + System.lineSeparator() + "" + hazard.getHazard() + System.lineSeparator() + "" + hazard.getHazardDescription());

        // Create buttontypes        
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));

        TextArea txtAreaDescription = new TextArea();
        if (descriptionAreaPromptText != null && descriptionAreaPromptText.trim().length() > 0) {
            txtAreaDescription.setPromptText(descriptionAreaPromptText);
            txtAreaDescription.setTooltip(new Tooltip(descriptionAreaPromptText));
        }

        grid.add(txtAreaDescription, 0, 0);

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> txtAreaDescription.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                List<String> lsResult = new ArrayList<String>(2);
                lsResult.add(txtAreaDescription.getText());
                lsResult.add("" + hazard.getId());
                return lsResult;
            }

            return null;
        });

        Optional<List<String>> result = dialog.showAndWait();
        return result;
    }

    /**
     * Method create a update dialog for cause
     *
     * @param title Title on the dialog
     * @param cause Cuase that we want to update
     * @param descriptionAreaPromptText Tooltip text for textarea
     * @param hazardId Hazard id
     * @return List of 3 texts. 1 is the updated cause, 2 is cause id, 3 is
     * hazard id
     */
    public static Optional<List<String>> CreateUpdateCauseDialog(String title, Cause cause, String descriptionAreaPromptText, int hazardId) {
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle(title);

        // Create buttontypes        
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));

        TextArea txtAreaDescription = new TextArea();

        if (cause != null) {
            txtAreaDescription.setText(cause.getCause());
        }

        if (descriptionAreaPromptText != null && descriptionAreaPromptText.trim().length() > 0) {
            txtAreaDescription.setTooltip(new Tooltip(descriptionAreaPromptText));
        }

        grid.add(txtAreaDescription, 0, 0);

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> txtAreaDescription.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                List<String> lsResult = new ArrayList<String>(3);
                lsResult.add(txtAreaDescription.getText());
                lsResult.add("" + cause.getId());
                lsResult.add("" + hazardId);
                return lsResult;
            }

            return null;
        });

        Optional<List<String>> result = dialog.showAndWait();
        return result;
    }

    public static Optional<List<String>> CreateAddSeverityAndProbabilityDialog(String title, String headerText, int hazardId) {
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);

        // Create buttontypes        
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));

        ObservableList<String> options1 = FXCollections.observableArrayList("High-75%", "Medium-50%", "Low-25%");
        final ComboBox<String> comboBox1 = new ComboBox<String>(options1);
        Text text1 = new Text("Severity");

        ObservableList<String> options2 = FXCollections.observableArrayList("High-75%", "Medium-50%", "Low-25%");
        final ComboBox<String> comboBox2 = new ComboBox<String>(options2);
        Text text2 = new Text("Probability");

        Text text3 = new Text("Risk Evaluation:");

        GridPane gridCombos = new GridPane();
        gridCombos.add(text1, 0, 0);
        gridCombos.add(comboBox1, 0, 1);

        gridCombos.add(text2, 1, 0);
        gridCombos.add(comboBox2, 1, 1);

        gridCombos.add(text3, 2, 0);

        grid.add(gridCombos, 0, 0);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                List<String> lsResult = new ArrayList<String>(3);
                lsResult.add("TEXT TEST");
                return lsResult;
            }

            return null;
        });

        Optional<List<String>> result = dialog.showAndWait();
        return result;
    }

    public static Optional<String> CreateAddVictimDialog(String title, String type) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle(title);
        dialog.setHeaderText("Enter a new " + type);
        //dialog.setContentText("Please enter the role:");
        Optional<String> result = dialog.showAndWait();
        return result;
    }
    
        public static Optional<String> UpdateVictimDialog(String title, String type, String oldHarmValue) {
        TextInputDialog dialog = new TextInputDialog(oldHarmValue);
        dialog.setTitle(title);
        dialog.setHeaderText("Update " + type);
        Optional<String> result = dialog.showAndWait();
        return result;
    }

    /**
     * Method return value from string input
     *
     * @param str Input string. Can be High-75%, Medium-50%, Low-25%
     * @return If input string High-75% = 0.75, If input string Medium-50% =
     * 0.50, If input string Low-25% = 0.25 else returns 0
     */
    private Double getRiskValueFromString(String str) {
        Double returnValue = 0D;

        if (str != null) {
            switch (str.toLowerCase()) {
                case "high-75%":
                    returnValue = 0.75D;
                    break;
                case "medium-50%":
                    returnValue = 0.50D;
                    break;
                case "low-25%":
                    returnValue = 0.25D;
                    break;
            }
        }

        return returnValue;
    }
}
