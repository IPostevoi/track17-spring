package track.lessons.lesson6;

/**
 * Created by bakla410 on 30.03.17.
 */

interface BlockingQueue<T> {
    void put(T item); // положить элемент, если очередь полная, то блокируемся , пока не появится свободное место

    T take(); // взять элемент из очереди, если очередь пустая, то ждем, пока в очереди появится элемент

    T poll(int timeout); // аналог take(), ждем timeout, а в случае полной очереди сразу возвращаем null

    boolean offer(T item); // Без ожидания добавляем элемент, false - если сразу не удалось
}