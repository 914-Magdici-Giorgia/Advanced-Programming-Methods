package Model.ADT;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T>{
    private ArrayList<T> output;
    public MyList(){
        output=new ArrayList<T>();
    }

    @Override
    public void add(T elem){
        output.add(elem);
    }

    @Override
    public String toString(){
        return output.toString();
    }

    @Override
    public List<T> getList() {
        synchronized (this) {
            return output;
        }
    }
}
