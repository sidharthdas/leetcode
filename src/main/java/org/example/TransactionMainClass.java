package org.example;


import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

public class TransactionMainClass {

    public static void main(String[] args) {
        //System.out.println(invalidTransactions(new String[]{"alice,20,800,mtv","alice,50,1200,mtv"}));

        //System.out.println(halveArray(new int[]{5, 19, 8, 1}));

        //s = "aababcaab", maxLetters = 2, minSize = 3, maxSize = 4
        //System.out.println(maxFreq("abcde", 2, 3, 3));

        //System.out.println(maxAlternatingSum(new int[]{1,2,3}));

        //System.out.println(maxPairStrength(new int[]{4,6,8}));

        System.out.println(nearestDrone(new int[][]{{0,0,8},{2,2,9}}, new int[]{3,4}));
    }

    public static int nearestDrone(int[][] drones, int[] target) {

        int ans = -1;
        int smallestDist = Integer.MAX_VALUE;
        int len = drones.length;
        for(int i = 0; i < len; i++) {
            int dist = Math.abs(drones[i][0] - target[0]) + Math.abs(drones[i][1] - target[1]);
            if(dist <= drones[i][2]) {
                if(smallestDist > dist) {
                    smallestDist = dist;
                    ans = i;
                }
            }
        }

        return ans;

    }

    public int countValidPrefixes(String s) {

        char[] crr = s.toCharArray();
        int oneCount = 0;
        int zeroCount = 0;
        int len = s.length();

        int prefixCount = 0;

        for(int i = 0; i < len; i++) {
            if(crr[i] =='0') zeroCount += 1;
            if(crr[i] == '1') oneCount += 1;

            if(zeroCount != 0 && oneCount != 0) {
                if((zeroCount == oneCount ) || Math.abs(zeroCount - oneCount) == 1) {
                    prefixCount++;
                }
            }
        }

        return prefixCount + 1;
    }

    public static int largestInteger(int n, int s) {

        int start = 0;
        int end = switch (n) {
            case 1 -> 9;
            case 2 -> 99;
            case 3 -> 999;
            case 4 -> 9999;
            case 5 -> 99999;
            default -> 0;
        };

        int max = -1;
        for (int i = start; i <= end; i += 1) {
            boolean flag = true;
            int temp = 0;
            int tempI = i;
            while (tempI != 0) {
                temp += tempI % 10;
                tempI /= 10;
                if (temp > s) {
                    flag = false;
                    break;
                }
            }

            if (flag && temp == s) {
                max = i;
            }
        }

        return max;

    }


    public static long maxPairStrength(int[] nums) {

        long max = Integer.MIN_VALUE;
        int len = nums.length;
        for(int i = 0; i < len; i++) {
            for(int j = i+1; j < len; j++) {
                long gcd = gcd(nums[i], nums[j]);
                long tem = ((long) nums[i] * nums[j]) / (gcd * gcd);
                max = Math.max(max, tem);

            }
        }

        return max;


    }


    private static int gcd (int a , int b) {
        while(b != 0) {
            int temp = b;
            b = a%b;
            a = temp;
        }
        return a;
    }

    public int elevatorRequests(int n, int[] requests) {

        int sum = 0;
        int len = requests.length;

        for(int i = 0; i < len; i++) {
            if(i == 0) {
                sum += requests[i];
            }else {
                sum += Math.abs(requests[i] - requests[i-1]);
            }
        }

        return sum;
    }

    public static long maxAlternatingSum(int[] nums) {

        int len = nums.length;
        int[] absNum = new int[len];
        for(int i = 0; i < len; i++) {
            absNum[i] = Math.abs(nums[i]);
        }

        Arrays.sort(absNum);

        int lastIndex = len - 1;
        int startIndex = 0;

        long sum = 0;

        while(startIndex < lastIndex) {
            sum += ((long) absNum[lastIndex] * absNum[lastIndex]) - ((long) absNum[startIndex] * absNum[startIndex]) ;
            startIndex++;
            lastIndex--;
        }

        if(len % 2 != 0) {
            int mid = len/2;
            sum += ((long) absNum[mid] * absNum[mid]);
        }

        return sum;

    }

