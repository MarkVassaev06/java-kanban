package ru.mark.sprint5.models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Эпик, включает в себя множество задач {@link Subtask}.
 * Решений одной задачи может быть много.
 * Хранение списка id подзадач требует вынести хранение самого списка куда-то в другое место.
 * Вероятно в TaskManager... Это сильно усложнит решение.
 * Эпик содержит подзадачи, TaskManager лишь обращается к методам Epic.
 * Из ТЗ: "Каждый эпик знает, какие подзадачи в него входят.".
 */
public class Epic extends Task {

    //Список подзадач. Выбрана концепция знания родителя о своих детях,
    // принадлежность подзадачи эпике не требовалась.
    private Map<Integer, Subtask> subtasks;
    /**
     * Внутренняя структура, для хранения отсортированного по времени начала списка подзадач.
     */
    private TreeSet<Subtask> innerStructure;

    //protected здесь неуместен, поскольку никто класс Epic не наследует.
    //Комментарий: "Достаточно хранить только список id..."
    //Ответ: Эпик содержит подзадачи и является владельцем подзадач.

    /**
     * Самая ранняя подзадача.
     */
    private Subtask firstSubTask;
    /**
     * Самая поздняя подзадача.
     */
    private Subtask lastSubTask;

    public Epic(int id, String name, String description) {
        super(id, name, description);
        subtasks = new HashMap<>();
        innerStructure = new TreeSet<>();
    }

    /**
     * Добавление подзадачи в эпику.
     */
    public void addSubTasks(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        innerStructure.add(subtask);
        //обновляем статус.
        checkStatus();
        //Обновляем начальную и последнюю задачи для наискорейшего доступа к временам.
        if (firstSubTask == null || firstSubTask.getStartTime().isAfter(subtask.getStartTime())) {
            firstSubTask = subtask;
        }
        if (lastSubTask == null || lastSubTask.getEndTime().isBefore(subtask.getEndTime())) {
            lastSubTask = subtask;
        }
    }

    @Override
    public Status getStatus() {
        //вычисляем статус, обновляем.
        checkStatus();
        return status;
    }

    private void checkStatus() {
        boolean done = true;
        boolean isNew = true;
        for (Subtask item : innerStructure) {
            done = done && item.status == Status.DONE;
            isNew = isNew && item.status == Status.NEW;
        }
        if (done) {
            status = Status.DONE;
        } else if (isNew) {
            status = Status.NEW;
        } else {
            status = Status.IN_PROGRESS;
        }
    }

    /**
     * Очищаем список подзадач эпики.
     */
    public void clearSubtasks() {
        //Очищаем список подзадач.
        subtasks.clear();
        innerStructure.clear();
        //обновляем статус.
        status = Status.NEW;
        firstSubTask = null;
        lastSubTask = null;
    }

    /**
     * Список подзадач эпики.
     */
    public List<Subtask> getAllSubtasks() {
        return List.copyOf(innerStructure);
    }

    /**
     * Расчетное дата-время завершения эпики.
     */
    public LocalDateTime getEndTime() {
        if (lastSubTask != null) {
            return lastSubTask.getEndTime();
        } else {
            return null;
        }
    }

    /**
     * Расчетное дата-время начала эпики.
     */
    public LocalDateTime getStartTime() {
        if (firstSubTask != null) {
            return firstSubTask.getStartTime();
        } else {
            return null;
        }
    }

    @Override
    public Duration getDuration() {
        Duration result = Duration.ZERO;
        for (Subtask subtask : innerStructure) {
            result.plus(subtask.getDuration());
        }
        return result;
    }

    public Map<Integer, Subtask> getSubtasks() {
        return Collections.unmodifiableMap(subtasks);
    }

    /**
     * Удаление подзадачи по идентификатору.
     *
     * @param subtaskId id подзадачи
     */
    public Subtask removeSubtask(int subtaskId) {
        Subtask removedSubTask = subtasks.remove(subtaskId);
        innerStructure.remove(removedSubTask);
        //обновляем статус.
        checkStatus();
        //Обновляем начальную и последнюю задачи для наискорейшего доступа к временам.
        firstSubTask = innerStructure.first();
        lastSubTask = innerStructure.last();
        return removedSubTask;
    }


    @Override
    public String toString() {
        checkStatus();
        //представление в формате id,type,name,status,description,startTime,duration,epicId
        return String.format("%d,SUBTASK,%s,%s,%s,$s,%d,",
                id,
                name,
                status,
                description,
                getStartTime().format(DATE_TIME_FORMATTER),
                getDuration().toMinutes());
    }

}
