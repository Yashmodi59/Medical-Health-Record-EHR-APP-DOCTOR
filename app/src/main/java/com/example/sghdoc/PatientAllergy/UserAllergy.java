package com.example.sghdoc.PatientAllergy;

public class UserAllergy {
    private String allergy,stillhave,type,severity,date;

    public UserAllergy() {

    }

    public UserAllergy(String allergy, String stillhave, String type, String severity, String date) {
        this.allergy = allergy;
        this.stillhave = stillhave;
        this.date=date;
        this.type = type;
        this.severity = severity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getStillhave() {
        return stillhave;
    }

    public void setStillhave(String stillhave) {
        this.stillhave = stillhave;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}
