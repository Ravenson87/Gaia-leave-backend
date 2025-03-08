package com.caci.gaia_leave.shared_tools.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum WorkingDayType {
    WEEKEND("WEEKEND"),
    NATIONAL_HOLIDAY("NATIONAL_HOLIDAY"),
    RELIGIOUS_HOLIDAY("RELIGIOUS_HOLIDAY"),
    WORKING_DAY("WORKING_DAY");

    public final String value;

    WorkingDayType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

//    @JsonCreator
//    public static WorkingDayType fromString(String value) {
//        for (WorkingDayType type : WorkingDayType.values()) {
//            if (type.value.equalsIgnoreCase(value)) {
//                return type;
//            }
//        }
//        throw new IllegalArgumentException("Unknown enum value: " + value);
//    }
}
