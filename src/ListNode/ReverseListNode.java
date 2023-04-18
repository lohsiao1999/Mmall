package ListNode;

/**
 * 反转链表
 */

public class ReverseListNode {

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode two = new ListNode(2);
        ListNode three = new ListNode(3);
        ListNode four = new ListNode(4);
        ListNode five = new ListNode(5);
        head.next = two;
        two.next = three;
        three.next = four;
        four.next = five;
        ListNode list = reverseList(head);
        System.out.println(list);
    }

    /*
     * 方法1：遍历两遍，第一遍获取链表长度，第二遍获取值并赋值
    public int[] reversePrint(ListNode head) {
        ListNode temp = head;
        int count = 0;
        while(temp != null){
            ++count;
            temp = temp.next;
        }
        int[] result = new int[count];
        temp = head;
        while(temp != null){
            result[--count] = temp.val;
            temp = temp.next;
        }
        return result;
    }
     */

    //-----------------------------------------------------------------------------------

    /*
     * 方法2：使用递归
    int[] result;

    public int[] reversePrint(ListNode head) {
        backTrace(head,0);
        return result;
    }

    public int backTrace(ListNode node,int length){
        if(node == null){
            result = new int[length];
            return 0;
        }
        int index = backTrace(node.next,length+1);
        result[index] = node.val;
        return index+1;
    }
     */

    //-----------------------------------------------------------------------------------

    /*
     * 递归方法1
     * n1 -> n2 -> ...-> n(k-1) -> n(K) -> null
     * 先递归至n(K)节点，即反转后的头节点；返回上一层n(k-1)节点，temp变量此时指向n(K)节点，因此将temp.next指向当前节点，即n(k-1)节点
     * 最后将n1.next指向null
    static ListNode result;

    public static ListNode reverseList(ListNode head) {
        if(head == null) return head;
        backTrace(head);
        head.next = null;
        return result;
    }

    public static ListNode backTrace(ListNode node){
        if(node.next == null){
            result = node;
            return result;
        }
        ListNode temp = backTrace(node.next);
        temp.next = node;
        return node;
    }
     */

    //-----------------------------------------------------------------------------------

    /*
     * 递归方法2 时间复杂度 O(n)：需遍历链表1次；空间复杂度O(n)：递归调用的栈空间
     * n1 -> n2 -> ... -> n(k) -> n(k+1) <- n(k+2)
     * 若当前处于 n(k)节点，则存在 n(k).next.next = n(k)，其中n(k).next 指向n(k+1)，而反转链表，需要 n(k+1).next 指向 n(k)
     * head.next = null 则是因为反转后，原先的头节点变成尾节点，因此其next需要指向null，避免出现环
     * newNode始终指向反转后的头节点
    public static ListNode reverseList(ListNode head) {
        if(head == null || head.next == null) return head;
        ListNode newNode = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return newNode;
    }
     */

    /*
     * 迭代，时间复杂度 O(n)：与链表长度相关，需遍历链表1次；空间复杂度O(1)
     */
    public static ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode curr = head;
        while (curr!=null){
            ListNode next = curr.next;
            //当前节点的next指向前一个节点
            curr.next = pre;
            //前一个节点指向当前节点
            pre = curr;
            //当前节点指向当前节点的原来的next节点
            curr = next;
        }
        return pre;
    }
}

class ListNode{
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
