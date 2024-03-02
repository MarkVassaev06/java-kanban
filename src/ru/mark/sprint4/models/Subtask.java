package ru.mark.sprint4.models;

/**
 * Подзадача, входит в эпику {@link Epic}.
 * Знание об эпике, в которую входит подзадача, не требуется.
 */
public class Subtask extends Task {

    public Subtask(int id, String name, String description) {
        super(id, name, description);
    }

    public Subtask(int id, String name, String description, Status status) {
        super(id, name, description, status);
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
