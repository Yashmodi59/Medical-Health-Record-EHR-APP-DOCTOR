package com.example.sghdoc.PatientProfile;

public class LastLog {

    String lastLog,email;
    public LastLog(String lastLog, String email) {
        this.lastLog = lastLog;
        this.email = email;
    }

    public String getLastLog() {
        return lastLog;
    }

    public void setLastLog(String lastLog) {
        this.lastLog = lastLog;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
