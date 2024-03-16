package ru.mark.sprint5.manager;

import ru.mark.sprint5.models.Task;

import java.util.List;

public interface HistoryManager {

    void add(Task task);

    /**
     * Возвращает последние 10 просмотренных задач.
     * Просмотром будем считать вызов тех методов, которые получают задачу по идентификатору.
     *
     * @return Список задач.
     */
    List<Task> getHistory();

}
