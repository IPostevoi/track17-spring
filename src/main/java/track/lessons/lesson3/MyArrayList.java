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

    private int iter = 0;

    private int[] mass;

    public MyArrayList() {
        mass = new int[0];
    }

    public MyArrayList(int capacity) {
        mass = new int[capacity];
    }

    @Override
    public void add(int item) {
        if (mass.length <= iter) {
            int[] tempMass = mass;
            mass = new int[tempMass.length  * 2 + 1];
            System.arraycopy(tempMass, 0, mass, 0, tempMass.length);
            mass[iter] = item;
            iter += 1;
        } else {
            mass[iter] = item;
            iter += 1;
        }
    }

    @Override
    public int remove(int idx) throws NoSuchElementException {
        if (idx < mass.length) {
            iter -= 1;
            int temp = mass[idx];
            System.arraycopy(mass, idx + 1, mass, idx, mass.length - idx - 1);
            return temp;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public int get(int idx) throws NoSuchElementException {
        if (idx < mass.length) {
            return mass[idx];
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public int size() {
        return iter;
    }
}
