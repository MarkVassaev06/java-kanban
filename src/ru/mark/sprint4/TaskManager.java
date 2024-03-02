package ru.mark.sprint4;

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
     * Получение списка всех задач.
     */
    public Collection<Task> getTasks() {
        return tasks.values();
    }

    /**
     * Получение списка всех эпик.
     */
    public Collection<Epic> getEpics() {
        return epics.values();
    }

    /**
     * Получить все подзадачи всех эпик.
     */
    public List<Subtask> getAllSubtask() {
        List<Subtask> result = new ArrayList<>();
        for (Epic epic : epics.values()) {
            result.addAll(epic.getAllSubtasks());
        }
        return result;
    }

    /**
     * Получение подзадач эпики.
     *
     * @param epicId id эпики
     */
    public Collection<Subtask> getSubtasksByEpicId(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            return epic.getAllSubtasks();
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Удаление всех задач.
     */
    public void removeAllTasks() {
        tasks.clear();
    }

    /**
     * Удаление всех эпик.
     */
    public void removeAllEpics() {
        epics.clear();
    }

    /**
     * Удаление всех подзадач эпики.
     */
    public void removeAllSubtasksFromEpic(Epic epic) {
        epic.clearSubtasks();
    }

    /**
     * Запрос задачи по идентификатору.
     *
     * @param taskId id задачи
     */
    public Task getTaskById(int taskId) {
        return tasks.get(taskId);
    }

    /**
     * Получить эпику по идентификатору.
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
     * @param epicId  id эпики
     * @param subtask подзадача эпики
     */
    public boolean addSubtask(int epicId, Subtask subtask) {
        Epic epic = epics.get(epicId);
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
        for (Epic epic : epics.values()) {
            Map<Integer, Subtask> subtasks = epic.getSubtasks();
            Subtask oldSubtask = subtasks.get(subtask.getId());
            //Обновление - замена чего-то существующего. Для добавления есть отдельный метод.
            if (oldSubtask != null) {
                subtasks.put(subtask.getId(), subtask);
                return true;
            }
        }
        return false;
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
                subtasks.remove(subtaskId);
                epic.removeSubtask(subtaskId);
                return true;
            }
        }
        return false;
    }

}
