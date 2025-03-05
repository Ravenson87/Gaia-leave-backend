package com.caci.gaia_leave.shared_tools.model;

public enum FreeDayType {
    VACATION_LEAVE("vacation_leave"),
    SICK_LEAVE("sick_leave"),
    TIME_OFF_IN_LIEU("time_off_in_lieu");

    public final String type;
    FreeDayType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

}
