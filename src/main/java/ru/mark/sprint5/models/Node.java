package ru.mark.sprint5.models;

public class Node<T> {

    public Node<T> prev;
    public Node<T> next;
    public T data;

    public Node(T data, Node<T> prev, Node<T> next) {
        this.prev = prev;
        this.next = next;
        this.data = data;
    }
}