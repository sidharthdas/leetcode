package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionMainClass {

    public static void main(String[] args) {
        System.out.println(invalidTransactions(new String[]{"alice,20,800,mtv","alice,50,1200,mtv"}));
    }

    static class  Transaction{
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


    public static  List<String> invalidTransactions(String[] transactions) {

        Map<String, List<Transaction>> map = new HashMap<>();

        for(String l : transactions) {
            Transaction transaction = new Transaction(l);
            if(map.containsKey(transaction.name)) {
                map.get(transaction.name).add(transaction);
            } else {
                List<Transaction> l1 = new ArrayList<>();
                l1.add(transaction);
                map.put(transaction.name, l1);
            }
        }

        List<String> inValid = new ArrayList<>();

        for(String trans : transactions) {
            Transaction transaction = new Transaction(trans);

            if(!isValid(transaction, map.getOrDefault(transaction.name, new ArrayList<>()))) {
                inValid.add(trans);
            }
        }


        return inValid;

    }

    private static boolean isValid(Transaction transaction, List<Transaction> list) {
        if (transaction.amount > 1000) {
            return false;
        }

        for(Transaction transaction1 : list) {
            if(Math.abs(transaction1.time - transaction.time) <= 60 && !transaction1.city.equals(transaction.city)) {
                return false;
            }
        }
        return true;
    }
}
