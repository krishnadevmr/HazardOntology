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

    public static final String DEICHASTEP1 = "Step 1: Identifies al the constituent parts of the CHD (No action necessary) \n\n"
            + "Step 2: For each kind object, all the roles are identified (No action necesssary) \n\n"
            + "Step 3: For each role object identify the relators that are dependant on selected role. "
            + "The application then lists the roles that are linked to selected relator. Choose the appropriate/relevant roles.\n\n"
            + "Step 4 : When user clicks on Add Expansion, the system identifies all the kinds that can play the role and adds it to a hazard expansion";

    public static final String CESTEP1 = "For each role explore the possible dispositions athat chareacterize this role.";
    public static final String CEICHASTEP2 = "For each role, explore and identify the corresponding kind object that can play this role. "
            + "The pre-initiating event that makes the kind object play the role is a candidate for the cause";
    public static final String CEICHASTEP3 = "UNCLEAR";
    public static final String CEICHASTEP4 = "For each relator or exposure, the pre initiating event that hampers or breaks the relator"
            + " will be a candidate for a cause";
    public static final String CEIEMSSTEP2 = "For each role, explore and identify the corresponding kind object that can play this role. "
            + "When such kind objects are chareacterized by the corresponding initiating factors/truthmakers, CHD will be triggered";

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
