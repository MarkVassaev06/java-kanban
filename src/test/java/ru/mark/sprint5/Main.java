package ru.mark.sprint5;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.mark.sprint5.manager.InMemoryTaskManager;
import ru.mark.sprint5.manager.TaskManager;
import ru.mark.sprint5.models.Epic;
import ru.mark.sprint5.models.Status;
import ru.mark.sprint5.models.Subtask;
import ru.mark.sprint5.models.Task;

import java.util.Collection;

public class Main {

    private static TaskManager taskManager;
    private static Task task1;
    private static Task task2;

    private static Epic epic1;
    private static Subtask subtask11;
    private static Subtask subtask12;
    private static Epic epic2;
    private static Subtask subtask21;

    @BeforeAll
    static void fillTasks() {
        taskManager = new InMemoryTaskManager();
        //Создайте две задачи,
        task1 = new Task(taskManager.nextTaskId(), "task1", "description1");
        taskManager.addTask(task1);
        task2 = new Task(taskManager.nextTaskId(), "task2", "description2");
        taskManager.addTask(task2);

        //а также эпик с двумя подзадачами и эпик с одной подзадачей.
        epic1 = new Epic(taskManager.nextTaskId(), "epic1", "epicDescription1");
        taskManager.addEpic(epic1);
        subtask11 = new Subtask(taskManager.nextTaskId(), "subtask11", "subtaskDescription11", epic1.getId());
        taskManager.addSubtask(subtask11);
        subtask12 = new Subtask(taskManager.nextTaskId(), "subtask12", "subtaskDescription12", epic1.getId());
        taskManager.addSubtask(subtask12);

        epic2 = new Epic(taskManager.nextTaskId(), "epic2", "epicDescription2");
        taskManager.addEpic(epic2);
        subtask21 = new Subtask(taskManager.nextTaskId(), "subtask21", "subtaskDescription21", epic2.getId());
        taskManager.addSubtask(subtask21);
    }

    @Test
    void test1() {
        print("Только что созданные", taskManager);

        task1.setStatus(Status.DONE);
        task2.setStatus(Status.IN_PROGRESS);

        subtask12.setStatus(Status.DONE);
        subtask11.setStatus(Status.DONE);
        subtask21.setStatus(Status.IN_PROGRESS);

        print("Сменили статусы", taskManager);

        taskManager.removeSubtaskById(subtask11.getId());
        print("Удалили подзадачу", taskManager);

    }

    private static void print(String title, TaskManager taskManager) {
        System.out.println(">>> " + title + " <<<");

        System.out.println("> tasks \n");
        printCollection(taskManager.getTasks());

        System.out.println("> epics \n");
        printCollection(taskManager.getEpics());

        System.out.println("> subtasks \n");
        printCollection(taskManager.getAllSubtask());
    }

    private static void printCollection(Collection<? extends Task> tasks) {
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

}
