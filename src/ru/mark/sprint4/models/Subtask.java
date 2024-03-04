package ru.mark.sprint4.models;

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

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + id +
                ", epicId=" + epicId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
