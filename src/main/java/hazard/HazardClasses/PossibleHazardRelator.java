package hazard.HazardClasses;

/**
 *
 * @author kmoothandas
 */
public class PossibleHazardRelator {

    /**
     * Constructor
     * @param exposureId
     * @param exposure
     * @param hazardElementId
     * @param hazardElement 
     */
    public PossibleHazardRelator(int exposureId, String exposure, int hazardElementId, String hazardElement) {
        this.exposureId = exposureId;
        this.hazardElementId = hazardElementId;
        this.exposure = exposure;
        this.hazardElement = hazardElement;
    }

    public int getExposureId() {
        return exposureId;
    }

    public void setExposureId(int exposureId) {
        this.exposureId = exposureId;
    }

    public int getHazardElementId() {
        return hazardElementId;
    }

    public void setHazardElementId(int hazardElementId) {
        this.hazardElementId = hazardElementId;
    }

    private String exposure;
    private String hazardElement;
    private int exposureId;
    private int hazardElementId;

    public String getExposure() {
        return exposure;
    }

    public void setExposure(String exposure) {
        this.exposure = exposure;
    }

    public String getHazardElement() {
        return hazardElement;
    }

    public void setHazardElement(String hazardElement) {
        this.hazardElement = hazardElement;
    }
}
