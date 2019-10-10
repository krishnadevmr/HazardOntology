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
public class HazardExpansion extends Play{

    private Kind rootKind;
    private Role rootRole;
    private Relator relator;
    private Role linkedRole;
    private Kind linkedKind;
    private Integer hazardId;

    public Kind getRootKind() {
        return rootKind;
    }

    public void setRootKind(Kind rootKind) {
        this.rootKind = rootKind;
    }

    public Role getRootRole() {
        return rootRole;
    }

    public void setRootRole(Role rootRole) {
        this.rootRole = rootRole;
    }

    public Relator getRelator() {
        return relator;
    }

    public void setRelator(Relator relator) {
        this.relator = relator;
    }

    public Role getLinkedRole() {
        return linkedRole;
    }

    public void setLinkedRole(Role linedRole) {
        this.linkedRole = linedRole;
    }

    public Kind getLinkedKind() {
        return linkedKind;
    }

    public void setLinkedKind(Kind linkedKind) {
        this.linkedKind = linkedKind;
    }

    public HazardExpansion() {
        super(-1);
    }

    public HazardExpansion(Kind rootKind, Role rootRole, Relator relator, Role linedRole, Kind linkedKind, Integer hazardId, int id) {
        super(id);
        this.rootKind = rootKind;
        this.rootRole = rootRole;
        this.relator = relator;
        this.linkedRole = linedRole;
        this.linkedKind = linkedKind;
        this.hazardId = hazardId;
    }

    public Integer getHazardId() {
        return hazardId;
    }

    public void setHazardId(Integer hazardId) {
        this.hazardId = hazardId;
    }

    public HazardExpansion(HazardExpansion source) {
        super(-1);
        this.rootKind = source.rootKind;
        this.rootRole = source.rootRole;
        this.relator = source.relator;
        this.linkedRole = source.linkedRole;
        this.linkedKind = source.linkedKind;
    }

}
