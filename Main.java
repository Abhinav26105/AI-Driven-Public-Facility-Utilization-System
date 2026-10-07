import java.util.*;

class Facility {
    int id, capacity, occupancy;
    String name, zone, amenities, hours;

    Facility(int id, String name, String zone, int capacity,
             int occupancy, String amenities, String hours) {
        this.id = id;
        this.name = name;
        this.zone = zone;
        this.capacity = capacity;
        this.occupancy = occupancy;
        this.amenities = amenities;
        this.hours = hours;
    }

    int available() {
        return capacity - occupancy;
    }

    public String toString() {
        return "ID: " + id +
               " | " + name +
               " | Zone: " + zone +
               " | Capacity: " + capacity +
               " | Occupancy: " + occupancy +
               " | Available: " + available() +
               " | Amenities: " + amenities +
               " | Hours: " + hours;
    }
}

class Edge {
    int to, distance;

    Edge(int to, int distance) {
        this.to = to;
        this.distance = distance;
    }
}

class Graph {
    ArrayList<ArrayList<Edge>> list;

    Graph(int n) {
        list = new ArrayList<>();

        for (int i = 0; i < n; i++)
            list.add(new ArrayList<>());
    }

    void addEdge(int a, int b, int distance) {
        list.get(a).add(new Edge(b, distance));
        list.get(b).add(new Edge(a, distance));
    }

    void display() {
        System.out.println("\n--- Public Facility Connections ---");

        for (int i = 0; i < list.size(); i++) {
            System.out.print("Facility " + i + " -> ");

            for (Edge e : list.get(i))
                System.out.print(
                    e.to + " (" + e.distance + " km) "
                );

            System.out.println();
        }
    }
}

/* ---------- FACILITY RECORD TREE ---------- */

class TreeNode {
    Facility data;
    TreeNode left, right;

    TreeNode(Facility data) {
        this.data = data;
    }
}

class FacilityTree {
    TreeNode root;

    TreeNode insert(TreeNode node, Facility f) {
        if (node == null)
            return new TreeNode(f);

        if (f.id < node.data.id)
            node.left = insert(node.left, f);
        else
            node.right = insert(node.right, f);

        return node;
    }

    void insert(Facility f) {
        root = insert(root, f);
    }

    void inorder(TreeNode node) {
        if (node == null)
            return;

        inorder(node.left);
        System.out.println(node.data);
        inorder(node.right);
    }

    void preorder(TreeNode node) {
        if (node == null)
            return;

        System.out.println(node.data);
        preorder(node.left);
        preorder(node.right);
    }

    void postorder(TreeNode node) {
        if (node == null)
            return;

        postorder(node.left);
        postorder(node.right);
        System.out.println(node.data);
    }
}

/* ---------- AVL FACILITY RECORDS ---------- */

class AVLNode {
    Facility data;
    AVLNode left, right;
    int height = 1;

    AVLNode(Facility data) {
        this.data = data;
    }
}

class FacilityAVL {
    AVLNode root;

    int height(AVLNode n) {
        return n == null ? 0 : n.height;
    }

    int balance(AVLNode n) {
        return n == null ? 0 :
            height(n.left) - height(n.right);
    }

    AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode t = x.right;

        x.right = y;
        y.left = t;

        y.height =
            Math.max(height(y.left), height(y.right)) + 1;

        x.height =
            Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode t = y.left;

        y.left = x;
        x.right = t;

        x.height =
            Math.max(height(x.left), height(x.right)) + 1;

        y.height =
            Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    AVLNode insert(AVLNode node, Facility f) {
        if (node == null)
            return new AVLNode(f);

        if (f.id < node.data.id)
            node.left = insert(node.left, f);
        else if (f.id > node.data.id)
            node.right = insert(node.right, f);
        else
            return node;

        node.height =
            Math.max(height(node.left), height(node.right)) + 1;

        int b = balance(node);

        if (b > 1 && f.id < node.left.data.id)
            return rightRotate(node);

        if (b < -1 && f.id > node.right.data.id)
            return leftRotate(node);

        if (b > 1 && f.id > node.left.data.id) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (b < -1 && f.id < node.right.data.id) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    void insert(Facility f) {
        root = insert(root, f);
    }

    void display(AVLNode node) {
        if (node == null)
            return;

        display(node.left);
        System.out.println(node.data);
        display(node.right);
    }
}

/* ---------- THREADED FACILITY RECORDS ---------- */

class ThreadNode {
    Facility data;
    ThreadNode left, right;
    boolean thread;

