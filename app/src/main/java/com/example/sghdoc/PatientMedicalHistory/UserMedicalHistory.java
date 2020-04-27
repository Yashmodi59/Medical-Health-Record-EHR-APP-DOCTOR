package com.example.sghdoc.PatientMedicalHistory;

public class UserMedicalHistory {
    private String history,haveAny,docname,stillhave,isuploaded,type,severity,date;


    public UserMedicalHistory() {
    }

    public UserMedicalHistory(String history, String haveAny, String docname, String stillhave, String isuploaded, String type, String severity, String date) {
        this.history = history;
        this.haveAny = haveAny;
        this.docname = docname;
        this.stillhave = stillhave;
        this.isuploaded = isuploaded;
        this.type = type;
        this.severity = severity;
        this.date = date;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getHaveAny() {
        return haveAny;
    }

    public void setHaveAny(String haveAny) {
        this.haveAny = haveAny;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public String getStillhave() {
        return stillhave;
    }

    public void setStillhave(String stillhave) {
        this.stillhave = stillhave;
    }

    public String getIsiploaded() {
        return isuploaded;
    }

    public void setIsiploaded(String isiploaded) {
        this.isuploaded = isiploaded;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
