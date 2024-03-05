package ru.mark.sprint4.manager;

import ru.mark.sprint4.models.Epic;
import ru.mark.sprint4.models.Subtask;
import ru.mark.sprint4.models.Task;

import java.util.*;

/**
 * Генератор id подзадач и эпик.
 */
public class TaskManager {
    private static int taskId = 1;

    private Map<Integer, Task> tasks;
    private Map<Integer, Epic> epics;

    public TaskManager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
    }

    public int nextTaskId() {
        return taskId++;
    }

    /**
     * Первый метод получения списка всех задач.
     */
    public List<Task> getTasks() {
        return Collections.unmodifiableList(new ArrayList<>(tasks.values()));
    }

    /**
     * Второй метод получения списка всех эпик.
     */
    public List<Epic> getEpics() {
        return Collections.unmodifiableList(new ArrayList<>(epics.values()));
    }

    /**
     * Третий метод получения всех подзадач.
     */
    public List<Subtask> getAllSubtask() {
        List<Subtask> result = new ArrayList<>();
        for (Epic epic : epics.values()) {
            result.addAll(epic.getAllSubtasks());
        }
        return result;
    }

    /**
     * Получение всех подзадач эпики.
     *
     * @param epicId id эпики
     */
    public List<Subtask> getSubtasksByEpicId(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            return epic.getAllSubtasks();
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * 1. Первый метод удаления: удаление всех задач.
     */
    public void removeAllTasks() {
        tasks.clear();
    }

    /**
     * 2. Второй метод удаления: удаление всех эпик.
     * Поскольку эпик содержит все свои позадачи, то удаление эпик удаляет все ссылки на его подзадачи.
     * Ровно также, как удаление объекта удаляет все ссылки на поля.
     * Итак: при удалении всех эпиков удаляются все подзадачи и, очевидно, их идентификаторы.
     */
    public void removeAllEpics() {
        epics.clear();
    }

    /**
     * 3. Третий метод: удаление всех подзадач. Очевидно, что подзадачи <b>всех</b> эпик.
     */
    public void removeAllSubtasks() {
        for (Epic epic : epics.values()) {
            //здесь же происходит смена статуса эпики на NEW.
            epic.clearSubtasks();
        }
    }

    /**
     * Удаление всех подзадач эпики.
     */
    public void removeAllSubtasksFromEpic(int epicId) {
        Epic epic = getEpicById(epicId);
        if (epic != null) {
            epic.clearSubtasks();
        }
    }

    /**
     * Получить задачу по идентификатору.
     *
     * @param taskId id задачи
     */
    public Task getTaskById(int taskId) {
        return tasks.get(taskId);
    }

    /**
     * Получить эпик по идентификатору.
     *
     * @param epicId id эпики
     */
    public Epic getEpicById(int epicId) {
        return epics.get(epicId);
    }

    /**
     * Запрос подзадачи по идентификтатору.
     *
     * @param subtaskId id подзадачи
     */
    public Subtask getSubtaskById(int subtaskId) {
        for (Epic epic : epics.values()) {
            Subtask subtask = epic.getSubtasks().get(subtaskId);
            if (subtask != null) {
                return subtask;
            }
        }
        return null;
    }

    /**
     * Добавление задачи.
     *
     * @param task
     */
    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    /**
     * Обновление задачи.
     *
     * @param task
     */
    public void updateTask(Task task) {
        //Структуры хранения данных позволяют добавление совместить с обновлением.
        addTask(task);
    }

    /**
     * Добавление эпики.
     *
     * @param epic
     */
    public void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    /**
     * Обновление эпики.
     *
     * @param epic
     */
    public void updateEpic(Epic epic) {
        //Структуры хранения данных позволяют добавление совместить с обновлением.
        addEpic(epic);
    }

    /**
     * Добавить подзадачу в эпику.
     *
     * @param subtask подзадача эпики
     */
    public boolean addSubtask(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpicId());
        if (epic == null) {
            //Передан неверный идентификатор эпики. Некуда добавлять подзадачу.
            return false;
        } else {
            epic.addSubTasks(subtask);
            return true;
        }
    }

    /**
     * Обновить подзадачу какой-то эпики.
     *
     * @param subtask подзадача эпики
     */
    public boolean updateSubtask(Subtask subtask) {
        //Структуры хранения данных позволяют добавление совместить с обновлением.
        return addSubtask(subtask);
    }

    /**
     * Удаление задачи по идентификатору.
     *
     * @param taskId id задачи
     */
    public boolean removeTaskById(int taskId) {
        return tasks.remove(taskId) != null;
    }

    /**
     * Удаление эпики по идентификатору.
     *
     * @param epicId id эпики
     */
    public boolean removeEpicById(int epicId) {
        return epics.remove(epicId) != null;
    }

    /**
     * Удаление подзадачи по идентификатору.
     *
     * @param subtaskId id подзадачи
     */
    public boolean removeSubtaskById(int subtaskId) {
        for (Epic epic : epics.values()) {
            Map<Integer, Subtask> subtasks = epic.getSubtasks();
            if (subtasks.containsKey(subtaskId)) {
                epic.removeSubtask(subtaskId);
                return true;
            }
        }
        return false;
    }

}