    ThreadNode(Facility data) {
        this.data = data;
    }
}

class ThreadedTree {
    ThreadNode root;

    ThreadNode insert(ThreadNode node, Facility f) {
        if (node == null)
            return new ThreadNode(f);

        if (f.id < node.data.id)
            node.left = insert(node.left, f);
        else if (f.id > node.data.id)
            node.right = insert(node.right, f);

        return node;
    }

    void insert(Facility f) {
        root = insert(root, f);
    }

    void makeThreads() {
        ThreadNode[] previous = new ThreadNode[1];
        previous[0] = null;
        makeThreads(root, previous);
    }

    void makeThreads(ThreadNode node, ThreadNode[] previous) {
        if (node == null)
            return;

        makeThreads(node.left, previous);

        if (previous[0] != null &&
            previous[0].right == null) {

            previous[0].right = node;
            previous[0].thread = true;
        }

        previous[0] = node;

        if (node.right != null && !node.thread)
            makeThreads(node.right, previous);
    }

    void display() {
        ThreadNode current = root;

        while (current != null && current.left != null)
            current = current.left;

        System.out.println(
            "\n--- Facility Record Browsing ---"
        );

        while (current != null) {
            System.out.println(current.data);

            if (current.thread)
                current = current.right;
            else {
                current = current.right;

                while (current != null &&
                       current.left != null)
                    current = current.left;
            }
        }
    }
}

/* ---------- MIN HEAP FOR ROUTES ---------- */

class MinHeap {
    PriorityQueue<int[]> queue =
        new PriorityQueue<>(
            (a, b) -> Integer.compare(a[1], b[1])
        );

    void add(int node, int distance) {
        queue.add(new int[]{node, distance});
    }

    int[] remove() {
        return queue.poll();
    }

    boolean empty() {
        return queue.isEmpty();
    }
}

/* ---------- DISJOINT SET ---------- */

class DSU {
    int[] parent;

    DSU(int n) {
        parent = new int[n];

        for (int i = 0; i < n; i++)
            parent[i] = i;
    }

    int find(int x) {
        if (parent[x] != x)
            parent[x] = find(parent[x]);

        return parent[x];
    }

    void union(int a, int b) {
        parent[find(a)] = find(b);
    }
}

/* ---------- MAIN SYSTEM ---------- */

public class Main {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Facility> facilities =
        new ArrayList<>();

    static HashMap<Integer, Facility> records =
        new HashMap<>();

    static FacilityTree tree =
        new FacilityTree();

    static FacilityAVL avl =
        new FacilityAVL();

    static ThreadedTree threaded =
        new ThreadedTree();

    static Graph graph;

    /* ---------- ADD FACILITY ---------- */

    static void addFacility() {

        System.out.print("Enter Facility ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (records.containsKey(id)) {
            System.out.println(
                "Facility already exists."
            );
            return;
        }

        System.out.print("Enter Facility Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Zone: ");
        String zone = sc.nextLine();

        System.out.print("Enter Capacity: ");
        int capacity = sc.nextInt();

        System.out.print("Enter Current Occupancy: ");
        int occupancy = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Amenities: ");
        String amenities = sc.nextLine();

        System.out.print("Enter Operating Hours: ");
        String hours = sc.nextLine();

        Facility f = new Facility(
            id, name, zone, capacity,
            occupancy, amenities, hours
        );

        facilities.add(f);
        records.put(id, f);

        tree.insert(f);
        avl.insert(f);
        threaded.insert(f);

        System.out.println(
            "Public facility added successfully."
        );
    }

    /* ---------- VIEW FACILITIES ---------- */

    static void viewFacilities() {

        System.out.println(
            "\n--- Available Public Facilities ---"
        );

        for (Facility f : facilities)
            System.out.println(f);
    }

