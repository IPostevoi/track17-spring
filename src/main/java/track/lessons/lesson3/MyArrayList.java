package track.lessons.lesson3;

import java.lang.*;

import java.util.NoSuchElementException;

/**
 * Должен наследовать List
 *
 * Должен иметь 2 конструктора
 * - без аргументов - создает внутренний массив дефолтного размера на ваш выбор
 * - с аргументом - начальный размер массива
 */
public class MyArrayList extends List {

    private int size;

    private int[] list;

    private static int DAFAULT_CAPACITY = 0;

    public MyArrayList() {
        list = new int[DAFAULT_CAPACITY];
    }

    public MyArrayList(int capacity) {
        list = new int[capacity];
    }

    @Override
    public void add(int item) {
        if (list.length <= size) {
            int[] oldList = list;
            list = new int[oldList.length  * 2 + 1];
            System.arraycopy(oldList, 0, list, 0, oldList.length);
            list[size] = item;
            size += 1;
        } else {
            list[size] = item;
            size += 1;
        }
    }

    @Override
    public int remove(int idx) throws NoSuchElementException {
        if (idx < list.length) {
            size -= 1;
            int temp = list[idx];
            System.arraycopy(list, idx + 1, list, idx, list.length - idx - 1);
            return temp;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public int get(int idx) throws NoSuchElementException {
        if (idx < list.length) {
            return list[idx];
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public int size() {
        return size;
    }
}