    public static int[] resultsArray(int[] nums, int k) {

        int len = nums.length;
        List<Integer> l = new ArrayList<>();

        for(int i = 0; i < len; i++) {
            if(i +k <= len) {
                if (checkSorted(Arrays.copyOfRange(nums, i, i + k)) && checkConsecutive(Arrays.copyOfRange(nums, i, i + k))) {
                    l.add(nums[i + k - 1]);
                } else {

                    l.add(-1);
                }
            }
        }
        return l.stream().mapToInt(x-> x).toArray();
    }

    private static boolean checkSorted(int[] ints) {

        int len = ints.length;

        for(int i = 1; i < len; i++) {
            if(ints[i - 1] > ints[i]) {
                return false;
            }
        }

        return true;
    }

    private static boolean checkConsecutive(int[] ints) {

        int len = ints.length;

        for(int i = 1; i < len; i++) {
            if(Math.abs(ints[i-1] - ints[i]) != 1) return false;
        }

        return true;
    }

    class Solution {
        public static int getNumberOfBacklogOrders(int[][] orders) {

            PriorityQueue<int[]> buyPq = new PriorityQueue<>((a, b) -> b[0] - a[0]);
            PriorityQueue<int[]> sellPq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

            //0 -> bug
            //1 -> sell


            for (int[] order : orders) {
                if (order[2] == 0) {
                    if (sellPq.isEmpty()) {
                        buyPq.offer(order);
                    } else {
                        int[] peekSell = sellPq.peek();
                        int remainingQty = order[1];
                        while (peekSell != null && remainingQty != 0) {

                            if (peekSell[0] <= order[0]) {
                                if (peekSell[1] > remainingQty) {
                                    sellPq.poll();
                                    sellPq.offer(new int[]{peekSell[0], peekSell[1] - remainingQty, peekSell[2]});
                                    remainingQty = 0;
                                    break;

                                } else if (peekSell[1] == remainingQty) {
                                    sellPq.poll();
                                    remainingQty = 0;
                                    break;

                                } else {
                                    remainingQty -= peekSell[1];
                                    sellPq.poll();
                                    peekSell = sellPq.peek();

                                }
                            } else {
                                buyPq.offer(new int[]{order[0], remainingQty, order[2]});
                                remainingQty = 0;
                                break;
                            }
                        }
                        if (remainingQty != 0) {
                            buyPq.offer(new int[]{order[0], remainingQty, order[2]});
                        }
                    }
                } else {


                    if (buyPq.isEmpty()) {
                        sellPq.offer(order);
                    } else {
                        int[] peekBuy = buyPq.peek();
                        int remainingQty = order[1];
                        while (peekBuy != null && remainingQty > 0) {

                            if (peekBuy[0] >= order[0]) {
                                if (peekBuy[1] > remainingQty) {
                                    buyPq.poll();
                                    buyPq.offer(new int[]{peekBuy[0], peekBuy[1] - remainingQty, peekBuy[2]});
                                    remainingQty = 0;
                                    break;

                                } else if (peekBuy[1] == remainingQty) {
                                    buyPq.poll();
                                    remainingQty = 0;
                                    break;

                                } else {
                                    remainingQty -= peekBuy[1];
                                    buyPq.poll();
                                    peekBuy = buyPq.peek();
                                }
                            } else {
                                sellPq.offer(new int[]{order[0], remainingQty, order[2]});
                                remainingQty = 0;
                                break;
                            }
                        }
                        if (remainingQty != 0) {
                            sellPq.offer(new int[]{order[0], remainingQty, order[2]});
                        }
                    }
                }
            }

            //return buyPq.size() + sellPq.size();

            long count = 0;
            long MOD = 1_000_000_007L;

            for (int[] a : buyPq) {
                count += a[1];
            }


            for (int[] a : sellPq) {
                count += a[1];
            }

            return (int) (count % MOD);
        }
    }