    /* ---------- SEARCH SERVICE ---------- */

    static void searchFacility() {

        System.out.print("Enter Facility ID: ");
        int id = sc.nextInt();

        Facility f = records.get(id);

        if (f == null)
            System.out.println(
                "Facility not found."
            );
        else {
            System.out.println(
                "\n--- Facility Details ---"
            );
            System.out.println(f);
        }
    }

    /* ---------- RECORD BROWSING ---------- */

    static void browseRecords() {

        System.out.println("\n1. ID-wise Facility List");
        System.out.println("2. Facility Hierarchy");
        System.out.println("3. Facility Record Browsing");

        System.out.print("Enter choice: ");
        int ch = sc.nextInt();

        if (ch == 1) {
            System.out.println(
                "\n--- ID-wise Facility List ---"
            );
            tree.inorder(tree.root);

        } else if (ch == 2) {
            System.out.println(
                "\n--- Facility Hierarchy ---"
            );
            tree.preorder(tree.root);

        } else if (ch == 3) {
            threaded.makeThreads();
            threaded.display();
        }
    }

    /* ---------- NEARBY FACILITY SEARCH ---------- */

    static void nearbyFacilities() {

        System.out.print(
            "Enter starting facility ID: "
        );

        int start = sc.nextInt();

        boolean[] visited =
            new boolean[graph.list.size()];

        Queue<Integer> queue =
            new LinkedList<>();

        queue.add(start);
        visited[start] = true;

        System.out.println(
            "\n--- Nearby Public Facilities ---"
        );

        while (!queue.isEmpty()) {

            int u = queue.poll();

            System.out.println(
                "Facility " + u
            );

            for (Edge e : graph.list.get(u)) {

                if (!visited[e.to]) {
                    visited[e.to] = true;
                    queue.add(e.to);
                }
            }
        }
    }

    /* ---------- ZONE EXPLORATION ---------- */

    static void exploreZone(
        int u, boolean[] visited
    ) {

        visited[u] = true;

        System.out.println(
            "Facility " + u
        );

        for (Edge e : graph.list.get(u)) {

            if (!visited[e.to])
                exploreZone(e.to, visited);
        }
    }

    static void exploreFacilities() {

        System.out.print(
            "Enter facility to explore zone: "
        );

        int start = sc.nextInt();

        boolean[] visited =
            new boolean[graph.list.size()];

        System.out.println(
            "\n--- Facility Zone Exploration ---"
        );

        exploreZone(start, visited);
    }

    /* ---------- SHORTEST ROUTE ---------- */

    static void shortestRoute() {

        System.out.print(
            "Enter starting facility: "
        );

        int source = sc.nextInt();

        int[] distance =
            new int[graph.list.size()];

        Arrays.fill(
            distance,
            Integer.MAX_VALUE
        );

        distance[source] = 0;

        MinHeap heap = new MinHeap();

        heap.add(source, 0);

        while (!heap.empty()) {

            int[] current = heap.remove();

            int u = current[0];

            for (Edge e : graph.list.get(u)) {

                if (distance[u] + e.distance
                    < distance[e.to]) {

                    distance[e.to] =
                        distance[u] + e.distance;

                    heap.add(
                        e.to,
                        distance[e.to]
                    );
                }
            }
        }

        System.out.println(
            "\n--- Shortest Travel Routes ---"
        );

        for (int i = 0;
             i < distance.length; i++) {

            System.out.println(
                "Facility " + source +
                " to Facility " + i +
                " = " + distance[i] + " km"
            );
        }
    }

    /* ---------- MINIMUM CONNECTION ---------- */

    static void minimumNetwork() {

        ArrayList<int[]> edges =
            new ArrayList<>();

        for (int i = 0;
             i < graph.list.size(); i++) {

            for (Edge e : graph.list.get(i)) {

                if (i < e.to) {

                    edges.add(
                        new int[]{
                            i, e.to, e.distance
                        }
                    );
                }
            }
        }

        edges.sort(
            Comparator.comparingInt(
                a -> a[2]
            )
        );

        DSU dsu =
            new DSU(graph.list.size());

        int total = 0;

        System.out.println(
            "\n--- Minimum Facility Connection ---"
        );

        for (int[] e : edges) {

            if (dsu.find(e[0]) !=
                dsu.find(e[1])) {

                dsu.union(e[0], e[1]);

                System.out.println(
                    "Facility " + e[0] +
                    " - Facility " + e[1] +
                    " : " + e[2] + " km"
                );

                total += e[2];
            }
        }

        System.out.println(
            "Minimum Connection Distance = "
            + total + " km"
        );
    }

