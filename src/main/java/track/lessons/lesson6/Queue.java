package track.lessons.lesson6;

/**
 * Created by bakla410 on 31.03.17.
 */
public class Queue {

    protected int end = 0;
    protected int[] array;

    public Queue(int capacity) {
        array = new int[capacity];
    }

    public void add(int item) throws IndexOutOfBoundsException {

        if (end != array.length) {
            array[end] = item;
            end += 1;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public int get() throws IndexOutOfBoundsException {

        if (end != 0) {
            int item = array[end - 1];
            end -= 1;
            return item;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public boolean isEmpty() {
        return (end == 0);
    }

    public boolean isFull() {
        return (end == array.length);
    }
}
