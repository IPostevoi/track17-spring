package track.lessons.lesson3;

import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class MyLinkedListTest {

    @Test(expected = NoSuchElementException.class)
    public void emptyList() throws Exception {
        List list = new MyLinkedList();
        Assert.assertTrue(list.size() == 0);
        list.get(0);
    }

    @Test
    public void listAdd() throws Exception {
        List list = new MyLinkedList();
        list.add(1);

        Assert.assertTrue(list.size() == 1);
    }

    @Test
    public void listAddRemove() throws Exception {
        List list = new MyLinkedList();
        list.add(1);
        list.add(2);
        list.add(3);

        Assert.assertEquals(3, list.size());

        Assert.assertEquals(1, list.get(0));
        Assert.assertEquals(2, list.get(1));
        Assert.assertEquals(3, list.get(2));

        list.remove(1);
        Assert.assertEquals(3, list.get(1));
        Assert.assertEquals(1, list.get(0));

        list.remove(1);
        list.remove(0);

        Assert.assertTrue(list.size() == 0);

        list.add(1);
        list.add(2);
        list.remove(0);
        Assert.assertEquals(2, list.remove(0));
    }

    @Test
    public void listRemove() throws Exception {
        List list = new MyLinkedList();
        list.add(1);
        list.remove(0);

        Assert.assertTrue(list.size() == 0);
    }

    @Test
    public void stackPushPop() throws  Exception {
        MyLinkedList list = new MyLinkedList();
        list.push(1);
        list.push(2);
        list.push(3);

        Assert.assertEquals(3, list.get(2));
        Assert.assertTrue(list.size() == 3);

        list.pop();
        list.pop();
        Assert.assertTrue(list.size() == 1);
        Assert.assertEquals(1, list.pop());
    }

    @Test
    public void queueEnqDeq() throws  Exception {
        MyLinkedList list = new MyLinkedList();
        list.enqueue(1);
        list.enqueue(2);
        list.enqueue(3);

        Assert.assertEquals(3, list.get(2));
        Assert.assertTrue(list.size() == 3);

        list.dequeu();
        list.dequeu();
        Assert.assertTrue(list.size() == 1);
        Assert.assertEquals(3, list.dequeu());
    }
}