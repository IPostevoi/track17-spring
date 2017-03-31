package track.lessons.lesson6;

import javax.xml.bind.ValidationEventLocator;
import java.util.concurrent.TimeUnit;


/**
 * Created by bakla410 on 29.03.17.
 */


public class BlockingQueueClass extends Queue implements BlockingQueue<Integer> {

    private Object obj = array;

    public BlockingQueueClass(int capacity) {
        super(capacity);
    }

    @Override
    public void put(Integer item) throws IndexOutOfBoundsException {
        try {
            while (isFull()) {
                System.out.println("Thread" + Thread.currentThread().getName() +
                        " is waiting for object ");
                TimeUnit.SECONDS.sleep(1);
            }
            synchronized (obj) {
                add(item);
            }
            System.out.println("Thread" + Thread.currentThread().getName() +
                    " added item ");
        } catch (InterruptedException e) {
            System.out.println("Thread" + Thread.currentThread().getName() +
                    " stopped");
            return;
        }
    }

    @Override
    public Integer take() throws IndexOutOfBoundsException {


        try {
            while (isEmpty()) {
                System.out.println("Thread" + Thread.currentThread().getName() +
                        " is waiting for object ");
                TimeUnit.SECONDS.sleep(1);
            }
            synchronized (obj) {
                System.out.println("Thread" + Thread.currentThread().getName() +
                        " took item ");
                return get();
            }
        } catch (InterruptedException e) {
            System.out.println("Thread" + Thread.currentThread().getName() +
                    " stopped");
            return null;
        }

    }

    @Override
    public Integer poll(int timeout) {


        try {
            if (isEmpty()) {
                System.out.println("Thread" + Thread.currentThread().getName() +
                        " is waiting " + timeout + " seconds for item with poll");
                TimeUnit.SECONDS.sleep(timeout);
            }
            if (!isEmpty()) {
                synchronized (obj) {
                    System.out.println("Thread" + Thread.currentThread().getName() +
                            " took item with poll");
                    return take();
                }
            } else {
                System.out.println("Thread" + Thread.currentThread().getName() +
                        " failed to take item with poll");
                return null;
            }
        } catch (InterruptedException e) {
            System.out.println("Thread" + Thread.currentThread().getName() +
                    " stopped");
            return null;
        }

    }

    @Override
    public boolean offer(Integer item) {


        if (!isFull()) {
            synchronized (obj) {
                System.out.println("Thread" + Thread.currentThread().getName() +
                        " added item with offer");
                add(item);
            }
            return true;
        } else {
            return false;
        }

    }
}