    /* ---------- MAINTENANCE PLANNING ---------- */

    static void maintenancePlanning() {

        System.out.print(
            "Enter number of maintenance tasks: "
        );

        int n = sc.nextInt();

        int[] cost = new int[n];
        int[] benefit = new int[n];

        for (int i = 0; i < n; i++) {

            System.out.print(
                "Enter task cost/time: "
            );

            cost[i] = sc.nextInt();

            System.out.print(
                "Enter usage importance: "
            );

            benefit[i] = sc.nextInt();
        }

        System.out.print(
            "Enter available budget/time: "
        );

        int budget = sc.nextInt();

        int[] dp =
            new int[budget + 1];

        for (int i = 0; i < n; i++) {

            for (int j = budget;
                 j >= cost[i]; j--) {

                dp[j] =
                    Math.max(
                        dp[j],
                        benefit[i] +
                        dp[j - cost[i]]
                    );
            }
        }

        System.out.println(
            "\n--- Maintenance Budget Planning ---"
        );

        System.out.println(
            "Maximum Usage Benefit = "
            + dp[budget]
        );
    }

    /* ---------- CAPACITY ALLOCATION ---------- */

    static void capacityAllocation() {

        System.out.print(
            "Enter total available slots: "
        );

        int slots = sc.nextInt();

        int n = facilities.size();

        int[][] dp =
            new int[n + 1][slots + 1];

        for (int i = 1; i <= n; i++) {

            Facility f =
                facilities.get(i - 1);

            int need =
                Math.max(
                    1,
                    f.capacity - f.occupancy
                );

            int benefit =
                f.capacity == 0
                ? 0
                : (f.occupancy * 100)
                  / f.capacity;

            for (int s = 0;
                 s <= slots; s++) {

                dp[i][s] =
                    dp[i - 1][s];

                if (need <= s) {

                    dp[i][s] =
                        Math.max(
                            dp[i][s],
                            benefit +
                            dp[i - 1][s - need]
                        );
                }
            }
        }

        System.out.println(
            "\n--- Facility Capacity Allocation ---"
        );

        System.out.println(
            "Maximum Utilization Value = "
            + dp[n][slots]
        );
    }

    /* ---------- TIME SLOT SCHEDULING ---------- */

    static boolean safeSlot(
        int facility,
        int slot,
        int[] assigned
    ) {

        for (Edge e :
            graph.list.get(facility)) {

            if (assigned[e.to] == slot)
                return false;
        }

        return true;
    }

    static boolean assignSlots(
        int facility,
        int totalSlots,
        int[] assigned
    ) {

        if (facility == graph.list.size())
            return true;

        for (int slot = 1;
             slot <= totalSlots; slot++) {

            if (safeSlot(
                facility,
                slot,
                assigned
            )) {

                assigned[facility] = slot;

                if (assignSlots(
                    facility + 1,
                    totalSlots,
                    assigned
                ))
                    return true;

                assigned[facility] = 0;
            }
        }

        return false;
    }

    static void timeScheduling() {

        System.out.print(
            "Enter number of time slots: "
        );

        int slots = sc.nextInt();

        int[] assigned =
            new int[graph.list.size()];

        if (assignSlots(
            0, slots, assigned
        )) {

            System.out.println(
                "\n--- Facility Time-Slot Schedule ---"
            );

            for (int i = 0;
                 i < assigned.length; i++) {

                System.out.println(
                    "Facility " + i +
                    " -> Time Slot "
                    + assigned[i]
                );
            }

        } else {

            System.out.println(
                "No valid time-slot schedule."
            );
        }
    }

    /* ---------- DEMAND MATCHING ---------- */

