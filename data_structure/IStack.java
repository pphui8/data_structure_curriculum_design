package data_structure;

public interface IStack<T> {
    void push(T t);
    T pop();
    boolean isEmpty();
}
