package ru.mark.sprint4;

/**
 * Подзадача, входит в эпику {@link Epic}.
 */
public class Subtask extends Task {

    public Subtask(int id, String name, String description, Status status) {
        super(id, name, description, status);
    }
}
