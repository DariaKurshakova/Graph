package graph;
import java.lang.reflect.Array;
class SimpleList<T> {
    private Object[] data;
    private int len;
    public SimpleList() {
        data = new Object[4];
        len = 0;
    }
    public void add(T item) {
        if (len == data.length) {
            Object[] newData = new Object[data.length * 2];
            System.arraycopy(data, 0, newData, 0, len);
            data = newData;
        }
        data[len++] = item;
    }
    public void add(int index, T item) {
        if (index < 0 || index > len) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + len);
        }
        if (len == data.length) {
            Object[] newData = new Object[data.length * 2];
            System.arraycopy(data, 0, newData, 0, len);
            data = newData;
        }
        System.arraycopy(data, index, data, index + 1, len - index);
        data[index] = item;
        len++;
    }
    public boolean remove(T item) {
        for (int i = 0; i < len; i++) {
            if (item == null ? data[i] == null : item.equals(data[i])) {
                System.arraycopy(data, i + 1, data, i, len - i - 1);
                data[--len] = null;
                return true;
            }
        }
        return false;
    }
    public T removeAt(int index) {
        if (index < 0 || index >= len) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + len);
        }
        @SuppressWarnings("unchecked")
        T old = (T) data[index];
        System.arraycopy(data, index + 1, data, index, len - index - 1);
        data[--len] = null;
        return old;
    }
    public boolean contains(T item) {
        for (int i = 0; i < len; i++) {
            if (item == null ? data[i] == null : item.equals(data[i])) {
                return true;
            }
        }
        return false;
    }
    public int indexOf(T item) {
        for (int i = 0; i < len; i++) {
            if (item == null ? data[i] == null : item.equals(data[i])) {
                return i;
            }
        }
        return -1;
    }
    @SuppressWarnings("unchecked")
    public T get(int idx) {
        if (idx < 0 || idx >= len) {
            throw new IndexOutOfBoundsException("Index: " + idx);
        }
        return (T) data[idx];
    }
    public int size() {
        return len;
    }
    @Override
    public String toString() {
        if (len == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < len; i++) {
            if (i > 0) sb.append(", ");
            sb.append(data[i]);
        }
        sb.append("]");
        return sb.toString();
    }
    @SuppressWarnings("unchecked")
    public T[] toArray(Class<T> clazz) {
        T[] arr = (T[]) Array.newInstance(clazz, len);
        System.arraycopy(data, 0, arr, 0, len);
        return arr;
    }
}