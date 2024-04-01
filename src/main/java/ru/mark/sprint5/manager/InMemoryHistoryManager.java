package ru.mark.sprint5.manager;

import ru.mark.sprint5.models.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int HISTORY_LIMIT = 10;

    private List<Task> history;

    public InMemoryHistoryManager() {
        history = new ArrayList<>();
    }

    @Override
    public void add(Task task) {
        if (task != null) {
            history.add(0, task);
            if (history.size() > HISTORY_LIMIT) {
                history.remove(HISTORY_LIMIT);
            }
        }
    }

    @Override
    public void remove(int id) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Task> getHistory() {
        return List.copyOf(history);
    }

}
