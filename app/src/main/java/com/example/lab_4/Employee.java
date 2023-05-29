package com.example.lab_4;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

public class Employee implements Serializable {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("hireDate")
    @Expose
    private String hireDate;
    @SerializedName("job")
    @Expose
    private Job job;
    @SerializedName("salary")
    @Expose
    private Double salary;
    @SerializedName("commissionPct")
    @Expose
    private Object commissionPct;
    @SerializedName("managerId")
    @Expose
    private Integer managerId;
    @SerializedName("departmentId")
    @Expose
    private Integer departmentId;
    @SerializedName("meeting")
    @Expose
    private Integer meeting;
    @SerializedName("meetingDate")
    @Expose
    private Object meetingDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Object getCommissionPct() {
        return commissionPct;
    }

    public void setCommissionPct(Object commissionPct) {
        this.commissionPct = commissionPct;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getMeeting() {
        return meeting;
    }

    public void setMeeting(Integer meeting) {
        this.meeting = meeting;
    }

    public Object getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Object meetingDate) {
        this.meetingDate = meetingDate;
    }


}
