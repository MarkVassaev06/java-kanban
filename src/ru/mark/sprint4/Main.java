package ru.mark.sprint4;

public class Main {

    public static final void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        //Создайте две задачи,
        Task task1 = new Task(taskManager.nextTaskId(), "task1", "description1");
        taskManager.addTask(task1);
        Task task2 = new Task(taskManager.nextTaskId(), "task2", "description2");
        taskManager.addTask(task2);

        //а также эпик с двумя подзадачами и эпик с одной подзадачей.
        Epic epic1 = new Epic(taskManager.nextTaskId(), "epic1", "epicDescription1");
        taskManager.addEpic(epic1);
        taskManager.addSubtask(
                epic1.getId(),
                new Subtask(taskManager.nextTaskId(), "subtask11", "subtaskDescription11"));
        taskManager.addSubtask(
                epic1.getId(),
                new Subtask(taskManager.nextTaskId(), "subtask12", "subtaskDescription12"));

        Epic epic2 = new Epic(taskManager.nextTaskId(), "epic2", "epicDescription2");
        taskManager.addEpic(epic2);
        Subtask subtask21 = new Subtask(taskManager.nextTaskId(), "subtask21", "subtaskDescription21");
        taskManager.addSubtask(
                epic2.getId(),
                subtask21);

        System.out.println("tasks \n" + taskManager.getTasks());

        System.out.println("epics \n" + taskManager.getEpics());

        System.out.println("subtasks \n" + taskManager.getAllSubtask());

        task1.status = Status.DONE;
        task2.status = Status.IN_PROGRESS;


    }

}
