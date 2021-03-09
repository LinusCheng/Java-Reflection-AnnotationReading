package practice.constant;

public enum PropellerType {

    FIXED_PITCH("Fixed Pitch"),
    CONSTANT_SPEED("Constant Speed"),
    GROUND_ADJUSTABLE("Ground-Adjustable");

    private final String propeller;

    private PropellerType(String propeller) {
        this.propeller = propeller;
    }

    public String toString() {
        return this.propeller;
    }

}
