package graph;
import java.io.*;
import java.lang.reflect.Array;
class Graph<V> {
    private boolean directed;
    private SimpleList<V> vertex;
    private SimpleList<SimpleList<Edge<V>>> adj;
    public Graph(boolean directed) {
        this.directed = directed;
        this.vertex = new SimpleList<>();
        this.adj = new SimpleList<>();
    }
    public void addVertex(V v) {
        if (v == null) {
            throw new IllegalArgumentException("Vertex cannot be null");
        }
        if (vertex.contains(v)) {
            return;
        }
        vertex.add(v);
        adj.add(new SimpleList<>());
    }
    private int indexOf(V v) {
        for (int i = 0; i < vertex.size(); i++) {
            if (vertex.get(i).equals(v)) {
                return i;
            }
        }
        return -1;
    }
    private boolean hasEdge(V from, V to) {
        int i = indexOf(from);
        if (i == -1) return false;
        SimpleList<Edge<V>> edges = adj.get(i);
        for (int k = 0; k < edges.size(); k++) {
            if (edges.get(k).to.equals(to)) {
                return true;
            }
        }
        return false;
    }
    public void addEdge(V from, V to, int weight) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Vertices cannot be null");
        }
        int i = indexOf(from);
        int j = indexOf(to);
        if (i == -1 || j == -1) {
            throw new IllegalArgumentException("Both vertices must exist");
        }
        if (hasEdge(from, to)) {
            return;
        }
        adj.get(i).add(new Edge<>(to, weight));
        if (!directed) {
            if (!hasEdge(to, from)) {
                adj.get(j).add(new Edge<>(from, weight));
            }
        }
    }
    public void removeVertex(V v) {
        int idx = indexOf(v);
        if (idx == -1) return;
        for (int i = 0; i < adj.size(); i++) {
            SimpleList<Edge<V>> list = adj.get(i);
            int k = 0;
            while (k < list.size()) {
                if (list.get(k).to.equals(v)) {
                    list.remove(list.get(k));
                } else {
                    k++;
                }
            }
        }
        SimpleList<V> newV = new SimpleList<>();
        SimpleList<SimpleList<Edge<V>>> newAdj = new SimpleList<>();
        for (int i = 0; i < vertex.size(); i++) {
            if (i != idx) {
                newV.add(vertex.get(i));
                newAdj.add(adj.get(i));
            }
        }
        vertex = newV;
        adj = newAdj;
    }
    public void removeEdge(V from, V to) {
        int i = indexOf(from);
        if (i == -1) return;

        SimpleList<Edge<V>> list = adj.get(i);
        for (int k = 0; k < list.size(); k++) {
            if (list.get(k).to.equals(to)) {
                list.remove(list.get(k));
                break;
            }
        }
        if (!directed) {
            int j = indexOf(to);
            if (j != -1) {
                SimpleList<Edge<V>> rev = adj.get(j);
                for (int k = 0; k < rev.size(); k++) {
                    if (rev.get(k).to.equals(from)) {
                        rev.remove(rev.get(k));
                        break;
                    }
                }
            }
        }
    }
    public SimpleList<V> getAdjacent(V v) {
        int i = indexOf(v);
        if (i == -1) {
            throw new IllegalArgumentException("Vertex not found: " + v);
        }
        SimpleList<V> res = new SimpleList<>();
        SimpleList<Edge<V>> edges = adj.get(i);
        for (int k = 0; k < edges.size(); k++) {
            res.add(edges.get(k).to);
        }
        return res;
    }
    public void dfs(V start) {
        int i = indexOf(start);
        if (i == -1) {
            throw new IllegalArgumentException("Start vertex not found: " + start);
        }
        boolean[] visited = new boolean[vertex.size()];
        dfsRec(i, visited);
    }
    private void dfsRec(int i, boolean[] visited) {
        visited[i] = true;
        System.out.println(vertex.get(i));
        SimpleList<Edge<V>> edges = adj.get(i);
        for (int k = 0; k < edges.size(); k++) {
            V n = edges.get(k).to;
            int j = indexOf(n);
            if (!visited[j]) {
                dfsRec(j, visited);
            }
        }
    }
    public void bfs(V start) {
        int i = indexOf(start);
        if (i == -1) {
            throw new IllegalArgumentException("Start vertex not found: " + start);
        }
        boolean[] visited = new boolean[vertex.size()];
        SimpleQueue<Integer> q = new SimpleQueue<>();
        q.enqueue(i);
        visited[i] = true;
        while (!q.isEmpty()) {
            int cur = q.dequeue();
            System.out.println(vertex.get(cur));
            SimpleList<Edge<V>> edges = adj.get(cur);
            for (int k = 0; k < edges.size(); k++) {
                V n = edges.get(k).to;
                int j = indexOf(n);
                if (!visited[j]) {
                    visited[j] = true;
                    q.enqueue(j);
                }
            }
        }
    }
    public boolean hasCycle() {
        boolean[] vis = new boolean[vertex.size()];
        boolean[] rec = new boolean[vertex.size()];
        for (int i = 0; i < vertex.size(); i++) {
            if (!vis[i]) {
                if (hasCycleRec(i, vis, rec)) return true;
            }
        }
        return false;
    }
    private boolean hasCycleRec(int i, boolean[] vis, boolean[] rec) {
        vis[i] = true;
        rec[i] = true;
        SimpleList<Edge<V>> edges = adj.get(i);
        for (int k = 0; k < edges.size(); k++) {
            V n = edges.get(k).to;
            int j = indexOf(n);
            if (!vis[j]) {
                if (hasCycleRec(j, vis, rec)) return true;
            } else if (rec[j]) {
                return true;
            }
        }
        rec[i] = false;
        return false;
    }
    public SimpleMap<V, Integer> shortestPath(V start) {
        int src = indexOf(start);
        if (src == -1) {
            throw new IllegalArgumentException("Start vertex not found");
        }
        int n = vertex.size();
        int[] dist = new int[n];
        boolean[] settled = new boolean[n];
        for (int i = 0; i < n; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[src] = 0;
        for (int count = 0; count < n - 1; count++) {
            int u = -1;
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (!settled[i] && dist[i] < min) {
                    min = dist[i];
                    u = i;
                }
            }
            if (u == -1) break;
            settled[u] = true;
            SimpleList<Edge<V>> edges = adj.get(u);
            for (int k = 0; k < edges.size(); k++) {
                Edge<V> e = edges.get(k);
                int vIdx = indexOf(e.to);
                if (!settled[vIdx] && dist[u] != Integer.MAX_VALUE) {
                    int newDist = dist[u] + e.weight;
                    if (newDist < dist[vIdx]) {
                        dist[vIdx] = newDist;
                    }
                }
            }
        }
        SimpleMap<V, Integer> res = new SimpleMap<>();
        for (int i = 0; i < n; i++) {
            int d = dist[i] == Integer.MAX_VALUE ? -1 : dist[i];
            res.put(vertex.get(i), d);
        }
        return res;
    }
    public void saveToFile(String file) throws IOException {
        try (PrintWriter w = new PrintWriter(new FileWriter(file))) {
            w.println(directed);
            w.println(vertex.size());
            for (int i = 0; i < vertex.size(); i++) {
                w.println(vertex.get(i).toString());
                SimpleList<Edge<V>> edges = adj.get(i);
                w.println(edges.size());
                for (int j = 0; j < edges.size(); j++) {
                    Edge<V> e = edges.get(j);
                    w.println(e.to + "|" + e.weight);
                }
            }
        } catch (IOException ex) {
            throw new IOException("Save failed: " + ex.getMessage(), ex);
        }
    }
    public void loadFromFile(String file) throws IOException {
        try (BufferedReader r = new BufferedReader(new FileReader(file))) {
            boolean fileDirected = Boolean.parseBoolean(r.readLine());
            if (fileDirected != directed) {
                throw new IllegalArgumentException("Graph direction mismatch");
            }
            int n = Integer.parseInt(r.readLine());
            SimpleList<V> tempVertices = new SimpleList<>();
            SimpleList<V> tempFrom = new SimpleList<>();
            SimpleList<V> tempTo = new SimpleList<>();
            SimpleList<Integer> tempWeights = new SimpleList<>();
            for (int i = 0; i < n; i++) {
                String vStr = r.readLine();
                @SuppressWarnings("unchecked")
                V v = (V) vStr;
                tempVertices.add(v);
                int m = Integer.parseInt(r.readLine());
                for (int j = 0; j < m; j++) {
                    String[] parts = r.readLine().split("\\|", 2);
                    @SuppressWarnings("unchecked")
                    V toV = (V) parts[0];
                    int w = Integer.parseInt(parts[1]);
                    tempFrom.add(v);
                    tempTo.add(toV);
                    tempWeights.add(w);
                }
            }
            vertex = new SimpleList<>();
            adj = new SimpleList<>();
            for (int i = 0; i < tempVertices.size(); i++) {
                addVertex(tempVertices.get(i));
            }
            for (int i = 0; i < tempFrom.size(); i++) {
                addEdge(tempFrom.get(i), tempTo.get(i), tempWeights.get(i));
            }
        } catch (IOException | NumberFormatException ex) {
            throw new IOException("Load failed: " + ex.getMessage(), ex);
        }
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(directed ? "Directed" : "Undirected").append(" graph:\n");
        for (int i = 0; i < vertex.size(); i++) {
            sb.append(vertex.get(i)).append(" -> ").append(getAdjacent(vertex.get(i))).append("\n");
        }
        return sb.toString();
    }
}