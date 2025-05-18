package com.example.employees_backend.model;

public class CommonProject {
    String projectId;
    Long commonDays;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Long getCommonDays() {
        return commonDays;
    }

    public void setCommonDays(Long commonDays) {
        this.commonDays = commonDays;
    }

    public CommonProject(String projectId, Long commonDays) {
        this.projectId = projectId;
        this.commonDays = commonDays;
    }
}
