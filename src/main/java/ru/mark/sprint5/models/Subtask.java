package ru.mark.sprint5.models;

import java.time.LocalDateTime;

/**
 * Подзадача, входит в эпику {@link Epic}.
 * Знание об эпике, в которую входит подзадача, не требуется.
 */
public class Subtask extends Task {
    //Для каждой подзадачи известно, в рамках какого эпика она выполняется.
    private final int epicId;

    public Subtask(int id,
                   String name,
                   String description,
                   LocalDateTime startTime,
                   int minutes,
                   int epicId) {
        super(id, name, description, startTime, minutes);
        this.epicId = epicId;
    }

    public Subtask(int id,
                   String name,
                   String description,
                   Status status,
                   LocalDateTime startTime,
                   int minutes,
                   int epicId) {
        this(id, name, description, startTime, minutes, epicId);
        this.status = status;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        //представление в формате id,type,name,status,description,startTime,duration,epicId
        return String.format("%d,SUBTASK,%s,%s,%s,$s,%d,%d",
                id,
                name,
                status,
                description,
                startTime.format(DATE_TIME_FORMATTER),
                duration.toMinutes(),
                epicId);
    }
}
