package ru.mark.sprint4;

/**
 * Задача.
 */
public class Task {
    /**
     * Наименование задачи.
     */
    private String name;
    /**
     * Описание задачи.
     */
    private String description;
    /**
     * Уникальный идентификатор задачи.
     */
    private int id;

    public Task(int id, String name, String description) {
        this.name = name;
        this.description = description;
        this.id = id;
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
}
