package ru.mark.sprint5.manager;

import ru.mark.sprint5.models.Epic;
import ru.mark.sprint5.models.Status;
import ru.mark.sprint5.models.Subtask;
import ru.mark.sprint5.models.Task;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.mark.sprint5.models.Task.DATE_TIME_FORMATTER;

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
        int totalCount = 0;
        // Количество пропущенных строк.
        int skipCount = 0;
        // Количество неформатных строк.
        int badCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            //Читаем заголовок. Впустую.
            if (br.ready()) {
                br.readLine();
            }

            while (br.ready()) {
                //Читаем строку с описанием задачи, эпики и подзадачи.
                String line = br.readLine();
                if (line == null || line.isEmpty()) {
                    skipCount++;
                    continue;
                }
                String[] fields = line.split(",");
                if (fields.length < 5) {
                    badCount++;
                    continue;
                }
                //попытаемся прочитать хоть что-то.
                try {
                    readLine(fields);
                } catch (IllegalArgumentException ie) { //NumberFormatException extends IllegalArgumentException
                    badCount++;
                }
            }
            if (skipCount != 0) {
                System.out.printf("При чтении файла было пропущено %d пустых строк. %n", skipCount);
            }
            if (badCount != 0) {
                System.out.printf("При чтении файла было пропущено %d неформатных строк. %n", badCount);
            }
        } catch (IOException ex) {
            throw new ManagerSaveException(ex.getMessage(), ex);
        }
        if (totalCount == 0) {
            throw new ManagerSaveException("Файл загрузки пустой.");
        }
        if (totalCount == skipCount + badCount) {
            throw new ManagerSaveException("Файл загрузки целиком состоит из некорректных данных.");
        }
    }

    private void readLine(String[] fields) {
        //представление в формате id,type,name,status,description,startTime,duration,epicId

        //вдруг строка не число?
        int id = Integer.parseInt(fields[0]);
        String name = fields[2];
        //возможно, и здесь у нас какое-то неверное значение для enum.
        Status status = Status.valueOf(fields[3]);
        String description = fields[4];
        String startTimeStr = fields[5];
        LocalDateTime startTime = null;
        int ofMinutes = -1;
        //У эпики дата начала и длитиельность - вычисляемые значения.
        if (!fields[1].equals("EPIC")) {
            startTime = LocalDateTime.from(DATE_TIME_FORMATTER.parse(startTimeStr));
            String durationStr = fields[6];
            ofMinutes = Integer.parseInt(durationStr);
        }

        switch (fields[1]) {
            case "TASK":
                super.addTask(new Task(id,
                        name,
                        description,
                        status,
                        startTime,
                        ofMinutes));
                break;
            case "EPIC":
                super.addEpic(new Epic(id, name, description));
                break;
            case "SUBTASK":
                super.addSubtask(new Subtask(id,
                        name,
                        description,
                        status,
                        startTime,
                        ofMinutes,
                        Integer.parseInt(fields[7]) //здесь тоже может выскочить Exception.
                ));
                break;
            default:
                //skip
        }
    }

    public void save() {
        List<Task> allTasks = new ArrayList<>(getEpics());
        allTasks.addAll(getTasks());
        allTasks.addAll(getAllSubtask());

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("id,type,name,status,description,startTime,duration,epic\n");
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
