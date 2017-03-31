package track.lessons.lesson3;

import java.util.NoSuchElementException;

/**
 * Должен наследовать List
 * Односвязный список
 */

public class MyLinkedList extends List implements Queue, Stack {

    /**
     * private - используется для сокрытия этого класса от других.
     * Класс доступен только изнутри того, где он объявлен
     * <p>
     * static - позволяет использовать Node без создания экземпляра внешнего класса
     */

    private int size = 0;
    private Node last;
    private Node first;


    @Override
    public void enqueue(int value) {
        push(value);
    }

    @Override
    public int dequeu() {
        Node node = first.next;
        if (node != null) {
            node.prev = null;
        }
        int value = first.val;
        first = node;
        size -= 1;
        return value;
    }

    @Override
    public void push(int value) {
        if (size == 0) {
            first = new Node(null, null, value);
            last = first;
            size += 1;
        } else {
            Node node = new Node(last, null, value);
            last.next = node;
            last = node;
            size += 1;
        }
    }

    @Override
    public int pop() {
        if (last != first) {
            Node previous = last.prev;
            previous.next = null;
            int value = last.val;
            last = previous;
            size -= 1;
            return value;
        } else {
            int value = last.val;
            last = null;
            size -= 1;
            return value;
        }
    }


    private static class Node {
        Node prev;
        Node next;
        int val;

        Node(Node prev, Node next, int val) {
            this.prev = prev;
            this.next = next;
            this.val = val;
        }
    }

    @Override
    public void add(int item) {
        push(item);
    }

    @Override
    public int remove(int idx) throws NoSuchElementException {
        if (size <= idx) {
            throw new NoSuchElementException();
        } else {
            if (idx == size - 1) {
                return pop();
            } else {
                Node node = first;
                for (int i = 0; i < idx; i++) {
                    node = node.next;
                }
                if (node == first) {
                    return dequeu();
                } else {
                    Node previous = node.prev;       //Берутся узлы, связанные с текующим узлом, и связываются ссылками
                    Node following = node.next;
                    following.prev = previous;
                    previous.next = following;
                    int value = node.val;
                    node = null;
                    size -= 1;
                    return value;
                }
            }
        }
    }

    @Override
    public int get(int idx) throws NoSuchElementException {
        if (size <= idx) {
            throw new NoSuchElementException();
        } else if (idx == size - 1) {
            return last.val;
        } else {
            Node next = first;
            for (int i = 0; i < idx; i++) {
                next = next.next;
            }
            return next.val;
        }
    }

    @Override
    public int size() {
        return size;
    }
}
