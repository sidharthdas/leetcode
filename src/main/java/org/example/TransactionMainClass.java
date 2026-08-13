package org.example;


import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

public class TransactionMainClass {

    public static void main(String[] args) {
        //System.out.println(invalidTransactions(new String[]{"alice,20,800,mtv","alice,50,1200,mtv"}));

        //System.out.println(halveArray(new int[]{5, 19, 8, 1}));

        //s = "aababcaab", maxLetters = 2, minSize = 3, maxSize = 4
        System.out.println(maxFreq("abcde", 2,3,3));
    }

    public static int maxFreq(String s, int maxLetters, int minSize, int maxSize) {

        int len = s.length();

        int count = 0;

        List<String> list = new ArrayList<>();

        for(int i = 0; i < len; i++) {
            Set<Character> set = new HashSet<>();
            int size = 0;
            StringBuilder stringBuilder = new StringBuilder();
            for(int j = i; j < len; j++) {



                if(size >= minSize && size <= maxSize) {
                    if(set.size() <= maxLetters) {
                        list.add(stringBuilder.toString());
                        count++;
                    } else  {
                        break;
                    }

                }
                if(size > maxSize) {
                    break;
                }

                stringBuilder.append(s.charAt(j));
                set.add(s.charAt(j));
                size++;
            }

            if(size >= minSize && size <= maxSize && set.size() <= maxLetters) {
                list.add(stringBuilder.toString());
            }
        }

        System.out.println(list);

        if(list.isEmpty()) return 0;

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
        double s = 0, k = 0; int i = 0;
        PriorityQueue<Double> pq = new PriorityQueue<>(Collections.reverseOrder());
        for (int x: nums) {
            pq.offer((double)x);
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
