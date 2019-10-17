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
    public static final String MVISTEP = "Continue with brainstorming possible harms that can threaten the victims, including"
            + "but not limited to, physical damages, chemical injuries, fatal illness, "
            + "explosion, etc.";

    public static final String HPSTEP = "For each mishap victim identified, a list of possible exposures and hazard elements are available. "
            + "Explore possible dispositions for each victim and add them as hazards.";

    public static final String CHDSTEP = "For each hazard, categorize into 'Hazard', 'Initiating Condition', 'Initiaiting Event' or 'Mishap'";

    public static final String DEICHASTEP1 = "TO-DO";
    
    public static final String CEICHASTEP1 = "TO-DO";

    public static final String HAZARD_CLASSIFICATION_HELP_Q1_HEADING = "Q1: Is the hazard description describing a situation (state of affairs) or an event? \n";
    public static final String HAZARD_CLASSIFICATION_HELP_Q2_HEADING = "\nQ2: If the hazard description is describing a situation, can the situation trigger mishaps when "
            + "some dispositions in the situation are manifested? \n";
    public static final String HAZARD_CLASSIFICATION_HELP_Q3_HEADING = "\nQ3: If the hazard description is describing an event, can the event bring about severe injuries of"
            + "people or damages to the environment? \n";
    public static final String HAZARD_CLASSIFICATION_HELP_Q1_BODY = "Q1 shall be asked to determine if the hazard description is describing a hazard/initiating condition (if the answer is situation)\n"
            + "or mishap/initiating event (if the answer is event),\n"
            + "according to the HO. Note that\n"
            + "1) if a hazard description describes that some event is supposed to occur but does not, then the hazard description is regarded as a generic situation that will\n"
            + "not trigger the specific event, such as “the brake command is not issued”, and\n"
            + "2) if a hazard description describes a repetitive and continuous behavior, it can be regarded as a situation, such as “a train is running on the track”. \n";
    public static final String HAZARD_CLASSIFICATION_HELP_Q2_BODY = "Q2 shall be asked to determine if the hazard description is describing a hazard (if the answer is yes)\n"
            + "or an initiating condition (if the answer is no), according to the HO. \n";
    public static final String HAZARD_CLASSIFICATION_HELP_Q3_BODY = "Q3 shall be asked to determine if the hazard description is describing a mishap (if the answer is yes)\n"
            + "or an initiating event (if the answer is no), according to the HO. \n";
}
