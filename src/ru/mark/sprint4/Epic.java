package ru.mark.sprint4;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Эпика, включает в себя множество задач {@link Subtask}.
 */
public class Epic extends Task {

    private Map<Integer, Subtask> subtasks;

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
        subtasks = new HashMap<>();
        this.status = Status.NEW;
    }

    /**
     * Добавление подзадачи в эпику.
     */
    public void addSubTasks(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        boolean done = true;
        for (Subtask item : subtasks.values()) {
            done = done && item.status == Status.DONE;
            if (!done) {
                break;
            }
        }
        if (done) {
            status = Status.DONE;
        } else {
            status = Status.IN_PROGRESS;
        }
    }

    /**
     * Очищаем список подзадач эпики.
     */
    public void clearSubtasks() {
        subtasks.clear();
        status = Status.NEW;
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
