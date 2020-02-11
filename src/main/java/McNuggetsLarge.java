import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
        data[0] = 0L;
        int count = 1;
        boolean changes = true;
        while (count < min || changes) {
            changes = false;
            for (int i = 0; i < data.length; ++i) {
                if (data[i] >= 0) {
                    for (int j = 1; j < numbers.size(); ++j) {
                        long value = data[i] + numbers.get(j);
                        final int index = (int) (value % min);
                        if (data[index] == -1) {
                            data[index] = value;
                            ++count;
                        } else if (data[index] > value) {
                            data[index] = value;
                            changes = true;
                        }
                    }
                }
            }
        }
        long largest = 0;
        for (long value : data) {
            largest = Math.max(largest, value);
        }
        final long time = System.nanoTime()-startTime;
        System.out.printf("Solved %s to %d in %d\n", numbers, largest-min, time);
    }
}
