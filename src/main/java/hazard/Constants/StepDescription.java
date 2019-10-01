/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Constants;

/**
 *
 * @author kmoothandas
 */
public final class StepDescription {
    public static final String SDFSTEP1 = "Identify the kind objects explicitly presented in the system description";
    public static final String SDFSTEP2 = "Identify the role objects explicitly presented in the system description";
    public static final String SDFSTEP3 = "For each kind object obtained in Step 1, identify all the roles it can play";
    public static final String SDFSTEP4 = "For each role object obtained in Step 1 and Step 2,"
                                            + "identify the relator that connects this role, and specify all the other roles"
                                            + "connected by the identified relator, considering the system description and"
                                            + "the analysts’ expertise";
    public static final String SDFSTEP5 = "For each role object obtained in Step 1, Step 2 and"
                                            + "Step 3, identify all the kind objects that can play the role, considering"
                                            + "the system description.";
    public static final String MVISTEP1 = "Continue with brainstorming possible harms that can threaten the victims, including but not limited to, physical damages, chemical injuries, fatal illness, "
                                            + "explosion, etc.";
}
