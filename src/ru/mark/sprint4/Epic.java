package ru.mark.sprint4;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Эпика, включает в себя множество задач {@link Subtask}.
 */
public class Epic extends Task {

    private Map<Integer, Subtask> subtasks;

    public Epic(int id, String name, String description) {
        super(id, name, description);
        subtasks = new HashMap<>();
    }

    /**
     * Добавление подзадачи в эпику.
     */
    public void addSubTasks(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }

    /**
     * Очищаем список подзадач эпики.
     */
    public void clearSubtasks() {
        subtasks.clear();
    }

    /**
     * Список подзадач эпики.
     */
    public Collection<Subtask> getAllSubtasks() {
        return subtasks.values();
    }

    public Map<Integer, Subtask> getSubtasks() {
        return subtasks;
    }
}
