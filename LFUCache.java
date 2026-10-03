// for get and put operations taking O(n) time complexity for removing from queue


class LFUCache {

    HashMap<Integer, Node> map = new HashMap<>();
    PriorityQueue<Node> q;

    int capacity;
    int time = 0;

    class Node {
        int key;
        int value;
        int frequency;
        int time;

        public Node(int key, int value, int frequency, int time) {
            this.key = key;
            this.value = value;
            this.frequency = frequency;
            this.time = time;
        }
    }

    public LFUCache(int capacity) {

        this.capacity = capacity;

        q = new PriorityQueue<>((a, b) -> {
            if (a.frequency == b.frequency) {
                return a.time - b.time;
            }

            return a.frequency - b.frequency;
        });
    }

    public int get(int key) {

        if (!map.containsKey(key)) {
            return -1;
        }

        Node curr = map.get(key);

        // Remove BEFORE changing frequency
        q.remove(curr);

        curr.frequency++;
        curr.time = time++;

        // Add it back so PriorityQueue can reorder it
        q.offer(curr);

        return curr.value;
    }

    public void put(int key, int value) {

        if (capacity == 0) {
            return;
        }

        if (map.containsKey(key)) {

            Node curr = map.get(key);

            q.remove(curr);

            curr.value = value;
            curr.frequency++;
            curr.time = time++;

            q.offer(curr);

        } else {

            if (map.size() == capacity) {

                Node lfuNode = q.poll();

                map.remove(lfuNode.key);
            }

            Node newNode = new Node(key, value, 1, time++);

            map.put(key, newNode);
            q.offer(newNode);
        }
    }
}
