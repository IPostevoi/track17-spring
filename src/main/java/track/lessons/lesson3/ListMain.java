package track.lessons.lesson3;

/**
 *
 */
public class ListMain {

    public static void main(String[] args) {
        List list = new MyArrayList();
        list.add(1);
        list.remove(0);
        System.out.println(list.size());

    }
}
