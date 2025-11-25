package graph;
class Edge<V> {
    final V to;
    final int weight;

    public Edge(V to, int weight) {
        this.to = to;
        this.weight = weight;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge<?> that = (Edge<?>) o;
        return to.equals(that.to);
    }
    @Override
    public int hashCode() {
        return to.hashCode();
    }
    @Override
    public String toString() {
        return to + "(" + weight + ")";
    }
}