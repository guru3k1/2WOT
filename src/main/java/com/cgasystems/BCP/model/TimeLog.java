package com.cgasystems.BCP.model;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

public class TimeLog {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long timeId;

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getTimeId() {
        return timeId;
    }

    public void setTimeId(Long timeId) {
        this.timeId = timeId;
    }

    public Duration getDuration(){
        if(startTime!=null && endTime!=null){
            return Duration.between(startTime,endTime);
        }
        return Duration.ofMinutes(0);
    }

    @Override
    public String toString() {
        return "TimeLog{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", timeId=" + timeId +
                '}';
    }
}
