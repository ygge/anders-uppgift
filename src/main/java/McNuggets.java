import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class McNuggets {

    private static final boolean[] ARRAY = new boolean[1000000000];

    public static void main(String[] args) {
        /*
43
17
759
6554
49927
248395
3425051

Array
Solved [6, 9, 20] to 43 in 82667
Solved [6, 7, 8] to 17 in 29705
Solved [37, 59, 113] to 759 in 3710958
Solved [115, 116, 117] to 6554 in 62148527
Solved [316, 317, 318] to 49927 in 1046379706
Solved [1160, 2377, 5431] to 248395 in 21482779485

Brute
Solved [6, 9, 20] to 43 in 89763 = 7
Solved [6, 7, 8] to 17 in 35764 = 2,8
Solved [37, 59, 113] to 759 in 602693 = 20
Solved [115, 116, 117] to 6554 in 20932425 = 56
Solved [316, 317, 318] to 49927 in 970656298 = 157
Solved [1160, 2377, 5431] to 248395 in 623050758 = 214
Solved [9745, 12377, 15321] to 3425051 in 84458780447 = 351
         */
        solve(Arrays.asList(6, 9, 20));
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
        final int min = numbers.get(0);
        final int max = numbers.get(numbers.size()-1);

        long startTime = System.nanoTime();
        int largest = 0;
        int numCannot = 0;
        int a = 0;
        int b = 0;
        List<Integer> cannotMake = new ArrayList<>();
        for (int i = max+1; ; ++i) {
            if (canMakeBrute(i, numbers)) {
                ++a;
                if (++numCannot == min) {
                    break;
                }
            } else {
                ++b;
                largest = i;
                numCannot = 0;
                cannotMake.add(i);
            }
        }
        long time = System.nanoTime()-startTime;
        System.out.printf("Solved %s to %d in %d (%d,%d,%s)\n", numbers, largest, time, a, b, cannotMake);
    }

    private static boolean canMakeBrute(int num, List<Integer> numbers) {
        return canMakeBrute(num, numbers, numbers.size()-1);
    }

    private static boolean canMakeBrute(int num, List<Integer> numbers, int index) {
        if (index == 0) {
            return num%numbers.get(0) == 0;
        }
        int n = numbers.get(index);
        for (int i = 0; i*n <= num; ++i) {
            if (canMakeBrute(num-i*n, numbers, index-1)) {
                return true;
            }
        }
        return false;
    }

    private static boolean canMakeArray(int num, List<Integer> numbers) {
        ARRAY[0] = true;
        Arrays.fill(ARRAY, 1, num, false);
        final int ln = numbers.get(numbers.size()-1);
        for (int j = 0; j < num; j += ln) {
            if (ARRAY[j]) {
                ARRAY[j+ln] = true;
                if (j+ln == num) {
                    return true;
                }
            }
        }
        for (int i = numbers.size()-2; i > 0; --i) {
            final int n = numbers.get(i);
            for (int j = 0; j < num; ++j) {
                if (ARRAY[j]) {
                    ARRAY[j+n] = true;
                    if (j+n == num) {
                        return true;
                    }
                }
            }
        }
        final int n = numbers.get(0);
        for (int i = 0; i < num; ++i) {
            if (ARRAY[i] && (num-i)%n == 0) {
                return true;
            }
        }
        return false;
    }
}
