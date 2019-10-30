/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Navigation;

import hazard.Controllers.MainPageController;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;

/**
 *
 * @author kmoothandas
 */
public class NavigationInterface implements Initializable {

    public Integer currentStep;
    public Integer totalSteps;

    public Integer getTotalSteps() {
        return stepMap.size();
    }
    public Map<Integer, ToggleButton> stepMap;

    public void setTotalSteps(Integer totalSteps) {
        this.totalSteps = totalSteps;
    }

    public void setStepMap(Map<Integer, ToggleButton> stepMap) {
        this.stepMap = stepMap;
    }

    public void SetCurrentStep(Integer stepNumber) {
        this.currentStep = stepNumber;
    }

    public Boolean NextStep() {
        if (currentStep < totalSteps) {
            stepMap.get(currentStep + 1).fire();
            return true;
        } else {
            return false;
        }
    }

    public Boolean PreviousStep() {
        if (currentStep <= 1) {
            return false;
        } else {
            //currentStep -= 1;
            stepMap.get(currentStep - 1).fire();
            return true;
        }
    }

    public void SetInitialStep(Boolean isBackwardsNavigation) {
        if (isBackwardsNavigation) {
            SetCurrentStep(totalSteps);
            stepMap.get(totalSteps).fire();
            //step5.fire();
        } else {
            SetCurrentStep(1);
            stepMap.get(1).fire();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void LoadMainView(MainPageController mainPageController, String url,
                            String phase, String step, String stepDescription, Integer stepNumber) {
        mainPageController.LoadPaneFromController(url, mainPageController.centerPane,
                phase, step, stepDescription);
        SetCurrentStep(1);
    }
}
