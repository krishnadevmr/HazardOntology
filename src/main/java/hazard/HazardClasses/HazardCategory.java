/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.HazardClasses;

/**
 *
 * @author kmoothandas
 */
public class HazardCategory {
    private int categoryid;
    private String category;

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public HazardCategory(int categoryid, String category) {
        this.categoryid = categoryid;
        this.category = category;
    }
}
