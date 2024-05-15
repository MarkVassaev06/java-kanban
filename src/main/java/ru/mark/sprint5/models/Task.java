package ru.mark.sprint5.models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Задача.
 */
public class Task implements Comparable<Task> {

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

    public Task(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startTime = LocalDateTime.now();
        this.duration = Duration.ZERO;
    }

    public Task(int id, String name, String description, LocalDateTime startTime, int minutes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.duration = Duration.ofMinutes(minutes);
        status = Status.NEW;
    }

    public Task(int id, String name, String description, Status status, LocalDateTime startTime, int minutes) {
        this(id, name, description, startTime, minutes);
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

    /**
     * Дата-время начала задачи.
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        //представление в формате id,type,name,status,description,startTime,duration,epicId
        return String.format("%d,TASK,%s,%s,%s,$s,%d,",
                id,
                name,
                status,
                description,
                startTime.format(DATE_TIME_FORMATTER),
                duration.toMinutes());
    }

    public Duration getDuration() {
        return duration;
    }

    /**
     * Для сортировки списка.
     */
    @Override
    public int compareTo(Task o) {
        if (o == null) {
            return 1;
        }
        return startTime.compareTo(o.getStartTime());
    }
}
