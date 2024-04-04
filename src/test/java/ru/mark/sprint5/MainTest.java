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

import java.util.*;

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
        assertEquals(3, subtasks.size());
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
        printHistory();
    }

    /**
     * Текст из задания:
     * Если у вас <strong>останется время, вы можете</strong> выполнить дополнительное задание. Реализуйте в классе Main опциональный пользовательский сценарий.
     * 1. Создайте две задачи, эпик с тремя подзадачами и эпик без подзадач.
     * 2. Запросите созданные задачи несколько раз в разном порядке.
     * 3. После каждого запроса выведите историю и убедитесь, что в ней нет повторов.
     * 4. Удалите задачу, которая есть в истории, и проверьте, что при печати она не будет выводиться.
     * 5. Удалите эпик с тремя подзадачами и убедитесь, что из истории удалился как сам эпик, так и все его подзадачи.
     */
    @Test
    @DisplayName("Тест из задания")
    void additionalTest() {
        taskManager = Managers.getDefault();
        historyManager = taskManager.getHistoryManager();

        //1. Создайте две задачи, эпик с тремя подзадачами и эпик без подзадач.
        //Создайте две задачи,
        Task task1 = new Task(taskManager.nextTaskId(), "task1", "description1");
        taskManager.addTask(task1);
        Task task2 = new Task(taskManager.nextTaskId(), "task2", "description2");
        taskManager.addTask(task2);

        //эпик с тремя подзадачами
        Epic epic1 = new Epic(taskManager.nextTaskId(), "epic1", "epicDescription1");
        taskManager.addEpic(epic1);
        Subtask subtask11 = new Subtask(taskManager.nextTaskId(), "subtask11", "subtaskDescription11", epic1.getId());
        taskManager.addSubtask(subtask11);
        Subtask subtask12 = new Subtask(taskManager.nextTaskId(), "subtask12", "subtaskDescription12", epic1.getId());
        taskManager.addSubtask(subtask12);
        Subtask subtask13 = new Subtask(taskManager.nextTaskId(), "subtask13", "subtaskDescription13", epic1.getId());
        taskManager.addSubtask(subtask13);

        //эпик без подзадач.
        Epic epic2 = new Epic(taskManager.nextTaskId(), "epic2", "epicDescription2");
        taskManager.addEpic(epic2);

        //2. Запросите созданные задачи несколько раз в разном порядке.
        List<Task> tasks = new ArrayList<>(taskManager.getTasks());
        tasks.addAll(taskManager.getAllSubtask());
        tasks.addAll(taskManager.getEpics());
        Collections.shuffle(tasks);
        for (Task task : tasks) {
            if (task instanceof Epic) {
                taskManager.getEpicById(task.getId());
            } else if (task instanceof Subtask) {
                taskManager.getSubtaskById(task.getId());
            } else {
                taskManager.getTaskById(task.getId());
            }
            //3. После каждого запроса выведите историю и убедитесь, что в ней нет повторов.
            List<Task> history = historyManager.getHistory();
            Set<Task> taskSet = new HashSet<>(history);
            //размеры совпадают, значит дубликатов нет
            assertEquals(history.size(), taskSet.size());
        }

        //4. Удалите задачу, которая есть в истории, и проверьте, что при печати она не будет выводиться.
        List<Task> history = historyManager.getHistory();
        //берем случайную задачу из истории
        Task task = history.get(0);
        historyManager.remove(task.getId());
        List<Task> newHistory = historyManager.getHistory();
        assertFalse(newHistory.contains(task));

        //5. Удалите эпик с тремя подзадачами и убедитесь, что из истории удалился как сам эпик, так и все его подзадачи.
        historyManager.remove(epic1.getId());
        history = historyManager.getHistory();
        assertFalse(history.contains(epic1));
        assertFalse(history.contains(subtask11));
        assertFalse(history.contains(subtask12));
        assertFalse(history.contains(subtask13));
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
