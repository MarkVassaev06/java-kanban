package ru.mark.sprint5.manager;

import ru.mark.sprint5.models.Epic;
import ru.mark.sprint5.models.Subtask;
import ru.mark.sprint5.models.Task;

import java.util.List;

/**
 * Интерфейс менеджер задач.
 */
public interface TaskManager {
    int nextTaskId();

    /**
     * Первый метод получения списка всех задач.
     */
    List<Task> getTasks();

    /**
     * Второй метод получения списка всех эпик.
     */
    List<Epic> getEpics();

    /**
     * Третий метод получения всех подзадач.
     */
    List<Subtask> getAllSubtask();

    /**
     * Получение всех подзадач эпики.
     *
     * @param epicId id эпики
     */
    List<Subtask> getSubtasksByEpicId(int epicId);

    /**
     * 1. Первый метод удаления: удаление всех задач.
     */
    void removeAllTasks();

    /**
     * 2. Второй метод удаления: удаление всех эпик.
     * Поскольку эпик содержит все свои позадачи, то удаление эпик удаляет все ссылки на его подзадачи.
     * Ровно также, как удаление объекта удаляет все ссылки на поля.
     * Итак: при удалении всех эпиков удаляются все подзадачи и, очевидно, их идентификаторы.
     */
    void removeAllEpics();

    /**
     * 3. Третий метод: удаление всех подзадач. Очевидно, что подзадачи <b>всех</b> эпик.
     */
    void removeAllSubtasks();

    /**
     * Удаление всех подзадач эпики.
     */
    void removeAllSubtasksFromEpic(int epicId);

    /**
     * Получить задачу по идентификатору.
     *
     * @param taskId id задачи
     */
    Task getTaskById(int taskId);

    /**
     * Получить эпик по идентификатору.
     *
     * @param epicId id эпики
     */
    Epic getEpicById(int epicId);

    /**
     * Запрос подзадачи по идентификтатору.
     *
     * @param subtaskId id подзадачи
     */
    Subtask getSubtaskById(int subtaskId);

    /**
     * Добавление задачи.
     *
     * @param task
     */
    void addTask(Task task);

    /**
     * Обновление задачи.
     *
     * @param task
     */
    void updateTask(Task task);

    /**
     * Добавление эпики.
     *
     * @param epic
     */
    void addEpic(Epic epic);

    /**
     * Обновление эпики.
     *
     * @param epic
     */
    void updateEpic(Epic epic);

    /**
     * Добавить подзадачу в эпику.
     *
     * @param subtask подзадача эпики
     */
    boolean addSubtask(Subtask subtask);

    /**
     * Обновить подзадачу какой-то эпики.
     *
     * @param subtask подзадача эпики
     */
    boolean updateSubtask(Subtask subtask);

    /**
     * Удаление задачи по идентификатору.
     *
     * @param taskId id задачи
     */
    boolean removeTaskById(int taskId);

    /**
     * Удаление эпики по идентификатору.
     *
     * @param epicId id эпики
     */
    boolean removeEpicById(int epicId);

    /**
     * Удаление подзадачи по идентификатору.
     *
     * @param subtaskId id подзадачи
     */
    boolean removeSubtaskById(int subtaskId);
}
