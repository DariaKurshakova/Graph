package graph;
class SimpleQueue<T> {
    private Object[] buf;
    private int head;
    private int tail;
    private int count;
    public SimpleQueue() {
        buf = new Object[8];
        head = 0;
        tail = 0;
        count = 0;
    }
    public void enqueue(T item) {
        if (count == buf.length) {
            Object[] newBuf = new Object[buf.length * 2];
            System.arraycopy(buf, head, newBuf, 0, buf.length - head);
            if (head > 0) {
                System.arraycopy(buf, 0, newBuf, buf.length - head, tail);
            }
            head = 0;
            tail = count;
            buf = newBuf;
        }
        buf[tail] = item;
        tail = (tail + 1) % buf.length;
        count++;
    }
    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (count == 0) {
            throw new RuntimeException("Queue is empty");
        }
        T item = (T) buf[head];
        buf[head] = null;
        head = (head + 1) % buf.length;
        count--;
        return item;
    }
    public boolean isEmpty() {
        return count == 0;
    }
}