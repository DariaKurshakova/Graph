package graph;
class SimpleMap<K, V> {
    private SimpleList<K> keys;
    private SimpleList<V> values;
    public SimpleMap() {
        keys = new SimpleList<>();
        values = new SimpleList<>();
    }
    public void put(K key, V value) {
        int i = keys.indexOf(key);
        if (i == -1) {
            keys.add(key);
            values.add(value);
        } else {
            values.removeAt(i);
            values.add(i, value);
        }
    }
    public V get(K key) {
        int i = keys.indexOf(key);
        if (i == -1) return null;
        return values.get(i);
    }
    public SimpleList<K> keySet() {
        return keys;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < keys.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(keys.get(i)).append("=").append(values.get(i));
        }
        sb.append("}");
        return sb.toString();
    }
}