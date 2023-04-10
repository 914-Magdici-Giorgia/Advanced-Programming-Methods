package Model.ADT;

import Exceptions.MyException;

import java.util.Map;
import java.util.Set;


public interface MyIDictionary<K,V> {
    void put(K key, V value);
    boolean isVarDef(K key);
    void update(K key, V value);
    V lookUp(K key);
    Map<K, V> getContent();
    void remove(K key) throws MyException;
    MyIDictionary<K, V> deepCopy() throws MyException;
   // String toString();
    Set<K> keySet();
}
