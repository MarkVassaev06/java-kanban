package ru.mark.sprint4.models;

import java.util.*;

/**
 * Эпик, включает в себя множество задач {@link Subtask}.
 */
public class Epic extends Task {

    //Список подзадач. Выбрана концепция знания родителя о своих детях, принадлежность подзадачи эпике не требовалась.
    private Map<Integer, Subtask> subtasks;

    public Epic(int id, String name, String description) {
        super(id, name, description);
        subtasks = new HashMap<>();
    }

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
        checkStatus();
    }

    @Override
    public Status getStatus() {
        checkStatus();
        return status;
    }

    private void checkStatus() {
        boolean done = true;
        boolean isNew = true;
        for (Subtask item : subtasks.values()) {
            done = done && item.status == Status.DONE;
            isNew = isNew && item.status == Status.NEW;
        }
        if (done) {
            status = Status.DONE;
        } else if (isNew) {
            status = Status.NEW;
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

    /**
     * Удаление подзадачи по идентификатору.
     *
     * @param subtaskId id подзадачи
     */
    public void removeSubtask(int subtaskId) {
        subtasks.remove(subtaskId);
        checkStatus();
    }

    @Override
    public String toString() {
        checkStatus();
        List<String> subTaskStr = new ArrayList<>();
        for (Subtask value : subtasks.values()) {
            subTaskStr.add(value.toString());
        }
        return "Epic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + getStatus() +
                ", \nsubtasks=\n" + String.join("\n", subTaskStr) +
                "\n}\n";
    }

}
