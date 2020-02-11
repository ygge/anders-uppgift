import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
        final Map<Integer, Long> data = new HashMap<>();
        data.put(0, 0L);
        while (data.size() < min) {
            for (long v : new HashSet<>(data.values())) {
                for (int i = 1; i < numbers.size(); ++i) {
                    add(data, numbers.get(i), min, v);
                }
            }
        }
        long largest = data.values().stream()
                .max(Long::compareTo)
                .orElse(-1L);
        final long time = System.nanoTime()-startTime;
        System.out.printf("Solved %s to %d in %d\n", numbers, largest-min, time);
    }

    private static void add(Map<Integer, Long> data, long b, int c, long v) {
        final int bb = (int) ((v + b) % c);
        if (!data.containsKey(bb) || data.get(bb) > v + b) {
            data.put(bb, v + b);
        }
    }
}
