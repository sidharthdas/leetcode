package org.example;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
public class Solution {
    public ListNode modifiedList(int[] nums, ListNode head) {

        Set<Integer> set = Arrays.stream(nums).boxed().collect(Collectors.toSet());
        ListNode temp = head;

        while(head != null) {
            if(set.contains(head.val)) {
                head = head.next;
            }else {
                head = head.next;
            }
        }
        return temp;


    }
}
