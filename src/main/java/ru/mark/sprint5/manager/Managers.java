package ru.mark.sprint5.manager;

/**
 * Фабрика?
 */
public class Managers {
    private Managers() {
    }

    /**
     * Менеджер задач по умолчанию.
     *
     * @return TaskManager
     */
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    /**
     * Менеджер истории.
     */
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
