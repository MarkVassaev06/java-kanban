package ru.mark.sprint5.manager;

/**
 * Фабрика?
 */
public class Managers {
    /**
     * Менеджер задач по умолчанию.
     *
     * @return TaskManager
     */
    TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
}
