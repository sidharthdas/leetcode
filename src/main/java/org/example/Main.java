package org.example;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        /*System.out.println(maxDigitRange(new int[]{76364,80946,83426,86822,8470,77400,91853,
                17447,37800,96545,84619,58374,35177,32777,97032,59483,19578,5770,90000,65561,
                11209,66371,24953,4463,11437,45951,55753,96286,37364,30585,40914,74370,78195,
                84824,3592,97757,11186,22197,77593,96587,73024,12818,18252,48610,90339,97032,5513,6493}));

        }*/

        //System.out.println(consecutiveSetBits(93));
        //System.out.println(minChanges("0000"));

        //[4,4,2,1], queries = [5,3,1,0]

        //System.out.println(gcd(44, 8));

        //System.out.println(gcdValues(new int[]{2,3,4}, new long[]{0,2,2}));

        //System.out.println(mergeAdjacent(new int[]{2, 1, 1, 2}));

        System.out.println(minSwaps("][]["));
    }

    public String shiftingLetters(String s, int[][] shifts) {

        int len = s.length();
        int[] arr = new int[len];

        for(int[] shift : shifts) {

            if(shift[2] == 0) {
                for(int i = shift[0]; i <= shift[1]; i++) {
                    arr[i] = arr[i] - 1;
                }
            } else {
                for(int i = shift[0]; i <= shift[1]; i++) {
                    arr[i] = arr[i] + 1;
                }
            }

        }

        for(int i : arr) {
            if( i < 0) {

            } else {

            }
        }

        return null;

    }

    public static int minSwaps(String s) {

        char[] crr = s.toCharArray();

        Stack<Character> stack = new Stack<>() ;

        int miss = 0;
        for (char c : crr) {

            if (c == '[') {
                stack.push('[');
            } else {
                if (!stack.isEmpty()) {
                    stack.pop();
                } else {
                    miss++;
                }
            }

        }

        return (miss+1)/2;

    }

    class Solution {
        public long totalCost(int[] costs, int k, int candidates) {
            int n = costs.length;

            PriorityQueue<Integer> leftPQ = new PriorityQueue<>();
            PriorityQueue<Integer> rightPQ = new PriorityQueue<>();

            int left = 0;
            int right = n - 1;

            while (left < candidates && left <= right) {
                leftPQ.offer(costs[left++]);
            }

            while (right >= n - candidates && right >= left) {
                rightPQ.offer(costs[right--]);
            }

            long ans = 0;

            while (k-- > 0) {
                int leftCost = leftPQ.isEmpty() ? Integer.MAX_VALUE : leftPQ.peek();
                int rightCost = rightPQ.isEmpty() ? Integer.MAX_VALUE : rightPQ.peek();

                if (leftCost <= rightCost) {
                    ans += leftPQ.poll();

                    if (left <= right) {
                        leftPQ.offer(costs[left++]);
                    }
                } else {
                    ans += rightPQ.poll();

                    if (left <= right) {
                        rightPQ.offer(costs[right--]);
                    }
                }
            }

            return ans;
        }
    }

    public long totalCost(int[] costs, int k, int candidates) {

        int len = costs.length;

        int left = 0;
        int right = len - 1;

        PriorityQueue<Integer> leftPq = new PriorityQueue<>();
        PriorityQueue<Integer> rightPq = new PriorityQueue<>();

        while (left < candidates && left <= right) {
            leftPq.offer(costs[left]);
            left++;
        }

        while (right >= len - candidates && right >= 0) {
            rightPq.offer(costs[right]);
            right--;
        }

        int totalCost = 0;

        while (k != 0) {
            int leftCost = leftPq.isEmpty() ? Integer.MAX_VALUE : leftPq.peek();

            int rightCost = rightPq.isEmpty() ? Integer.MAX_VALUE : rightPq.peek();

            if (leftCost <= rightCost) {
                totalCost += leftPq.poll();

                if (left <= right) {
                    leftPq.offer(costs[left++]);
                }
            } else {
                totalCost += rightPq.poll();

                if (left <= right) {
                    rightPq.offer(costs[right--]);
                }
            }

        }
        return totalCost;


    }

    public static List<Long> mergeAdjacent(int[] nums) {

        Stack<Long> stack = new Stack<>();
        int len = nums.length;
        for (int i = 0; i < len; i++) {

            if (stack.isEmpty()) {
                stack.add((long) nums[i]);
            } else {
                if (stack.peek() == nums[i]) {
                    long temp = nums[i];
                    while (!stack.isEmpty() && stack.peek() == temp) {
                        temp += stack.pop();
                    }
                    stack.add(temp);
                } else {
                    stack.add((long) nums[i]);
                }
            }
        }
        List<Long> ans = new ArrayList<>();
        while (!stack.isEmpty()) {
            ans.addFirst(stack.pop());
        }
        return ans;
    }

    public static int[] gcdValues(int[] nums, long[] queries) {

        int len = nums.length;

        List<Integer> gcdPairs = new ArrayList<>();
        int index = 0;


        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                gcdPairs.add(gcd(nums[i], nums[j]));
                index++;
            }
        }

        Collections.sort(gcdPairs);
        int[] sol = new int[queries.length];
        index = 0;

        for (long i : queries) {
            sol[index] = gcdPairs.get(Math.toIntExact(i));
            index++;

        }

        return sol;

    }

    public static int minChanges(String s) {

        List<String> list = new ArrayList<>();

        int len = s.length();
        for (int i = 0; i < len; i += 2) {
            list.add(s.charAt(i) + "" + s.charAt(i + 1));
        }
        int count = 0;
        for (String s1 : list) {

            if ((s1.charAt(0) == '0' && s1.charAt(1) == '0') ||
                    (s1.charAt(0) == '1' && s1.charAt(1) == '1')) {
                continue;
            } else {
                count += 1;
            }

        }

        return count;


    }

    public long gcdSum(int[] nums) {

        int len = nums.length;

        int[] prefixGcd = new int[len];

        int max = nums[0];
        for (int i = 0; i < len; i++) {
            max = Math.max(max, nums[i]);
            prefixGcd[i] = gcd(nums[i], max);
        }

        Arrays.sort(prefixGcd);

        int start = 0;
        int end = prefixGcd.length - 1;

        long sum = 0;

        while (start < end) {
            sum += gcd(prefixGcd[start], prefixGcd[end]);
            start++;
            end--;
        }

        return sum;

    }

    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }

        return a;
    }

    public List<Integer> findGoodIntegers(int n) {

        int limit = (int) Math.cbrt(n);

        Map<Integer, Integer> map = new HashMap<>();

        for (int a = 1; a <= limit; a++) {
            int a3 = a * a * a;
            for (int b = a; b <= limit; b++) {
                int sum = a3 + b * b * b;
                if (sum > n) break;
                map.put(sum, map.getOrDefault(sum, 0) + 1);

            }
        }

        List<Integer> ans = new ArrayList<>();

        for (Map.Entry<Integer, Integer> m : map.entrySet()) {
            if (m.getValue() == 2) ans.add(m.getKey());
        }

        Collections.sort(ans);

        return ans;

    }


    public static boolean consecutiveSetBits(int n) {

        String s = Integer.toBinaryString(n);
        System.out.println(s);

        if (s.contains("11")) {
            s = s.replaceFirst("11", "");
            if (s.contains("11")) {
                return false;
            } else {
                return true;
            }
        }

        return false;

    }


    public static int maxDigitRange(int[] nums) {
        System.out.println(nums.length);

        Map<Integer, Integer> map1 = new HashMap<>();

        for (int num : nums) {
            map1.put(num, map1.getOrDefault(num, 0) + 1);
        }

        int[] ans = new int[nums.length];

        int maxRange = Integer.MIN_VALUE;
        Map<Integer, Integer> map = new HashMap<>();

        int index = 0;
        for (int num : nums) {
            int max = Integer.MIN_VALUE;
            int min = Integer.MAX_VALUE;

            int temp = num;

            while (temp != 0) {
                int t = temp % 10;
                max = Math.max(max, t);
                min = Math.min(min, t);
                temp = temp / 10;
            }

            maxRange = Math.max(maxRange, max - min);
            map.put(num, max - min);
            ans[index] = max - min;
            index++;
        }

        int sum = 0;

        for (int i = 0; i < ans.length; i++) {
            if (ans[i] == maxRange) {
                sum += nums[i];
            }
        }

        return sum;
    }
}
