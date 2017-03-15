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

    int iter = 0;
    Node last = null;
    Node first = null;


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
        iter -= 1;
        return value;
    }

    @Override
    public void push(int value) {
        Node node = new Node(last, null, value);
        last.next = node;
        last = node;
        iter += 1;
    }

    @Override
    public int pop() {
        Node previous = last.prev;
        previous.next = null;
        int value = last.val;
        last = null;
        iter -= 1;
        return  value;
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
    void add(int item) {
        if (iter == 0) {
            first = new Node(null, null, item );
            last = first;
            iter += 1;
        } else {
            push(item);
        }
    }

    @Override
    int remove(int idx) throws NoSuchElementException {
        if (iter <= idx) {
            throw new NoSuchElementException();
        } else {
            if (idx == 0) {
                return dequeu();
            } else if (idx == iter - 1) {
                return pop();
            } else {
                Node node = first;
                for (int i = 0; i < idx; i++) {
                    node = node.next;
                }
                Node previous = node.prev;
                Node following = node.next;
                following.prev = previous;
                previous.next = following;
                int value = node.val;
                node = null;
                iter -= 1;
                return value;
            }
        }
    }

    @Override
    int get(int idx) throws NoSuchElementException {
        if (iter <= idx) {
            throw new NoSuchElementException();
        } else {
            Node next = first;
            for (int i = 0; i < idx; i++) {
                next = next.next;
            }
            return next.val;
        }
    }

    @Override
    int size() {
        return iter;
    }
}
