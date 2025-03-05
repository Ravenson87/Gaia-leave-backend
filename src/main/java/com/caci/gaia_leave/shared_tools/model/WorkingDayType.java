package com.caci.gaia_leave.shared_tools.model;

public enum WorkingDayType {
    WEEKEND("weekend"),
    NATIONAL_HOLIDAY("national_holiday"),
    WORKING_DAY("working_day");

    public final String type;
    WorkingDayType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
}
