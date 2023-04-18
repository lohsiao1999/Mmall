package ListNode;

/*
* 复杂链表的复制，除了指向下一个节点，还random指向任意节点
 */

import java.util.HashMap;
import java.util.Map;

public class CopyListNode {

    public static void main(String[] args) {
        Node n1 = new Node(7);
        Node n2 = new Node(13);
        Node n3 = new Node(11);
        Node n4 = new Node(10);
        Node n5 = new Node(1);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n2.random = n1;
        n3.random = n5;
        n4.random = n3;
        n5.random = n1;
        Node node = copyRandomList(n1);
        System.out.println(node);
    }

    /*
    * 方法1：通过map存储节点信息，并构建链表
    * 时间复杂度：O(N)两轮遍历
    * 空间复杂度：O(N)哈希表额外占用空间；
    public Node copyRandomList(Node head) {
        Map<Node,Node> map = new HashMap<>();
        Node temp = head;
        while (temp != null){
            Node node = new Node(temp.val);
            map.put(temp,node);
            temp = temp.next;
        }
        temp = head;
        while (temp != null){
            Node node = map.get(temp);
            node.next = map.get(temp.next);
            node.random = map.get(temp.random);
            temp = temp.next;
        }
        return map.get(head);
    }
     */

     /*
    * 方法1：构建 原节点1 -> 新节点1 -> 原节点2 -> 新节点2 -> 原节点3 -> ... 的拼接链表
    * 时间复杂度：O(N)两轮遍历
    * 空间复杂度：O(1)节点引用变量使用常数大小的额外空间；
    */
    public static Node copyRandomList(Node head) {
        if(head == null) return head;
        Node temp = head;
        //构建拼接链表
        while(temp != null){
            Node node = new Node(temp.val);
            node.next = temp.next;
            temp.next = node;
            temp = node.next;
        }
        temp = head;
        while (temp != null){
            if(temp.random != null ){
                temp.next.random = temp.random.next;
            }
            temp = temp.next.next;
        }
        temp = head;
        Node res = head.next,point = head.next;
        //构建链表的random节点并拆分拼接链表
        while (point.next != null){
            temp.next = temp.next.next;
            point.next = point.next.next;
            temp = temp.next;
            point = point.next;
        }
        temp.next = null;
        return res;
    }
}

class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
