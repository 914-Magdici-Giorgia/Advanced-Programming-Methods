package Model.ADT;

import Exceptions.MyException;

import java.util.List;

public interface MyIStack<T>{
    void push(T e);
    T pop () throws MyException;
    boolean isEmpty();
    List<T> getReverse();

    //String toString();
}
