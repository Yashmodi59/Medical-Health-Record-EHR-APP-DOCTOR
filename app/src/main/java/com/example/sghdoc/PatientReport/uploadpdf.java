package com.example.sghdoc.PatientReport;

public class uploadpdf {
    public String url;
    public String text;

    public uploadpdf() {
    }

    public uploadpdf(String url, String text) {
        this.url = url;
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
