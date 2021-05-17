package data_structure;

public class MyStack<T> implements IStack<T>{
    //定义链表
    static class Node<T> {
        private T t;
        private Node next;
    }

    private Node<T> head;

    //构造函数初始化头指针
    public MyStack() {
        head = null;
    }

    //入栈
    @Override
    public void push(T t) {
        if(t == null) throw new NullPointerException();
        if(head == null) {
            head = new Node<T>();
            head.t = t;
            head.next = null;
        } else {
            Node<T> temp = head;
            head = new Node<>();
            head.t = t;
            head.next = temp;
        }
        return;
    }

    //出栈
    @Override
    public T pop() {
        T t = head.t;
        head = head.next;
        return t;
    }

    //栈空
    @Override
    public boolean isEmpty() {
        if (head == null)
            return true;
        else
            return false;
    }
}