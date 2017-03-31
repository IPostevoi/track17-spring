package track.lessons.lesson6;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by bakla410 on 31.03.17.
 */
public class BlockingQueueTest {

    @Test
    public void queueTest() throws Exception {
        final int item1 = 1;
        final int item2 = 2;

        BlockingQueueClass blockingQueue = new BlockingQueueClass(7);

        Thread thread0 = new Thread(() -> blockingQueue.poll(20));
        thread0.start();
        Thread thread1 = new Thread(() -> blockingQueue.put(item1));
        thread1.start();
        Thread thread2 = new Thread(() -> blockingQueue.put(item2));
        thread2.start();
        Thread thread3 = new Thread(() -> blockingQueue.put(item2));
        thread3.start();
        Thread thread4 = new Thread(() -> blockingQueue.put(item1));
        thread4.start();
        thread0.interrupt();
        Thread thread5 = new Thread(() -> blockingQueue.offer(item2));
        thread5.start();
        Thread thread6 = new Thread(() -> System.out.println(blockingQueue.take()));
        thread6.start();

        Thread[] threads = {thread0, thread1, thread2, thread3, thread4, thread5, thread6};
        for (Thread thread: threads) {
            thread.join();
        }
    }
}
