package ru.mark.sprint5.models;

import java.time.LocalDateTime;

/**
 * Подзадача, входит в эпику {@link Epic}.
 * Знание об эпике, в которую входит подзадача, не требуется.
 */
public class Subtask extends Task {
    //Для каждой подзадачи известно, в рамках какого эпика она выполняется.
    private final int epicId;

    public Subtask(int id, String name, String description, int minutes, LocalDateTime startTime, int epicId) {
        super(id, name, description, minutes, startTime);
        this.epicId = epicId;
    }

    public Subtask(int id, String name, String description, int minutes, LocalDateTime startTime, int epicId, Status status) {
        this(id, name, description, minutes, startTime, epicId);
        this.status = status;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return String.format("%d, SUBTASK. %s. $d минут. %s, %s, $s. %d",
                id,
                startTime.format(DATE_TIME_FORMATTER),
                duration.toMinutes(),
                name,
                status,
                description,
                epicId);
    }
}
