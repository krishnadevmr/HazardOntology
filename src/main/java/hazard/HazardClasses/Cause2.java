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
public class Cause2 extends Play{
    
    private Role role;
    private String disposition;
    private Kind environmentObject;
    private Relator relator;
    private String preInitiationEvent;
    private Integer isComplete;
    private Hazard2 hazard;

    public Cause2(Role role, String disposition, int id) {
        super(id);
        this.role = role;
        this.disposition = disposition;
    }

    public Hazard2 getHazard() {
        return hazard;
    }

    public void setHazard(Hazard2 hazard) {
        this.hazard = hazard;
    }


    
    public Cause2(int id) {
        super(id);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getDisposition() {
        return disposition;
    }

    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }

    public Kind getEnvironmentObject() {
        return environmentObject;
    }

    public void setEnvironmentObject(Kind environmentObject) {
        this.environmentObject = environmentObject;
    }

    public Relator getRelator() {
        return relator;
    }

    public void setRelator(Relator relator) {
        this.relator = relator;
    }

    public String getPreInitiationEvent() {
        return preInitiationEvent;
    }

    public void setPreInitiationEvent(String preInitiationEvent) {
        this.preInitiationEvent = preInitiationEvent;
    }

    public Integer getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Integer isComplete) {
        this.isComplete = isComplete;
    }
    
}
