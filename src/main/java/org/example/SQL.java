package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQL {

    public static void main(String[] args) {
        // Creates three tables.
        SQL sql = new SQL(List.of("one", "two", "three"), List.of(2, 3, 1));

// Adds a row to the table "two" with id 1. Returns True.
        System.out.println(sql.ins("two", List.of("first", "second", "third")));

// Returns the value "third" from the third column
// in the row with id 1 of the table "two".
        System.out.println(sql.sel("two", 1, 3));

// Adds another row to the table "two" with id 2. Returns True.
        System.out.println(sql.ins("two", List.of("fourth", "fifth", "sixth")));

// Exports the rows of the table "two".
// Currently, the table has 2 rows with ids 1 and 2.
        System.out.println(sql.exp("two"));

// Removes the first row of the table "two". Note that the second row
// will still have the id 2.
        sql.rmv("two", 1);

// Returns the value "fifth" from the second column
// in the row with id 2 of the table "two".
        System.out.println(sql.sel("two", 2, 2));

// Exports the rows of the table "two".
// Currently, the table has 1 row with id 2.
        System.out.println(sql.exp("two"));
    }

    Map<String, List<List<String>>> map;
    Map<String,  Integer> nameAndColumnMap;

    public SQL(List<String> names, List<Integer> columns) {
        map = new HashMap<>();
        nameAndColumnMap = new HashMap<>();

        int len = names.size();

        for(int i = 0; i < len; i++) {
            nameAndColumnMap.put(names.get(i), columns.get(i));
            map.put(names.get(i), new ArrayList<>());
        }
    }

    public boolean ins(String name, List<String> row) {

        if(!nameAndColumnMap.containsKey(name)) return false;
        if(nameAndColumnMap.get(name) != row.size()) return false;

        List<List<String>> list = map.get(name);
        list.add(row);

        return true;

    }

    public void rmv(String name, int rowId) {

        if(map.containsKey(name) && map.get(name).size() > rowId-1) {
            map.get(name).set(rowId-1, null);
        }

    }

    public String sel(String name, int rowId, int columnId) {

        if(map.containsKey(name) && map.get(name).size() > rowId-1
                && map.get(name).get(rowId-1) != null
                && nameAndColumnMap.get(name) > columnId-1) {
            return map.get(name).get(rowId-1).get(columnId-1);
        }
        return "<null>";

    }

    public List<String> exp(String name) {

        if(map.containsKey(name)) {
            List<String> ans = new ArrayList<>();

            int index = 0;
            for(List<String> list  : map.get(name)) {
                index += 1;
                if(list != null ) {
                    ans.add(index+","+String.join(",", list));
                }
            }
            return ans;
        }
        return List.of();
    }
}
