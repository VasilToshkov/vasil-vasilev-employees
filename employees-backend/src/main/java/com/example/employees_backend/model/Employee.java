package com.example.employees_backend.model;

import java.time.LocalDate;
import java.util.Objects;

public class Employee {
//    EmpID, ProjectID, DateFrom, DateTo
    Long emplId;
    String projectId;
    LocalDate dateFrom;
    LocalDate dateTo;

    public Employee(Long emplId, String projectId, LocalDate dateFrom, LocalDate dateTo) {
        this.emplId = emplId;
        this.projectId = projectId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Long getEmplId() {
        return emplId;
    }

    public String getProjectId() {
        return projectId;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(emplId, employee.emplId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(emplId);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "emplId=" + emplId +
                ", projectId='" + projectId + '\'' +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }
}
