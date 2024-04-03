package ru.mark.sprint5.models;

public class Node<T> {

    public Node prev;
    public Node next;
    public T data;

    public Node(T data, Node prev, Node next) {
        this.prev = prev;
        this.next = next;
        this.data = data;
    }
}