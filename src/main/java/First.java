import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class First {

    private static final boolean PRINT_SOLUTION = false;
    private static final int NUM_V2 = 100;
    private static final long MOD_V2 = 1000000000;

    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().maxMemory());
        calcVersion2();
        //calcVersion1();
    }

    private static void calcVersion1() {
        long last = 1;
        long sum = 0;
        for (int i = 1; i < 17; ++i) {
            Map<Integer, Integer> next = new HashMap<>();
            long curr = calc(i, next);
            //System.out.println(String.format("%d: %d %d %f %f", i, curr, sum, curr*1.0/last, sum == 0 ? 0 : curr*1.0/sum));
            System.out.println(String.format("%d: %d, %s", i, curr, next));
            sum += curr;
            last = curr;
        }
    }

    private static long calc(int num, Map<Integer, Integer> next) {
        int[] dp = new int[num+1];
        return calc(dp, 0, num, next);
    }

    private static long calc(int[] dp, int current, int num, Map<Integer, Integer> next) {
        if (current == num) {
            int nextValue = calcAvailable(dp);
            Integer prev = next.get(nextValue);
            next.put(nextValue, (prev == null ? 0 : prev) + 1);
            if (PRINT_SOLUTION) {
                System.out.println("*** SOLUTION ***");
                for (int i : dp) {
                    System.out.println(i);
                }
            }
            return 1;
        }
        ++dp[0];
        long sum = calc(dp, current+1, num, next);
        --dp[0];
        for (int i = 1; i < num; ++i) {
            if (dp[i] < dp[i-1]) {
                ++dp[i];
                sum += calc(dp, current+1, num, next);
                --dp[i];
            } else if (dp[i] == 0) {
                break;
            }
        }
        return sum;
    }

    private static int calcAvailable(int[] dp) {
        int nextValue = 1;
        for (int i = 1; i < dp.length; ++i) {
            if (dp[i] < dp[i-1]) {
                ++nextValue;
            }
        }
        return nextValue;
    }

    private static void calcVersion2() {
        Map<Node, Long> dp = new HashMap<>();
        dp.put(new Node(), 1L);
        for (int i = 1; i <= 6; ++i) {
            long sum = 0;
            for (Long value : dp.values()) {
                sum = (sum+value)%MOD_V2;
            }
            Map<Integer, Long> dist = new HashMap<>();
            for (Map.Entry<Node, Long> entry : dp.entrySet()) {
                int available = calcAvailable(entry.getKey().len);
                Long v = dist.get(available);
                dist.put(available, entry.getValue() + (v == null ? 0 : v));
            }
            if (PRINT_SOLUTION) {
                for (Node node : dp.keySet()) {
                    System.out.println(node);
                }
            }
            //System.out.printf("%d: %d %d %d %d %d %d %d %d %s\n", i, sum, dp.size(), dist.get(2), dist.get(3), dist.get(4), dist.get(5), dist.get(6), dist.get(7), dist);
            System.out.printf("%d: %d %d\n", i, sum, Runtime.getRuntime().totalMemory());
            Map<Node, Long> newDp = new HashMap<>();
            for (Map.Entry<Node, Long> entry : dp.entrySet()) {
                Node node = entry.getKey();
                add(newDp, new Node(node, 0), entry.getValue());
                for (int j = 1; j < node.len.length; ++j) {
                    if (node.len[j] < node.len[j-1]) {
                        add(newDp, new Node(node, j), entry.getValue());
                    }
                    if (node.len[j] == 0) {
                        break;
                    }
                }
            }
            dp = newDp;
        }
    }

    private static void add(Map<Node, Long> dp, Node node, Long value) {
        Long oldValue = dp.get(node);
        dp.put(node, (value + (oldValue == null ? 0 : oldValue))%MOD_V2);
    }

    private static class Node {
        private final int[] len = new int[NUM_V2+2];

        private Node() {
            len[0] = 1;
        }

        private Node(Node node, int row) {
            System.arraycopy(node.len, 0, len, 0, len.length);
            ++len[row];
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Node node = (Node) o;
            return Arrays.equals(len, node.len);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(len);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int k : len) {
                if (k == 0) {
                    break;
                }
                for (int i = 0; i < k; ++i) {
                    sb.append("*");
                }
                sb.append("\n");
            }
            sb.append("\n");
            return sb.toString();
        }
    }
}
