
package hazard.HazardClasses;

/**
 *
 * @author kmoothandas
 */
public class MishapVictim2 extends Play {

    private String role;

    public String getRole() {
        return role;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getHarm() {
        return harm;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setHarm(String harm) {
        this.harm = harm;
    }
    private int roleId;
    private String harm;

    public MishapVictim2(int id, String role, int roleId, String harm) {
        super(id);
        this.role = role;
        this.roleId = roleId;
        this.harm = harm;
    }

}
