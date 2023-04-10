package Model.Value;

import Model.Type.BoolType;
import Model.Type.IType;

public class BoolValue implements IValue{
    boolean val;

    public BoolValue(boolean v){
        val=v;
    }

    @Override
    public IType getType(){
        return new BoolType();
    }

    public boolean getVal(){
        return val;
    }
    @Override
    public boolean equals (Object another){
        if(!(another instanceof BoolValue))
            return false;
        BoolValue castVal= (BoolValue) another;
        return val == castVal.val;
    }
    @Override
    public IValue deepCopy(){
        return new BoolValue(val);
    }
    @Override
    public String toString(){
        return String.valueOf(val);
    }
}
