/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.HazardClasses;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author kmoothandas
 */
public class Hazard2 extends Play {
    
    public Hazard2(int id) {
        super(id);
    }
    
    private String mishapVictim;
    private int victimId;
    private String exposure;
    private int exposureId;
    private String hazardElement;
    private int hazardElementId;
    private String truthmaker;
    private String hazardDescription;
    private String victimKind;
    private String hazardKind;
    private int categoryId;
    private String category;
    private int isExpanded;

    public int getIsExpanded() {
        return isExpanded;
    }

    public void setIsExpanded(int isExpanded) {
        this.isExpanded = isExpanded;
    }


    public Hazard2( int id, String hazardDescription, String category) 
    {
        super(id);
        this.hazardDescription = hazardDescription;
        this.category = category;
    }

    public String getVictimKind() {
        return victimKind;
    }

    public void setVictimKind(String victimKind) {
        this.victimKind = victimKind;
    }

    public String getHazardKind() {
        return hazardKind;
    }

    public void setHazardKind(String hazardKind) {
        this.hazardKind = hazardKind;
    }

    public Hazard2(String mishapVictim, int victimId, String exposure, int exposureId, String hazardElement, int hazardElementId, String truthmaker, String hazardDescription, String victimKind, String hazardKind, int categoryid, String category, int isExpanded, int id) {
        super(id);
        this.mishapVictim = mishapVictim;
        this.victimId = victimId;
        this.exposure = exposure;
        this.exposureId = exposureId;
        this.hazardElement = hazardElement;
        this.hazardElementId = hazardElementId;
        this.truthmaker = truthmaker;
        this.hazardDescription = hazardDescription;
        this.victimKind = victimKind;
        this.hazardKind = hazardKind;
        this.categoryId = categoryid;
        this.category = category;
        this.isExpanded = isExpanded;
    }
    

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
        
    public Hazard2(String mishapVictim, int victimId, String exposure, int exposureId, String hazardElement, int hazardElementId, String truthmaker, String hazardDescription, int id) {
        super(id);
        this.mishapVictim = mishapVictim;
        this.victimId = victimId;
        this.exposure = exposure;
        this.exposureId = exposureId;
        this.hazardElement = hazardElement;
        this.hazardElementId = hazardElementId;
        this.truthmaker = truthmaker;
        this.hazardDescription = hazardDescription;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getMishapVictim() {
        return mishapVictim;
    }

    public void setMishapVictim(String mishapVictim) {
        this.mishapVictim = mishapVictim;
    }

    public int getVictimId() {
        return victimId;
    }

    public void setVictimId(int victimId) {
        this.victimId = victimId;
    }

    public String getExposure() {
        return exposure;
    }

    public void setExposure(String exposure) {
        this.exposure = exposure;
    }

    public int getExposureId() {
        return exposureId;
    }

    public void setExposureId(int exposureId) {
        this.exposureId = exposureId;
    }

    public String getHazardElement() {
        return hazardElement;
    }

    public void setHazardElement(String hazardElement) {
        this.hazardElement = hazardElement;
    }

    public int getHazardElementId() {
        return hazardElementId;
    }

    public void setHazardElementId(int hazardElementId) {
        this.hazardElementId = hazardElementId;
    }

    public String getTruthmaker() {
        return truthmaker;
    }

    public void setTruthmaker(String truthmaker) {
        this.truthmaker = truthmaker;
    }

    public String getHazardDescription() {
        return hazardDescription;
    }

    public void setHazardDescription(String hazardDescription) {
        this.hazardDescription = hazardDescription;
    }
    
}
