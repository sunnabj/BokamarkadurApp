package com.example.bokamarkadur.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.security.auth.Subject;

public class SubjectsResponse {

    @SerializedName("errors")
    private String errors;
    @SerializedName("subjects")
    private List<String> subjects;
    @SerializedName("msg")
    private String msg;
    @SerializedName("ok")
    private Boolean ok;

    public SubjectsResponse(String errors, List<String> subjects, String msg, Boolean ok) {

        this.errors = errors;
        this.subjects = subjects;
        this.msg = msg;
        this.ok = ok;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public List<String> getAvailableSubjects() { return subjects; }
}