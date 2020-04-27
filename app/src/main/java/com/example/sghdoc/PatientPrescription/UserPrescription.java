package com.example.sghdoc.PatientPrescription;

public class UserPrescription {
    private String doctor,remark,appointment,severity,date,uurl,report;

    public UserPrescription() {
    }

    public UserPrescription(String uurl) {
        this.uurl = uurl;
    }

    public String getUurl() {
        return uurl;
    }

    public void setUurl(String uurl) {
        this.uurl = uurl;
    }

    public UserPrescription(String doctor, String remark, String appointment, String severity, String date, String uurl, String report) {
        this.doctor = doctor;
        this.remark = remark;
        this.appointment = appointment;
        this.severity = severity;
        this.date = date;
        this.uurl = uurl;
        this.report=report;
    }

    public UserPrescription(String doctor, String remark, String appointment, String severity, String date) {
        this.doctor = doctor;
        this.remark = remark;
        this.appointment = appointment;
        this.severity = severity;
        this.date = date;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
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
