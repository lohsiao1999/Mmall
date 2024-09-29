package ListNode;

public class SortListNode {

    public static void main(String[] args) {
        SortListNode sortListNode = new SortListNode();
        ListNode n1 = new ListNode(-1);
        ListNode n2 = new ListNode(5);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(0);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        sortListNode.sortList(n1);
    }

    public ListNode sortList(ListNode head) {
        if(head == null || head.next == null) return head;

        //计算链表长度
        int length = 0;
        ListNode point = head;
        while (point!= null){
            length +=1;
            point = point.next;
        }
        //初始化虚拟节点
        ListNode dummy = new ListNode(0,head);
        //循环分割链表并排序，初始分割长度1；分割链表后进行两两合并排序，完成后分割长度翻倍
        for(int subLen = 1 ;subLen < length;subLen <<= 1){
            ListNode tmpHead = dummy; //记录每次分割的头节点
            ListNode cur = tmpHead.next; //记录每次分割的尾节点
            while(cur != null){
                ListNode head1 = cur;
                for(int i=1;i<subLen && cur != null;i++ ){
                    cur = cur.next;
                }
                //如果分割出来的第一个链表已经到了原链表的尾部，则不再执行下面逻辑。因为不能分割出第二个链表用于合并
                if(cur == null){
                    tmpHead.next = head1;
                    continue;
                }
                ListNode head2 = cur.next;
                cur.next = null; //断开第一个链表和第二个链表的连接
                cur = head2;
                for(int i=1;i<subLen && cur!= null;i++){
                    cur = cur.next;
                }
                //分割出第一个和第二个链表后，剩余的后续节点
                ListNode next = null;
                //只有在分割出第二个链表且仍然存在后续节点的情况下需要记录下后续节点
                if(cur != null){
                    next = cur.next;
                    cur.next = null; //断开第二个节点和后续节点的链接
                }

                //合并两个有序数组
                ListNode mergeList = mergeNode(head1, head2);
                //临时头节点指向合并后的有序数组
                tmpHead.next = mergeList;
                //零时头节点遍历至合并后数组的最后一个节点，也就是分割的第二个链表的尾节点
                while(tmpHead.next != null){
                    tmpHead = tmpHead.next;
                }
                cur = next; //将cur指向后续节点，继续循环拆分
            }
        }
        return dummy.next;
    }

    //合并两个有序列表
    public ListNode mergeNode(ListNode head1,ListNode head2){
        ListNode dummy = new ListNode(0);
        ListNode point = dummy;
        while (head1 != null && head2!=null){
            if(head1.val > head2.val){
                point.next = head2;
                head2 = head2.next;
            }else{
                point.next = head1;
                head1 = head1.next;
            }
            point = point.next;
        }
        point.next = head1 == null? head2 : head1;
        return dummy.next;
    }
}
