package com.cgasystems.BCP.model;

import java.time.Duration;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Task extends BaseModel{
    private long taskId;
    private UUID userId;
    private String name;
    private String details;
    private boolean isCompleted;
    private String state;
    private String currentSprint;
    private String expectedCompletionTime;
    private Set<TimeLog> loggedTime;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCurrentSprint() {
        return currentSprint;
    }

    public void setCurrentSprint(String currentSprint) {
        this.currentSprint = currentSprint;
    }

    public String getExpectedCompletionTime() {
        return expectedCompletionTime;
    }

    public void setExpectedCompletionTime(String expectedCompletionTime) {
        this.expectedCompletionTime = expectedCompletionTime;
    }

    public Duration getExpectedCompletionTimeDuration() {
        if(getExpectedCompletionTime() != null){
            String value = getExpectedCompletionTime().substring(0,getExpectedCompletionTime().length()-1);
            if(getExpectedCompletionTime().toLowerCase().endsWith("d")){
                return Duration.ofDays(Double.valueOf(value).intValue());
            }else if(getExpectedCompletionTime().toLowerCase().endsWith("h")){
                return Duration.ofHours(Double.valueOf(value).intValue());
            }else if(getExpectedCompletionTime().toLowerCase().endsWith("m")){
                return Duration.ofMinutes(Double.valueOf(value).intValue());
            }
        }
        return null;
    }



    public Set<TimeLog> getLoggedTime() {
        return loggedTime;
    }

    public void setLoggedTime(Set<TimeLog> loggedTime) {
        this.loggedTime = loggedTime;
    }

    public Duration getLoggedDuration(){

        if(getLoggedTime()!=null){
            long taskDuration = getLoggedTime().stream().mapToLong(timeLog -> timeLog.getDuration().toMillis()).sum();
            return Duration.ofMillis(taskDuration);
        }
        return null;
    }

    public long getCompletedPercentage(){
        Duration expected = getExpectedCompletionTimeDuration(); //400
        Duration actual = getLoggedDuration(); //100
        if(expected != null && actual != null){
            long remaining = expected.toMillis()-actual.toMillis(); // 300
            Double percent = ((double)remaining/expected.toMillis())*100;
            return -percent.longValue()+100;
        }
        return 0L;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskId == task.taskId && userId == task.userId && isCompleted == task.isCompleted && Objects.equals(name, task.name) && Objects.equals(details, task.details) && Objects.equals(state, task.state) && Objects.equals(currentSprint, task.currentSprint) && Objects.equals(expectedCompletionTime, task.expectedCompletionTime);
    }

    public boolean equalsUpdate(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskId == task.taskId && userId == task.userId && name.equals(task.name) && details.equals(task.details) && currentSprint.equals(task.currentSprint) && expectedCompletionTime.equals(task.expectedCompletionTime) && type.equals(task.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, userId, name, details, isCompleted, state, currentSprint, expectedCompletionTime);
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", isCompleted=" + isCompleted +
                ", state='" + state + '\'' +
                ", currentSprint='" + currentSprint + '\'' +
                ", expectedCompletionTime='" + expectedCompletionTime + '\'' +
                ", loggedTime=" + loggedTime +
                ", type='" + type + '\'' +
                '}';
    }
}
