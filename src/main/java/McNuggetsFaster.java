import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class McNuggetsFaster {

    private static final boolean[] ARRAY = new boolean[1000000000];

    public static void main(String[] args) {
        solve(Arrays.asList(3, 5, 8));
        solve(Arrays.asList(6, 7, 8));
        solve(Arrays.asList(37, 59, 113));
        solve(Arrays.asList(115, 116, 117));
        solve(Arrays.asList(316, 317, 318));
        solve(Arrays.asList(1160, 2377, 5431));
        solve(Arrays.asList(9745, 12377, 15321));
        solve(Arrays.asList(63455, 97654, 201249));
    }

    private static void solve(List<Integer> numbers) {
        Collections.sort(numbers);

        long startTime = System.nanoTime();
        ARRAY[0] = true;

        final int max = numbers.get(numbers.size()-1);
        int start = 1;
        Integer largest = null;
        while (largest == null) {
            largest = calc(numbers, start);
            start += max;
        }
        long time = System.nanoTime()-startTime;
        System.out.printf("Solved %s to %d in %d\n", numbers, largest, time);
    }

    private static Integer calc(List<Integer> numbers, int start) {
        final int min = numbers.get(0);
        final int max = numbers.get(numbers.size()-1);

        Arrays.fill(ARRAY, start, start+max, false);
        for (int n : numbers) {
            for (int j = Math.max(0, start-n); j + n < start+max; ++j) {
                if (ARRAY[j]) {
                    ARRAY[j + n] = true;
                }
            }
        }

        int largest = 0;
        int numCannot = 0;
        for (int i = Math.max(0, start-min); i < start+max; ++i) {
            if (ARRAY[i]) {
                if (++numCannot == min) {
                    return largest;
                }
            } else {
                largest = i;
                numCannot = 0;
            }
        }
        return null;
    }
}
