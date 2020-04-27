package com.example.sghdoc.PatientDocument;

public class UserMedicalDocument {
    public String doctor;
    public String remark;
    public String labname;
    public String type;


    public String date1;

    public UserMedicalDocument() {
    }

    public UserMedicalDocument(String uurl) {
        this.uurl = uurl;
    }

    public String uurl;
    public UserMedicalDocument(String doctor, String remark, String labname, String type, String date1, String uurl) {
        this.doctor = doctor;
        this.remark = remark;
        this.labname = labname;
        this.type = type;
        this.date1 = date1;
        this.uurl = uurl;
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

    public String getLabname() {
        return labname;
    }

    public void setLabname(String labname) {
        this.labname = labname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getUurl() {
        return uurl;
    }

    public void setUurl(String uurl) {
        this.uurl = uurl;
    }
}
