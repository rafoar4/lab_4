package com.example.lab_4;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Job implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("jobTitle")
    @Expose
    private String jobTitle;
    @SerializedName("minSalary")
    @Expose
    private Integer minSalary;
    @SerializedName("maxSalary")
    @Expose
    private Integer maxSalary;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Integer getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Integer minSalary) {
        this.minSalary = minSalary;
    }

    public Integer getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(Integer maxSalary) {
        this.maxSalary = maxSalary;
    }
}
