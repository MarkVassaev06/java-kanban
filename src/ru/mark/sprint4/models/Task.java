package ru.mark.sprint4.models;

import java.util.Objects;

/**
 * Задача.
 */
public class Task {
    /**
     * Уникальный идентификатор задачи.
     */
    protected final int id;
    /**
     * Наименование задачи.
     */
    protected final String name;
    /**
     * Описание задачи.
     */
    protected final String description;

    /**
     * Статус задачи.
     */
    protected Status status;

    public Task(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        status = Status.NEW;
    }

    public Task(int id, String name, String description, Status status) {
        this(id, name, description);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
