package ListNode;

public class MergeListNode {

    /*
    * 合并两个有序数组 https://leetcode.cn/problems/he-bing-liang-ge-pai-xu-de-lian-biao-lcof/
    * 技巧：先创建临时节点作为头节点
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode temp = new ListNode(0);
        ListNode cur = temp;
        while(l1 != null && l2!= null){
            if(l1.val < l2.val){
                cur.next = l1;
                l1 = l1.next;
            }else{
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }
        cur.next = l1 == null ? l2 : l1;
        return temp.next;
    }
}
