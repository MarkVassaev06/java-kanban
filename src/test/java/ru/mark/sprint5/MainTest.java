package ru.mark.sprint5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.mark.sprint5.manager.HistoryManager;
import ru.mark.sprint5.manager.Managers;
import ru.mark.sprint5.manager.TaskManager;
import ru.mark.sprint5.models.Epic;
import ru.mark.sprint5.models.Status;
import ru.mark.sprint5.models.Subtask;
import ru.mark.sprint5.models.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    private static TaskManager taskManager;
    private static HistoryManager historyManager;
    private static Task task1;
    private static Task task2;

    private static Epic epic1;
    private static Subtask subtask11;
    private static Subtask subtask12;
    private static Epic epic2;
    private static Subtask subtask21;

    @BeforeAll
    static void fillTasks() {
        taskManager = Managers.getDefault();
        Assertions.assertNotNull(taskManager);

        historyManager = taskManager.getHistoryManager();
        Assertions.assertNotNull(historyManager);

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

        printAllTasks();
    }

    @Test
    @DisplayName("Старые тесты")
    void test1() {

        List<Task> tasks = taskManager.getTasks();
        Assertions.assertNotNull(tasks);
        assertEquals(2, tasks.size());

        task1.setStatus(Status.DONE);
        task2.setStatus(Status.IN_PROGRESS);

        subtask12.setStatus(Status.DONE);
        subtask11.setStatus(Status.DONE);
        assertEquals(Status.DONE, epic1.getStatus());

        subtask21.setStatus(Status.IN_PROGRESS);
        assertEquals(Status.IN_PROGRESS, epic2.getStatus());

        taskManager.removeSubtaskById(subtask11.getId());
        List<Subtask> subtasks = taskManager.getAllSubtask();
        assertEquals(2, subtasks.size());
    }

    @Test
    @DisplayName("Проверки равенства")
    void equalsTest() {
        assertNotEquals(task1, task2);
        assertNotEquals(epic1, epic2);
        assertNotEquals(subtask11, subtask12);
        assertNotEquals(subtask11, subtask21);

        Task t1 = new Task(1, "t1", "d1");
        Task t2 = new Task(1, "t2", "d2");
        assertEquals(t1, t2);

        Epic e1 = new Epic(2, "e1", "d1");
        Epic e2 = new Epic(2, "e2", "d2");
        assertEquals(e1, e2);

        Subtask s1 = new Subtask(1, "s1", "d1", 1);
        Subtask s2 = new Subtask(1, "s2", "d2", 2);
        assertEquals(s1, s2);
    }

    @Test
    @DisplayName("Epic нельзя добавить в самого себя. Subtask нельзя сделать своим же эпиком")
    void epicTest() {
        //В эпик добавить эпик невозможно, поскольку у меня используется жесткая типизация.
        //Subtask нельзя сделать своим же эпиком по той же причине.
    }

    @Test
    void managersTest() {

        taskManager.addTask(task1);
        Task taskById = taskManager.getTaskById(task1.getId());
        assertEquals(task1, taskById);
        //Посмотрим, что с историей.
        printHistory();

        assertEquals(task1.getName(), taskById.getName());
        assertEquals(task1.getDescription(), taskById.getDescription());
        assertEquals(task1.getStatus(), taskById.getStatus());

        historyManager.add(task1);
        Task task = new Task(task1.getId(), task1.getName() + "new", task1.getDescription() + "new");
        historyManager.add(task);
        List<Task> history = historyManager.getHistory();
        //одна задача попала в историю при вызове getTaskById, еще 2 - простым добавлением.
        assertEquals(3, history.size());
        assertEquals(history.get(0), history.get(1));
        assertTrue(history.get(2).getName().endsWith("new"));
        printHistory();
    }

    /**
     * 1. Создайте две задачи, эпик с тремя подзадачами и эпик без подзадач.
     * 2. Запросите созданные задачи несколько раз в разном порядке.
     * 3. После каждого запроса выведите историю и убедитесь, что в ней нет повторов.
     * 4. Удалите задачу, которая есть в истории, и проверьте, что при печати она не будет выводиться.
     * 5. Удалите эпик с тремя подзадачами и убедитесь, что из истории удалился как сам эпик, так и все его подзадачи.
     */
    @Test
    void additionalTest() {

    }

    private static void printAllTasks() {
        System.out.println("Задачи:");
        for (Task task : taskManager.getTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : taskManager.getEpics()) {
            System.out.println(epic);
            for (Task task : taskManager.getSubtasksByEpicId(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : taskManager.getAllSubtask()) {
            System.out.println(subtask);
        }
    }

    private static void printHistory() {
        System.out.println("История:");
        for (Task task : historyManager.getHistory()) {
            System.out.println(task);
        }
    }

}
