package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TaskMapperTest {
    TaskMapper taskMapper = new TaskMapper();

    @Test
    public void mapToTaskTest(){

        //given
        TaskDto taskDto1 = new TaskDto(1L, "Title1", "Content1");

        //when
        Task task1 = taskMapper.mapToTask(taskDto1);

        //then
        assertNotNull(task1);
        assertEquals(1L,task1.getId().longValue());
    }

    @Test
    public void mapToTaskDtoTest() {
        //given
        Task task1 = new Task(1L, "Title1", "Content1");

        //when
        TaskDto taskDto1 = taskMapper.mapToTaskDto(task1);

        //then
        assertNotNull(taskDto1);
        assertEquals(1L,taskDto1.getId().longValue());
    }

    @Test
    public void mapToTaskList() {
        //given
        Task task1 = new Task(1L, "Title1", "Content1");
        Task task2 = new Task(2L, "Title2", "Content2");

        List<Task> taskList = new ArrayList<>();

        taskList.add(task1);
        taskList.add(task2);

        //when
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);

        //then
        assertNotNull(taskDtoList);
        assertEquals(2,taskDtoList.size());
    }
}
