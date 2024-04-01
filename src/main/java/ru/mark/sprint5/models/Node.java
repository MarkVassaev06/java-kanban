package ru.mark.sprint5.models;

public class Node {

    private Node prev;
    private Node next;
    private Task task;

    public Node(Task task, Node prev, Node next) {
        this.prev = prev;
        this.next = next;
        this.task = task;
    }
}
