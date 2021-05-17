package data_structure;

public interface IQueue<T> {
    void push(T t);
    T pop();
    boolean isEmpty();
}
