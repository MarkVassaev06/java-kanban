package ru.mark.sprint4;

/**
 * Генератор id подзадач и эпик.
 */
public class TaskManager {
    private static int taskId = 1;

    private TaskManager() {
    }

    public int nextTaskId() {
        return taskId++;
    }
}