    static boolean subset(
        int[] values,
        int index,
        int target,
        ArrayList<Integer> selected
    ) {

        if (target == 0) {

            System.out.println(
                "Matching Capacity = "
                + selected
            );

            return true;
        }

        if (index == values.length ||
            target < 0)

            return false;

        selected.add(values[index]);

        if (subset(
            values,
            index + 1,
            target - values[index],
            selected
        ))
            return true;

        selected.remove(
            selected.size() - 1
        );

        return subset(
            values,
            index + 1,
            target,
            selected
        );
    }

    static void demandMatching() {

        System.out.print(
            "Enter number of demand values: "
        );

        int n = sc.nextInt();

        int[] values = new int[n];

        for (int i = 0; i < n; i++) {

            System.out.print(
                "Enter demand value: "
            );

            values[i] = sc.nextInt();
        }

        System.out.print(
            "Enter target capacity: "
        );

        int target = sc.nextInt();

        System.out.println(
            "\n--- Demand-Capacity Matching ---"
        );

        if (!subset(
            values,
            0,
            target,
            new ArrayList<>()
        )) {

            System.out.println(
                "No matching combination found."
            );
        }
    }

    /* ---------- SMART OPTIMIZATION ---------- */

    static class Task {
        int cost, value;

        Task(int cost, int value) {
            this.cost = cost;
            this.value = value;
        }
    }

    static int best;

    static double upperBound(
        ArrayList<Task> tasks,
        int index,
        int capacity,
        int value
    ) {

        double result = value;

        for (int i = index;
             i < tasks.size();
             i++) {

            Task t = tasks.get(i);

            if (t.cost <= capacity) {

                capacity -= t.cost;
                result += t.value;

            } else {

                result +=
                    ((double)t.value / t.cost)
                    * capacity;

                break;
            }
        }

        return result;
    }

    static void optimize(
        ArrayList<Task> tasks,
        int index,
        int capacity,
        int value
    ) {

        if (index == tasks.size()) {

            best =
                Math.max(best, value);

            return;
        }

        if (upperBound(
            tasks,
            index,
            capacity,
            value
        ) <= best)
            return;

        Task t = tasks.get(index);

        if (t.cost <= capacity) {

            optimize(
                tasks,
                index + 1,
                capacity - t.cost,
                value + t.value
            );
        }

        optimize(
            tasks,
            index + 1,
            capacity,
            value
        );
    }

    static void smartOptimization() {

        System.out.print(
            "Enter number of improvement tasks: "
        );

        int n = sc.nextInt();

        ArrayList<Task> tasks =
            new ArrayList<>();

        for (int i = 0; i < n; i++) {

            System.out.print(
                "Enter task cost: "
            );

            int cost = sc.nextInt();

            System.out.print(
                "Enter task benefit: "
            );

            int value = sc.nextInt();

            tasks.add(
                new Task(cost, value)
            );
        }

        System.out.print(
            "Enter available resources: "
        );

        int capacity = sc.nextInt();

        tasks.sort(
            (a, b) ->
                Double.compare(
                    (double)b.value / b.cost,
                    (double)a.value / a.cost
                )
        );

        best = 0;

        optimize(
            tasks,
            0,
            capacity,
            0
        );

        System.out.println(
            "\n--- Smart Resource Optimization ---"
        );

        System.out.println(
            "Best Utilization Value = " + best
        );
    }

    /* ---------- FACILITY RECOMMENDATION ---------- */

    static void recommendFacility() {

        System.out.print(
            "Enter required available capacity: "
        );

        int required = sc.nextInt();

        PriorityQueue<Facility> priority =
            new PriorityQueue<>(
                (a, b) ->
                    Integer.compare(
                        b.available(),
                        a.available()
                    )
            );

        for (Facility f : facilities) {

            if (f.available() >= required)
                priority.add(f);
        }

        System.out.println(
            "\n--- Recommended Public Facilities ---"
        );

        if (priority.isEmpty()) {

            System.out.println(
                "No suitable facility available."
            );

            return;
        }

        int count = 0;

        while (!priority.isEmpty() &&
               count < 3) {

            System.out.println(
                priority.poll()
            );

            count++;
        }
    }

