import java.util.*;

class City {
    int id;
    String name;

    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class Edge {
    int source;
    int destination;
    int distance;

    public Edge(int source, int destination, int distance) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }
}

public class DijkstraAlgorithm {
    private List<List<Edge>> adjacencyList;
    private int numCities;
    private City[] cities;

    public DijkstraAlgorithm(int numCities) {
        this.numCities = numCities;
        adjacencyList = new ArrayList<>();
        cities = new City[numCities];

        for (int i = 0; i < numCities; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public void addCity(int id, String name) {
        cities[id] = new City(id, name);
    }

    public void addEdge(int source, int destination, int distance) {
        Edge edge = new Edge(source, destination, distance);
        adjacencyList.get(source).add(edge);
    }

    public void dijkstra(int startCity) {
        int[] distances = new int[numCities];
        boolean[] visited = new boolean[numCities];
        int[] previous = new int[numCities];

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(visited, false);
        Arrays.fill(previous, -1);

        distances[startCity] = 0;

        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.distance));
        pq.offer(new Edge(startCity, startCity, 0));

        while (!pq.isEmpty()) {
            Edge currentEdge = pq.poll();
            int currentCity = currentEdge.destination;

            if (visited[currentCity])
                continue;

            visited[currentCity] = true;

            for (Edge neighbor : adjacencyList.get(currentCity)) {
                int neighborCity = neighbor.destination;
                int newDistance = distances[currentCity] + neighbor.distance;

                if (newDistance < distances[neighborCity]) {
                    distances[neighborCity] = newDistance;
                    previous[neighborCity] = currentCity;
                    pq.offer(new Edge(currentCity, neighborCity, newDistance));
                }
            }
        }

        for (int i = 0; i < numCities; i++) {
            if (i != startCity) {
                System.out.println("City: " + cities[i].name + ", Distance: " + distances[i]);
            }
        }
    }

    public static void main(String[] args) {
        DijkstraAlgorithm graph = new DijkstraAlgorithm(5);

        graph.addCity(0, "Wrocław");
        graph.addCity(1, "Oława");
        graph.addCity(2, "Brzeg");
        graph.addCity(3, "Nysa");
        graph.addCity(4, "Opole");

        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 3, 30);
        graph.addEdge(0, 4, 100);
        graph.addEdge(1, 2, 50);
        graph.addEdge(2, 4, 10);
        graph.addEdge(3, 2, 20);
        graph.addEdge(3, 4, 60);

        int startCity = 1;
        System.out.println("Starting city: " + graph.cities[startCity].name);
        graph.dijkstra(startCity);
    }
}
