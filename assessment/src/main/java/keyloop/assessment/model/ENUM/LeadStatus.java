package keyloop.assessment.model.ENUM;


public enum LeadStatus {
    NEW,
    CONTACTED,
    QUALIFIED,
    WON,
    LOST;

    public static LeadStatus defaultValue() {
        return NEW;
    }
}
