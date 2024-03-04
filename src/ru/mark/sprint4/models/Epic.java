package ru.mark.sprint4.models;

import java.util.*;

/**
 * Эпик, включает в себя множество задач {@link Subtask}.
 * Решений одной задачи может быть много.
 * Хранение списка id подзадач требует вынести хранение самого списка куда-то в другое место.
 * Вероятно в TaskManager... Это сильно усложнит решение.
 * Эпик содержит подзадачи, TaskManager лишь обращается к методам Epic.
 * Из ТЗ: "Каждый эпик знает, какие подзадачи в него входят.".
 */
public class Epic extends Task {

    //Список подзадач. Выбрана концепция знания родителя о своих детях, принадлежность подзадачи эпике не требовалась.
    private Map<Integer, Subtask> subtasks;
    //protected здесь неуместен, поскольку никто класс Epic не наследует.
    //Комментарий: "Достаточно хранить только список id..."
    //Ответ: Эпик содержит подзадачи и является владельцем подзадач.

    public Epic(int id, String name, String description) {
        super(id, name, description);
        subtasks = new HashMap<>();
    }

    /**
     * Добавление подзадачи в эпику.
     */
    public void addSubTasks(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        //обновляем статус.
        checkStatus();
    }

    @Override
    public Status getStatus() {
        //вычисляем статус, обновляем.
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
        //Очищаем список подзадач.
        subtasks.clear();
        //обновляем статус.
        status = Status.NEW;
    }

    /**
     * Список подзадач эпики.
     */
    public List<Subtask> getAllSubtasks() {
        return Collections.unmodifiableList(new ArrayList<>(subtasks.values()));
    }

    public Map<Integer, Subtask> getSubtasks() {
        return Collections.unmodifiableMap(subtasks);
    }

    /**
     * Удаление подзадачи по идентификатору.
     *
     * @param subtaskId id подзадачи
     */
    public void removeSubtask(int subtaskId) {
        subtasks.remove(subtaskId);
        //обновляем статус.
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
