package ru.mark.sprint5.manager;

import ru.mark.sprint5.models.Epic;
import ru.mark.sprint5.models.Node;
import ru.mark.sprint5.models.Subtask;
import ru.mark.sprint5.models.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private MyLinkedHashMap<Task> history;

    public InMemoryHistoryManager() {
        history = new MyLinkedHashMap<>();
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

    public static class MyLinkedHashMap<T extends Task> {
        int size = 0;

        Node<T> first;
        Node<T> last;

        Map<Integer, Node<T>> tasks = new HashMap<>();

        public int size() {
            return size;
        }

        void linkLast(T task) {
            Node l = last;
            //Вначале создаем ноду
            Node newNode = new Node(task, l, null);
            last = newNode;
            //создаем ссылку на добавляемую ноду
            if (l == null) {
                first = newNode;
            } else {
                l.next = newNode;
            }
            //увеличиваем размер
            size++;
            //"следует убедиться, что удаляется предыдущий узел с таким же ID (если он существует), чтобы избежать дублирования"
            //находим предыдущую ноду по id добавляемой задачи.
            Node<T> prevNode = tasks.get(task.getId());
            //и, если ссылка существует, то удаляем её.
            if (prevNode != null) {
                remove(prevNode);
            }
            //добавляем в карту новую ноду.
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
            if (node != null) {
                Task task = node.data;
                //1. удаляем из мапы ноду по ключу задачи.
                tasks.remove(task.getId());
                //2. удаляем ссылки на удаляемую ноду.
                Node<T> nextNode = node.next;
                Node<T> prevNode = node.prev;
                if (nextNode != null && prevNode != null) {
                    nextNode.prev = prevNode;
                    prevNode.next = nextNode;
                } else if (nextNode == null) {
                    prevNode.next = null;
                    last = prevNode;
                } else {
                    nextNode.prev = null;
                    first = nextNode;
                }
                size--;
            }
        }

        public void removeById(int id) {
            Node<T> node = tasks.get(id);
            if (node != null) {
                remove(node);
                Task task = node.data;
                if (task instanceof Epic) {
                    for (Subtask subtask : ((Epic) task).getAllSubtasks()) {
                        removeById(subtask.getId());
                    }
                }
            }
        }
    }

}
