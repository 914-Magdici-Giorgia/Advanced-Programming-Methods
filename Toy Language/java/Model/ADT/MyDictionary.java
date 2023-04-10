package Model.ADT;

import Exceptions.MyException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary <K,V> implements MyIDictionary<K,V> {
    private Map<K,V> map;
    public MyDictionary(){
        map=new HashMap<K,V>();
    }

    @Override
    public void put(K key, V value){
        map.put(key,value);
    }

    @Override
    public boolean isVarDef(K key){
        return map.containsKey(key);
    }

    @Override
    public void update(K key, V value){
        map.put(key, value);
    }

    @Override
    public V lookUp(K key){
        return map.get(key);
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    public Map<K, V> getContent() {
        return map;
    }
    @Override
    public String toString(){
        return map.toString();
    }

    @Override
    public void remove(K key) throws MyException {
        if (!isVarDef(key))
            throw new MyException(key + " is not defined.");
        map.remove(key);
    }

    @Override
    public MyIDictionary<K, V> deepCopy() throws MyException {
        MyIDictionary<K, V> toReturn = new MyDictionary<>();
        for (K key: keySet())
            toReturn.put(key, lookUp(key));
        return toReturn;
    }

}
