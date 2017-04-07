package track.lessons.lesson6;

import java.util.concurrent.TimeUnit;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by bakla410 on 07.04.17.
 */
public class BlockingQueueInst<T> implements BlockingQueue<T> {

    List<T> list = new LinkedList<>();
    int limit;

    public BlockingQueueInst(int limit) {

        this.limit = limit;
    }

    public synchronized void put(T item) {
        while (list.size() == limit) {
            try {
                wait();
            } catch (InterruptedException ex) {
                notify();
            }
        }
        notify();
        list.add(item);
    }

    public synchronized T take() {
        while (list.size() == 0) {
            try {
                wait();
            } catch (InterruptedException ex) {
                notify();
            }
        }
        notify();
        return list.remove(0);
    }

    public synchronized T poll(int timeout) {
        if (list.size() != 0) {
            notify();
            return list.remove(0);
        } else {
            try {
                TimeUnit.SECONDS.sleep(timeout);
            } catch (InterruptedException ex) {

            }

        }
        if (list.size() != 0) {
            notify();
            return list.remove(0);
        } else {
            return null;
        }
    }

    public synchronized boolean offer(T item) {
        if (list.size() == limit) {
            return false;
        } else {
            list.add(item);
            notify();
            return true;
        }
    }





}
