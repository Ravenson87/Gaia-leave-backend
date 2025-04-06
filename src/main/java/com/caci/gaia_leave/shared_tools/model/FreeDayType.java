package com.caci.gaia_leave.shared_tools.model;

//TODO Proveriti da li ovo koristimo, i ako ne koristimo, izbrisati
public enum FreeDayType {
    VACATION_LEAVE("VACATION_LEAVE"),
    SICK_LEAVE("SICK_LEAVE"),
    TOIL("TOIL");

    public final String type;

    FreeDayType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
