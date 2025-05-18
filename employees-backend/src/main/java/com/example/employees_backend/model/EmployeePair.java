package com.example.employees_backend.model;

import java.util.Objects;

public class EmployeePair {
    private Long firstEmployeeId;
    private Long secondEmployeeId;

    public EmployeePair(Long firstEmployeeId, Long secondEmployeeId) {
        this.firstEmployeeId = firstEmployeeId;
        this.secondEmployeeId = secondEmployeeId;
    }

    public Long getFirstEmployeeId() {
        return firstEmployeeId;
    }

    public void setFirstEmployeeId(Long firstEmployeeId) {
        this.firstEmployeeId = firstEmployeeId;
    }

    public Long getSecondEmployeeId() {
        return secondEmployeeId;
    }

    public void setSecondEmployeeId(Long secondEmployeeId) {
        this.secondEmployeeId = secondEmployeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EmployeePair that = (EmployeePair) o;
        return Objects.equals(firstEmployeeId, that.firstEmployeeId) && Objects.equals(secondEmployeeId, that.secondEmployeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstEmployeeId, secondEmployeeId);
    }
}
