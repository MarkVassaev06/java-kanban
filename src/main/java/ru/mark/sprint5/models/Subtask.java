package ru.mark.sprint5.models;

/**
 * Подзадача, входит в эпику {@link Epic}.
 * Знание об эпике, в которую входит подзадача, не требуется.
 */
public class Subtask extends Task {
    //Для каждой подзадачи известно, в рамках какого эпика она выполняется.
    private final int epicId;

    public Subtask(int id, String name, String description, int epicId) {
        super(id, name, description);
        this.epicId = epicId;
    }

    public Subtask(int id, String name, String description, int epicId, Status status) {
        this(id, name, description, epicId);
        this.status = status;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return id + ",SUBTASK," + name + ',' + status + ',' + description + ',' + epicId;
    }
}
