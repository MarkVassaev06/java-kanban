package ru.mark.sprint5.manager;

import ru.mark.sprint5.models.Node;
import ru.mark.sprint5.models.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private LinkedHashMap<Task> history;

    public InMemoryHistoryManager() {
        history = new LinkedHashMap<>();
    }

    @Override
    public void add(Task task) {
        if (task != null) {
            history.linkLast(task);
        }
    }

    @Override
    public void remove(int id) {
        history.removeById(id);
    }

    @Override
    public void removeNode(Node<Task> node) {
        history.remove(node);
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

        Map<Integer, Node<T>> tasks = new HashMap<>();

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
            Node<T> prevNode = tasks.get(task.getId());
            if (prevNode != null) {
                remove(prevNode);
            }
            tasks.put(task.getId(), newNode);
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

        void remove(Node<T> node) {
            Task task = node.data;
            //удаляем из мапы ноду
            tasks.remove(task.getId());
            Node<T> nextNode = node.next;
            Node<T> prevNode = node.prev;
            if (nextNode != null && prevNode != null) {
                nextNode.prev = prevNode;
                prevNode.next = nextNode;
            } else if (nextNode == null) {
                prevNode.next = null;
            } else {
                nextNode.prev = null;
            }

        }

        public void removeById(int id) {
            Node<T> node = tasks.get(id);
            remove(node);
        }
    }

}
