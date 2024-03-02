package ru.mark.sprint4;

import java.util.Objects;

/**
 * Задача.
 */
public class Task {
    /**
     * Уникальный идентификатор задачи.
     */
    protected int id;
    /**
     * Наименование задачи.
     */
    protected String name;
    /**
     * Описание задачи.
     */
    protected String description;

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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return getClass().getSimpleName() + "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
