package ru.mark.sprint5.models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Задача.
 */
public class Task {

    public static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
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

    /**
     * Продолжительность задачи, оценка того, сколько времени она займёт в минутах.
     */
    protected Duration duration;
    /**
     * Дата и время, когда предполагается приступить к выполнению задачи.
     */
    protected LocalDateTime startTime;

    public Task(int id, String name, String description, int minutes, LocalDateTime startTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = Duration.ofMinutes(minutes);
        this.startTime = startTime;
        status = Status.NEW;
    }

    public Task(int id, String name, String description, int minutes, LocalDateTime startTime, Status status) {
        this(id, name, description, minutes, startTime);
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

    /**
     * Дата и время завершения задачи, которые рассчитываются исходя из startTime и duration.
     */
    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("%d, TASK. %s. $d минут. %s, %s, $s",
                id,
                startTime.format(DATE_TIME_FORMATTER),
                duration.toMinutes(),
                name,
                status,
                description);
    }
}