    public static int maxFreq(String s, int maxLetters, int minSize, int maxSize) {

        int len = s.length();

        int count = 0;

        List<String> list = new ArrayList<>();

        for (int i = 0; i < len; i++) {
            Set<Character> set = new HashSet<>();
            int size = 0;
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = i; j < len; j++) {


                if (size >= minSize && size <= maxSize) {
                    if (set.size() <= maxLetters) {
                        list.add(stringBuilder.toString());
                        count++;
                    } else {
                        break;
                    }

                }
                if (size > maxSize) {
                    break;
                }

                stringBuilder.append(s.charAt(j));
                set.add(s.charAt(j));
                size++;
            }

            if (size >= minSize && size <= maxSize && set.size() <= maxLetters) {
                list.add(stringBuilder.toString());
            }
        }

        System.out.println(list);

        if (list.isEmpty()) return 0;

        return Math.toIntExact(list.stream()
                .filter(x -> x.length() >= minSize && x.length() <= maxSize)
                .collect(Collectors.groupingBy(x -> x, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .findFirst()
                .get().getValue());


        //return count;

    }

    public static int halveArray(int[] nums) {

        PriorityQueue<Double> priorityQueue = new PriorityQueue<>(Collections.reverseOrder());
        double sum = 0;
        for (int i : nums) {
            priorityQueue.offer((double) i);
            sum += i;
        }

        int count = 0;
        double k = 0;


        while (sum - k > sum / 2) {
            double temp = priorityQueue.poll();
            k += temp / 2;
            priorityQueue.offer(temp / 2);
            count++;
        }
        return count + 1;
    }

    public int halveArray1(int[] nums) {
        double s = 0, k = 0;
        int i = 0;
        PriorityQueue<Double> pq = new PriorityQueue<>(Collections.reverseOrder());
        for (int x : nums) {
            pq.offer((double) x);
            s += x;
        }
        while (s - k > s / 2) {
            double x = pq.poll();
            k += x / 2;
            pq.offer(x / 2);
            i++;
        }
        return i;
    }

    public String repeatLimitedString(String s, int repeatLimit) {

        Map<String, Long> map = Arrays.stream(s.split("")).collect(Collectors
                .groupingBy(x -> x, Collectors.counting()));

        List<String> list = map.keySet().stream().sorted(Collections.reverseOrder()).toList();

        return null;


    }

    static class Transaction {
        String name;
        int time;
        int amount;
        String city;

        public Transaction(String trans) {
            String[] srr = trans.split(",");
            this.name = srr[0];
            this.time = Integer.parseInt(srr[1]);
            this.amount = Integer.parseInt(srr[2]);
            this.city = srr[3];
        }
    }


    public static List<String> invalidTransactions(String[] transactions) {

        Map<String, List<Transaction>> map = new HashMap<>();

        for (String l : transactions) {
            Transaction transaction = new Transaction(l);
            if (map.containsKey(transaction.name)) {
                map.get(transaction.name).add(transaction);
            } else {
                List<Transaction> l1 = new ArrayList<>();
                l1.add(transaction);
                map.put(transaction.name, l1);
            }
        }

        List<String> inValid = new ArrayList<>();

        for (String trans : transactions) {
            Transaction transaction = new Transaction(trans);

            if (!isValid(transaction, map.getOrDefault(transaction.name, new ArrayList<>()))) {
                inValid.add(trans);
            }
        }


        return inValid;

    }

    private static boolean isValid(Transaction transaction, List<Transaction> list) {
        if (transaction.amount > 1000) {
            return false;
        }

        for (Transaction transaction1 : list) {
            if (Math.abs(transaction1.time - transaction.time) <= 60 && !transaction1.city.equals(transaction.city)) {
                return false;
            }
        }
        return true;
    }
}
