package com.cgasystems.BCP.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.TemporalUnit;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void getExpectedCompletionTimeDurationDaysFractionUpper() {
        Duration expectedDuration = Duration.ofDays(2);

        Task testTask = new Task();
        testTask.setExpectedCompletionTime("2.5D");

        Duration actualDuration = testTask.getExpectedCompletionTimeDuration();

        Assertions.assertEquals(expectedDuration.toMillis(),actualDuration.toMillis());
    }

    @Test
    void getExpectedCompletionTimeDurationDaysUpper() {
        Duration expectedDuration = Duration.ofDays(2);

        Task testTask = new Task();
        testTask.setExpectedCompletionTime("2D");

        Duration actualDuration = testTask.getExpectedCompletionTimeDuration();

        Assertions.assertEquals(expectedDuration.toMillis(),actualDuration.toMillis());
    }

    @Test
    void getExpectedCompletionTimeDurationDaysLower() {
        Duration expectedDuration = Duration.ofDays(2);

        Task testTask = new Task();
        testTask.setExpectedCompletionTime("2d");

        Duration actualDuration = testTask.getExpectedCompletionTimeDuration();

        Assertions.assertEquals(expectedDuration.toMillis(),actualDuration.toMillis());
    }

    @Test
    void getExpectedCompletionTimeDurationHoursUpper() {
        Duration expectedDuration = Duration.ofHours(2);

        Task testTask = new Task();
        testTask.setExpectedCompletionTime("2H");

        Duration actualDuration = testTask.getExpectedCompletionTimeDuration();

        Assertions.assertEquals(expectedDuration.toMillis(),actualDuration.toMillis());
    }

    @Test
    void getExpectedCompletionTimeDurationHoursLower() {
        Duration expectedDuration = Duration.ofHours(2);

        Task testTask = new Task();
        testTask.setExpectedCompletionTime("2h");

        Duration actualDuration = testTask.getExpectedCompletionTimeDuration();

        Assertions.assertEquals(expectedDuration.toMillis(),actualDuration.toMillis());
    }


    @Test
    void getExpectedCompletionTimeDurationMinutesUpper() {
        Duration expectedDuration = Duration.ofMinutes(90);

        Task testTask = new Task();
        testTask.setExpectedCompletionTime("90M");

        Duration actualDuration = testTask.getExpectedCompletionTimeDuration();

        Assertions.assertEquals(expectedDuration.toMillis(),actualDuration.toMillis());
    }

    @Test
    void getExpectedCompletionTimeDurationMinutesLower() {
        Duration expectedDuration = Duration.ofMinutes(90);

        Task testTask = new Task();
        testTask.setExpectedCompletionTime("90m");

        Duration actualDuration = testTask.getExpectedCompletionTimeDuration();

        Assertions.assertEquals(expectedDuration.toMillis(),actualDuration.toMillis());
    }


}