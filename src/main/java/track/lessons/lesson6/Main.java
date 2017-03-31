package track.lessons.lesson6;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by bakla410 on 29.03.17.
 */

public class Main {

    static Logger logger = LoggerFactory.getLogger(Main.class);

    public static class Queue {

        private static int DEFAULT_CAPACITY = 0;
        private int end = 0;
        private int[] array;

        public Queue(int capacity) {
            array = new int[capacity];
        }

        public Queue() {
            array = new int[DEFAULT_CAPACITY];
        }

        public void add(int item) throws IndexOutOfBoundsException {
            if (end != array.length) {
                array[end] = item;
                end += 1;
            } else {
                throw new IndexOutOfBoundsException();
            }
        }

        public int take() throws IndexOutOfBoundsException {
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

    public static class BlockingQueueImpement implements BlockingQueue<Integer> {

         Queue queue;

        public BlockingQueueImpement(Queue queue) {
            this.queue = queue;
        }

        @Override
        public void put(Integer item) throws  IndexOutOfBoundsException {
            while (queue.isFull()) {
                try {
                    System.out.println("Thread" + Thread.currentThread().getName() +
                            " is waiting for object ");

                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e){ }
            }
            try {
                synchronized (queue) {
                    queue.add(item);
                }
                System.out.println("Thread" + Thread.currentThread().getName() +
                        " added item ");
            } catch (IndexOutOfBoundsException e) {
                throw new IndexOutOfBoundsException();
            }
        }

        @Override
        public Integer take() throws IndexOutOfBoundsException {
            while (queue.isEmpty()) {
                try {
                    System.out.println("Thread" + Thread.currentThread().getName() +
                            " is waiting for object ");

                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e){ }
            }
            System.out.println("Thread" + Thread.currentThread().getName() +
                    " took item ");
            try {
                return queue.take();
            } catch (IndexOutOfBoundsException e) {
                throw new IndexOutOfBoundsException();
            }
        }

        @Override
        public Integer poll() throws IndexOutOfBoundsException {
            if (queue.isEmpty()) {
                return 0;
            } else {
                System.out.println("Thread" + Thread.currentThread().getName() +
                        " took item ");
                try {
                    return queue.take();
                } catch (IndexOutOfBoundsException e) {
                    throw new IndexOutOfBoundsException();
                }
            }
        }

        @Override
        public boolean offer(Integer item) {
            return false;
        }
    }

    public static void main(String[] args) throws  Exception {

        int item1 = 1;
        int item2 = 2;

        Queue queue = new Queue(4);

        BlockingQueueImpement blockingQueue = new BlockingQueueImpement(queue);

        Thread thread1 = new Thread(() -> blockingQueue.put(item1));
        Thread thread2 = new Thread(() -> blockingQueue.put(item2));
        Thread thread3 = new Thread(() -> blockingQueue.put(item2));
        Thread thread4 = new Thread(() -> blockingQueue.put(item2));
        Thread thread5 = new Thread(() -> blockingQueue.put(item2));
        Thread thread6 = new Thread(() -> blockingQueue.take());





        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();
        thread6.join();

        System.out.println(queue.take());
    }
}
