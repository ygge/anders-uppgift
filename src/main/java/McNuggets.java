import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class McNuggets {

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
    }

    private static void solve(List<Integer> numbers) {
        Collections.sort(numbers);
        final int min = numbers.get(0);
        final int max = numbers.get(numbers.size()-1);
        final boolean[] cache = new boolean[max];
        cache[0] = true;

        long startTime = System.nanoTime();
        int largest = 0;
        int numCannot = 0;
        for (int i = 1; ; ++i) {
            if (canMakeMem(cache, i, numbers)) {
                if (++numCannot == min) {
                    break;
                }
            } else {
                largest = i;
                numCannot = 0;
            }
        }
        long time = System.nanoTime()-startTime;
        System.out.printf("Solved %s to %d in %d\n", numbers, largest, time);
    }

    private static boolean canMakeMem(boolean[] cache, int num, List<Integer> numbers) {
        return cache[num % cache.length] = numbers.stream()
                .anyMatch(n -> num-n >= 0 && cache[(num - n) % cache.length]);
    }
}
