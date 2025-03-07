package com.caci.gaia_leave.shared_tools.model;

public enum FreeDayType {
    VACATION_LEAVE("VACATION_LEAVE"),
    SICK_LEAVE("SICK_LEAVE"),
    TIME_OFF_IN_LIEU("TOIL");

    public final String type;
    FreeDayType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

}
