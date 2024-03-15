package ru.mark.sprint4.manager;

import ru.mark.sprint4.models.Epic;
import ru.mark.sprint4.models.Subtask;
import ru.mark.sprint4.models.Task;

import java.util.*;

/**
 * Генератор id подзадач и эпик.
 */
public class InMemoryTaskManager implements TaskManager {
    private static int taskId = 1;

    private Map<Integer, Task> tasks;
    private Map<Integer, Epic> epics;

    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
    }

    @Override
    public int nextTaskId() {
        return taskId++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Task> getTasks() {
        return Collections.unmodifiableList(new ArrayList<>(tasks.values()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Epic> getEpics() {
        return Collections.unmodifiableList(new ArrayList<>(epics.values()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Subtask> getAllSubtask() {
        List<Subtask> result = new ArrayList<>();
        for (Epic epic : epics.values()) {
            result.addAll(epic.getAllSubtasks());
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Subtask> getSubtasksByEpicId(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            return epic.getAllSubtasks();
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAllEpics() {
        epics.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAllSubtasks() {
        for (Epic epic : epics.values()) {
            //здесь же происходит смена статуса эпики на NEW.
            epic.clearSubtasks();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAllSubtasksFromEpic(int epicId) {
        Epic epic = getEpicById(epicId);
        if (epic != null) {
            epic.clearSubtasks();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Task getTaskById(int taskId) {
        return tasks.get(taskId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Epic getEpicById(int epicId) {
        return epics.get(epicId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateTask(Task task) {
        //Структуры хранения данных позволяют добавление совместить с обновлением.
        addTask(task);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEpic(Epic epic) {
        //Структуры хранения данных позволяют добавление совместить с обновлением.
        addEpic(epic);
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
    public boolean updateSubtask(Subtask subtask) {
        //Структуры хранения данных позволяют добавление совместить с обновлением.
        return addSubtask(subtask);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeTaskById(int taskId) {
        return tasks.remove(taskId) != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeEpicById(int epicId) {
        return epics.remove(epicId) != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
