package data_structure;

//链队结点
class QNode<T>{
    public T data;//数据域
    public QNode<T> next;//指针域

    //初始化1
    public QNode(){
        this.data = null;
        this.next = null;
    }
}

public class MyQueue<T> implements IQueue<T> {
    private QNode<T> front;//队头指针
    private QNode<T> rear;//队尾指针
    private int maxSize;//为了便于操作，使用这个变量表示链队的数据容量

    //初始化
    public MyQueue(){
        this.front = new QNode<T>();
        this.rear = new QNode<T>();
        this.maxSize = 0;
    }

    //初始化队列
    public void initQueue(){
        front.next = null;
        rear.next = null;
        maxSize = 0;
    }

    //队空判断
    @Override
    public boolean isEmpty(){
        return front.next == null || rear.next == null;
    }

    //进队
    @Override
    public void push(T t){
        QNode<T> node = new QNode<>();
        node.data = t;
        if(isEmpty()){
            //第一次
            front.next = node;
            rear.next = node;
        }
        else{
            node.next = front.next;
            front.next = node;
        }
        maxSize++;
    }
    //出队
    @Override
    public T pop(){
        if(isEmpty())
            return null;//队为空时，无法出队
        else if(maxSize==1){
            //队只有一个元素时直接初始化即可
            QNode<T> node  = front.next;
            initQueue();
            return node.data;
        }
        else{
            //准备工作
            QNode<T> p = front;//使用p指针来遍历队列
            for(int i=1;i<maxSize-1;i++)
                p = p.next;
            //pop
            QNode<T> node = rear.next;
            rear.next = p.next;
            maxSize--;
            return node.data;
        }
    }

}