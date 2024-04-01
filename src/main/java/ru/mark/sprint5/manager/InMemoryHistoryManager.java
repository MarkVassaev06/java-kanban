package ru.mark.sprint5.manager;


import ru.mark.sprint5.models.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int HISTORY_LIMIT = 10;

    private LinkedHashMap<Task> history;

    public InMemoryHistoryManager() {
        history = new LinkedHashMap<>();
    }

    @Override
    public void add(Task task) {
        if (task != null) {
            history.linkLast(task);
            if (history.size() > HISTORY_LIMIT) {
                history.remove(HISTORY_LIMIT);
            }
        }
    }

    private void removeNode(Node node) {
        history.
    }

    @Override
    public void remove(int id) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Task> getHistory() {
        return List.copyOf(history.getTasks());
    }

    public static class LinkedHashMap<T extends Task> {
        int size = 0;

        Node<T> first;
        Node<T> last;

        Map<Integer, T> tasks = new HashMap<>();

        public int size() {
            return size;
        }

        void linkLast(T task) {
            Node l = last;
            Node newNode = new Node(task, l, null);
            last = newNode;
            if (l == null) {
                first = newNode;
            } else {
                l.next = newNode;
            }
            size++;
            tasks.put(task.getId(), task);
        }

        List<T> getTasks() {
            List<T> result = new ArrayList<>();
            Node<T> temp = first;
            while (temp != null) {
                result.add(temp.data);
                temp = temp.next;
            }
            return result;
        }

    }

    public static class Node<T> {

        private Node prev;
        private Node next;
        private T data;

        public Node(T data, Node prev, Node next) {
            this.prev = prev;
            this.next = next;
            this.data = data;
        }
    }
}
