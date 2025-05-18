package com.example.employees_backend.model;

import java.util.Map;

public class CommonProjectsMap {
    Long totalCommonDays;
    Map<String, Long> commonProjectsAndDays;

    public CommonProjectsMap(Long totalCommonDays, Map<String, Long> commonProjectsAndDays) {
        this.totalCommonDays = totalCommonDays;
        this.commonProjectsAndDays = commonProjectsAndDays;
    }

    public Long getTotalCommonDays() {
        return totalCommonDays;
    }

    public void setTotalCommonDays(Long totalCommonDays) {
        this.totalCommonDays = totalCommonDays;
    }

    public Map<String, Long> getCommonProjectsAndDays() {
        return commonProjectsAndDays;
    }

    public void setCommonProjectsAndDays(Map<String, Long> commonProjectsAndDays) {
        this.commonProjectsAndDays = commonProjectsAndDays;
    }
}