    /* ---------- SAMPLE DATA ---------- */

    static void loadData() {

        Facility[] data = {

            new Facility(
                0,
                "Central Park",
                "Zone A",
                100,
                60,
                "Walking, Seating",
                "6 AM - 8 PM"
            ),

            new Facility(
                1,
                "Community Hall",
                "Zone A",
                80,
                30,
                "Hall, Parking",
                "9 AM - 9 PM"
            ),

            new Facility(
                2,
                "Public Library",
                "Zone B",
                120,
                90,
                "Books, WiFi",
                "8 AM - 8 PM"
            ),

            new Facility(
                3,
                "Sports Center",
                "Zone B",
                150,
                100,
                "Gym, Ground",
                "6 AM - 10 PM"
            ),

            new Facility(
                4,
                "Health Center",
                "Zone C",
                70,
                45,
                "Clinic, Pharmacy",
                "8 AM - 6 PM"
            ),

            new Facility(
                5,
                "Recreation Center",
                "Zone C",
                90,
                40,
                "Games, Indoor Hall",
                "9 AM - 9 PM"
            )
        };

        for (Facility f : data) {

            facilities.add(f);
            records.put(f.id, f);

            tree.insert(f);
            avl.insert(f);
            threaded.insert(f);
        }

        graph =
            new Graph(data.length);

        graph.addEdge(0, 1, 2);
        graph.addEdge(0, 2, 4);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 3, 2);
        graph.addEdge(2, 4, 6);
        graph.addEdge(3, 4, 3);
        graph.addEdge(3, 5, 4);
        graph.addEdge(4, 5, 2);
    }

    /* ---------- MAIN ---------- */

    public static void main(String[] args) {

        loadData();

        int choice;

        do {

            System.out.println(
                "\n=============================================="
            );

            System.out.println(
                " AI-DRIVEN PUBLIC FACILITY UTILIZATION SYSTEM"
            );

            System.out.println(
                "=============================================="
            );

            System.out.println(
                "1. View Public Facilities"
            );

            System.out.println(
                "2. Add New Facility"
            );

            System.out.println(
                "3. Search Facility"
            );

            System.out.println(
                "4. Browse Facility Records"
            );

            System.out.println(
                "5. View Facility Connections"
            );

            System.out.println(
                "6. Find Nearby Facilities"
            );

            System.out.println(
                "7. Explore Facility Zones"
            );

            System.out.println(
                "8. Find Shortest Travel Route"
            );

            System.out.println(
                "9. Create Minimum Facility Network"
            );

            System.out.println(
                "10. Maintenance Budget Planning"
            );

            System.out.println(
                "11. Facility Capacity Allocation"
            );

            System.out.println(
                "12. Facility Time-Slot Scheduling"
            );

            System.out.println(
                "13. Demand-Capacity Matching"
            );

            System.out.println(
                "14. Smart Resource Optimization"
            );

            System.out.println(
                "15. Recommend Best Facility"
            );

            System.out.println(
                "0. Exit"
            );

            System.out.print(
                "\nEnter your choice: "
            );

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    viewFacilities();
                    break;

                case 2:
                    addFacility();
                    break;

                case 3:
                    searchFacility();
                    break;

                case 4:
                    browseRecords();
                    break;

                case 5:
                    graph.display();
                    break;

                case 6:
                    nearbyFacilities();
                    break;

                case 7:
                    exploreFacilities();
                    break;

                case 8:
                    shortestRoute();
                    break;

                case 9:
                    minimumNetwork();
                    break;

                case 10:
                    maintenancePlanning();
                    break;

                case 11:
                    capacityAllocation();
                    break;

                case 12:
                    timeScheduling();
                    break;

                case 13:
                    demandMatching();
                    break;

                case 14:
                    smartOptimization();
                    break;

                case 15:
                    recommendFacility();
                    break;

                case 0:
                    System.out.println(
                        "\nThank you for using the system."
                    );
                    break;

                default:
                    System.out.println(
                        "Invalid choice."
                    );
            }

        } while (choice != 0);

        sc.close();
    }
}