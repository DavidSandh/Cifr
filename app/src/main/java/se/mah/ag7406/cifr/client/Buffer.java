package se.mah.ag7406.cifr.client;

import java.util.LinkedList;

/**
 * Created by Jens on 2017-04-10.
 */

public class Buffer<T> {
    private LinkedList<T> buffer = new LinkedList<T>();

    public synchronized void put(T obj) {
        buffer.addLast(obj);

    }

    public synchronized T get() throws InterruptedException {
        while(buffer.isEmpty()) {
            wait();
        }
        return buffer.removeFirst();
    }

    public int size() {
        return buffer.size();
    }
}
