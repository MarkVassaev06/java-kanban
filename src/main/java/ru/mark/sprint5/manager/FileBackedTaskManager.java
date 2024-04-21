package ru.mark.sprint5.manager;

import ru.mark.sprint5.models.Epic;
import ru.mark.sprint5.models.Status;
import ru.mark.sprint5.models.Subtask;
import ru.mark.sprint5.models.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Файловый менеджер задач.
 */
public class FileBackedTaskManager extends InMemoryTaskManager {

    private final File file;

    public FileBackedTaskManager(File file) throws ManagerSaveException {
        this.file = file;
        if (!file.exists()) {
            throw new ManagerSaveException("Файл не существует.");
        } else if (!file.isFile()) {
            throw new ManagerSaveException("Путь не является файлом.");
        }
        innerLoad();
    }

    /**
     * Загрузка данных из файла.
     */
    private void innerLoad() {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            //Читаем заголовок. Впустую.
            String header = br.readLine();
            while (br.ready()) {
                //Читаем строку с описанием задачи, эпики и подзадачи.
                String line = br.readLine();
                String[] fields = line.split(",");
                int id = Integer.parseInt(fields[0]);
                String name = fields[2];
                Status status = Status.valueOf(fields[3]);
                String description = fields[4];
                switch (fields[1]) {
                    case "TASK":
                        super.addTask(new Task(id,
                                name,
                                description,
                                status));
                        break;
                    case "EPIC":
                        super.addEpic(new Epic(id, name, description));
                        break;
                    case "SUBTASK":
                        super.addSubtask(new Subtask(id,
                                name,
                                description,
                                Integer.parseInt(fields[5]),
                                status));
                        break;
                }
            }
        } catch (IOException ex) {
            throw new ManagerSaveException(ex.getMessage(), ex);
        }
    }

    public void save() {
        List<Task> allTasks = new ArrayList<>(getEpics());
        allTasks.addAll(getTasks());
        allTasks.addAll(getAllSubtask());

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("id,type,name,status,description,epic\n");
            for (Task task : allTasks) {
                bw.write(task.toString() + "\n");
            }
        } catch (IOException ex) {
            throw new ManagerSaveException(ex.getMessage(), ex);
        }
    }

    public static FileBackedTaskManager loadFromFile(File file) throws ManagerSaveException {
        return new FileBackedTaskManager(file);
    }

    @Override
    public boolean addSubtask(Subtask subtask) {
        boolean result = super.addSubtask(subtask);
        save();
        return result;
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        save();
    }

    @Override
    public void removeAllSubtasksFromEpic(int epicId) {
        super.removeAllSubtasksFromEpic(epicId);
        save();
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public boolean updateSubtask(Subtask subtask) {
        boolean result = super.updateSubtask(subtask);
        save();
        return result;
    }

    @Override
    public boolean removeTaskById(int taskId) {
        boolean result = super.removeTaskById(taskId);
        save();
        return result;
    }

    @Override
    public boolean removeEpicById(int epicId) {
        boolean result = super.removeEpicById(epicId);
        save();
        return result;
    }

    @Override
    public boolean removeSubtaskById(int subtaskId) {
        boolean result = super.removeSubtaskById(subtaskId);
        save();
        return result;
    }
}
