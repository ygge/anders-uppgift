import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

public class McNuggetsLarge {

    public static void main(String[] args) {
        solve(Arrays.asList(3, 5, 8));
        solve(Arrays.asList(6, 7, 8));
        solve(Arrays.asList(37, 59, 113));
        solve(Arrays.asList(115, 116, 117));
        solve(Arrays.asList(316, 317, 318));
        solve(Arrays.asList(1160, 2377, 5431));
        solve(Arrays.asList(9745, 12377, 15321));
        solve(Arrays.asList(63455, 97654, 201249));
        solve(Arrays.asList(115623, 135704, 174347));
        solveLong(Arrays.asList(1111L, 35686677278789L, 35669685667435L));
    }

    private static void solve(List<Integer> numbers) {
        solveLong(numbers.stream().map(i -> (long)i).collect(Collectors.toList()));
    }

    private static void solveLong(List<Long> numbers) {
        Collections.sort(numbers);
        final int min = (int)(long)numbers.get(0);

        long startTime = System.nanoTime();
        long[] data = new long[min];
        Arrays.fill(data, -1);
        int num = 0;
        Queue<Node> que = new PriorityQueue<>(Comparator.comparingLong(a -> a.value));
        que.add(new Node(0, 0));
        long largest = 0;
        while (!que.isEmpty()) {
            ++num;
            Node node = que.poll();
            if (data[node.mod] != -1 && data[node.mod] <= node.value) {
                continue;
            }
            data[node.mod] = node.value;
            largest = Math.max(largest, node.value);
            for (int j = 1; j < numbers.size(); ++j) {
                long value = node.value + numbers.get(j);
                final int index = (int) (value % min);
                if (data[index] == -1 || data[index] > value) {
                    que.add(new Node(value, index));
                }
            }
        }
        final long time = System.nanoTime()-startTime;
        System.out.printf("Solved %s to %d in %d (%d)\n", numbers, largest-min, time, num);
    }

    private static final class Node {
        private final long value;
        private final int mod;

        private Node(long value, int mod) {
            this.value = value;
            this.mod = mod;
        }
    }
}
