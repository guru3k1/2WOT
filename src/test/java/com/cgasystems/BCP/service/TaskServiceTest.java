package com.cgasystems.BCP.service;

import com.cgasystems.BCP.dao.TimeUserDao;
import com.cgasystems.BCP.model.Task;
import com.cgasystems.BCP.model.TaskTime;
import com.cgasystems.BCP.model.TimeLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;

class TaskServiceTest {

    Task mockedTask;
    @Spy
    @InjectMocks
    TaskService taskService;

    @Mock
    TimeUserDao timeUserDao;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        mockedTask = new Task();
        mockedTask.setTaskId(1);
    }

    @Test
    void getTaskTime() {
        when(timeUserDao.getTimeLogsByTaskId(mockedTask.getTaskId())).thenReturn(getMockedTimeLogSet());
        TaskTime actualTaskTime = taskService.getTaskTime(mockedTask);
        System.out.println(actualTaskTime);
    }

    Set<TimeLog> getMockedTimeLogSet(){
        Set<TimeLog> timeLogSet = new HashSet<>();
        TimeLog firstTimeLog = new TimeLog();
        firstTimeLog.setStartTime(LocalDateTime.of(2022,12,10,10,50,15));
        firstTimeLog.setEndTime(LocalDateTime.of(2022,12,10,11,50,15));
        firstTimeLog.setTimeId(1L);
        TimeLog secondTimeLog = new TimeLog();
        secondTimeLog.setStartTime(LocalDateTime.of(2022,12,11,10,35,15));
        secondTimeLog.setEndTime(LocalDateTime.of(2022,12,11,12,35,15));
        secondTimeLog.setTimeId(2L);
        TimeLog thirdTimeLog = new TimeLog();
        thirdTimeLog.setStartTime(LocalDateTime.of(2022,12,10,15,10,5));
        thirdTimeLog.setEndTime(LocalDateTime.of(2022,12,10,16,40,5));
        thirdTimeLog.setTimeId(3L);
        TimeLog fourthTimeLog = new TimeLog();
        fourthTimeLog.setStartTime(LocalDateTime.of(2022,12,11,16,00,15));
        fourthTimeLog.setEndTime(LocalDateTime.of(2022,12,11,17,00,15));
        fourthTimeLog.setTimeId(4L);
        timeLogSet.add(firstTimeLog);
        timeLogSet.add(secondTimeLog);
        timeLogSet.add(thirdTimeLog);
        timeLogSet.add(fourthTimeLog);
        return timeLogSet;
    }
}