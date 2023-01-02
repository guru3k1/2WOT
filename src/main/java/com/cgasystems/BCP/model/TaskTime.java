package com.cgasystems.BCP.model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TaskTime extends BaseModel {
    public TaskTime() {
        this.loggedTime = new HashSet<>();
        this.dailyTimeMap = new HashMap<>();
        this.durationMap = new HashMap<>();
    }

    private Set<TimeLog> loggedTime;
    private Map<LocalDate,Set<TimeLog>> dailyTimeMap;

    private Map<LocalDate,Duration> durationMap;


    public Map<LocalDate, Duration> getDurationMap() {
        return durationMap;
    }

    public void setDurationMap(Map<LocalDate, Duration> durationMap) {
        this.durationMap = durationMap;
    }

    public Set<TimeLog> getLoggedTime() {
        return loggedTime;
    }

    public void setLoggedTime(Set<TimeLog> loggedTime) {
        this.loggedTime = loggedTime;
    }

    public Map<LocalDate, Set<TimeLog>> getDailyTimeMap() {
        return dailyTimeMap;
    }

    public void setDailyTimeMap(Map<LocalDate, Set<TimeLog>> dailyTimeMap) {
        this.dailyTimeMap = dailyTimeMap;
    }
}
