package ru.mark.sprint5.manager;

import ru.mark.sprint5.models.Epic;
import ru.mark.sprint5.models.Subtask;
import ru.mark.sprint5.models.Task;

import java.util.*;

/**
 * Генератор id подзадач и эпик.
 */
public class InMemoryTaskManager implements TaskManager {
    private static int taskId = 1;

    private Map<Integer, Task> tasks;
    private Map<Integer, Epic> epics;
    private HistoryManager historyManager;
    private TreeSet<Task> innerStructure;

    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        innerStructure = new TreeSet<>();
        historyManager = Managers.getDefaultHistory();
    }

    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
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
        innerStructure.removeAll(tasks.values());
        tasks.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAllEpics() {
        for (Epic epic : epics.values()) {
            innerStructure.removeAll(epic.getAllSubtasks());
        }
        epics.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAllSubtasks() {
        for (Epic epic : epics.values()) {
            //здесь же происходит смена статуса эпики на NEW.
            innerStructure.removeAll(epic.getAllSubtasks());
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
            innerStructure.removeAll(epic.getAllSubtasks());
            epic.clearSubtasks();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Task getTaskById(int taskId) {
        Task task = tasks.get(taskId);
        historyManager.add(task);
        return task;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = epics.get(epicId);
        historyManager.add(epic);
        return epic;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Subtask getSubtaskById(int subtaskId) {
        Subtask subtask = null;
        for (Epic epic : epics.values()) {
            subtask = epic.getSubtasks().get(subtaskId);
            if (subtask != null) {
                break;
            }
        }
        historyManager.add(subtask);
        return subtask;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTask(Task task) {
        tasks.put(task.getId(), task);
        innerStructure.add(task);
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
        innerStructure.addAll(epic.getAllSubtasks());
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
            innerStructure.add(subtask);
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
        Task task = tasks.remove(taskId);
        innerStructure.remove(task);
        return task != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeEpicById(int epicId) {
        Epic epic = epics.remove(epicId);
        innerStructure.remove(epic);
        return epic != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeSubtaskById(int subtaskId) {
        for (Epic epic : epics.values()) {
            Map<Integer, Subtask> subtasks = epic.getSubtasks();
            if (subtasks.containsKey(subtaskId)) {
                Subtask subtask = epic.removeSubtask(subtaskId);
                innerStructure.remove(subtask);
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Task> getPrioritizedTasks() {
        return innerStructure;
    }

}
