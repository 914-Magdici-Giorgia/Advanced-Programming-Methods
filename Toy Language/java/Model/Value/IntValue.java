package Model.Value;

import Model.Type.IType;
import Model.Type.IntType;

public class IntValue implements IValue{

    int val;
    public IntValue(int v){
        val=v;
    }

    public int getVal(){
        return val;
    }

    @Override
    public IType getType(){
        return new IntType();
    }

    @Override //object method
    public boolean equals(Object another) {
        if (!(another instanceof IntValue))
            return false;
        IntValue castVal = (IntValue) another;
        return this.val == castVal.val;
    }

    @Override
    public String toString(){
        return String.valueOf(val);
    }

    @Override
    public IValue deepCopy(){
        return new IntValue(val);
    }
}
