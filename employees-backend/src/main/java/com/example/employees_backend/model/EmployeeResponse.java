package com.example.employees_backend.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class EmployeeResponse {
   Long firstEmployee;
   Long secondEmployee;
   Long totalCommonDays;
   List<CommonProject> commonProjects;

    public EmployeeResponse(Long firstEmployee, Long secondEmployee, CommonProjectsMap commonProjectsInfo) {
        this.firstEmployee = firstEmployee;
        this.secondEmployee = secondEmployee;
        this.totalCommonDays = commonProjectsInfo.getTotalCommonDays();
        this.commonProjects= new ArrayList<>();
        for(Entry<String, Long> entry: commonProjectsInfo.getCommonProjectsAndDays().entrySet()) {
            commonProjects.add(new CommonProject(entry.getKey(), entry.getValue()));
        }
    }

    public Long getFirstEmployee() {
        return firstEmployee;
    }

    public void setFirstEmployee(Long firstEmployee) {
        this.firstEmployee = firstEmployee;
    }

    public Long getSecondEmployee() {
        return secondEmployee;
    }

    public void setSecondEmployee(Long secondEmployee) {
        this.secondEmployee = secondEmployee;
    }

    public Long getTotalCommonDays() {
        return totalCommonDays;
    }

    public void setTotalCommonDays(Long totalCommonDays) {
        this.totalCommonDays = totalCommonDays;
    }

    public List<CommonProject> getCommonProjects() {
        return commonProjects;
    }

    public void setCommonProjects(List<CommonProject> commonProjects) {
        this.commonProjects = commonProjects;
    }
}
