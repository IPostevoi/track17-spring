package track.lessons.lesson3;

import java.util.NoSuchElementException;

/**
 * Должен наследовать List
 * Односвязный список
 */

public class MyLinkedList extends List implements Queue {

    /**
     * private - используется для сокрытия этого класса от других.
     * Класс доступен только изнутри того, где он объявлен
     * <p>
     * static - позволяет использовать Node без создания экземпляра внешнего класса
     */
    @Override
    public void enqueue(int value){}

    @Override
    public int dequeu(){
        return 0;
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
    }

    @Override
    int remove(int idx) throws NoSuchElementException {
        return 0;
    }

    @Override
    int get(int idx) throws NoSuchElementException {
        return 0;
    }

    @Override
    int size() {
        return 0;
    }
}
