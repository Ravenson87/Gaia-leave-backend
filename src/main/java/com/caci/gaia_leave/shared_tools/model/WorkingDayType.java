package com.caci.gaia_leave.shared_tools.model;

public enum WorkingDayType {
    WEEKEND("0"),
    NATIONAL_HOLIDAY("1"),
    WORKING_DAY("2");

    public final String type;
    WorkingDayType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
}
