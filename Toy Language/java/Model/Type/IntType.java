package Model.Type;

import Model.Value.IValue;
import Model.Value.IntValue;

public class IntType implements IType{

    @Override
    public boolean equals(IType another){
        if(another instanceof IntType)
            return true;
        else
            return false;
    }

    @Override
    public IValue defaultValue(){
        return new IntValue(0);
    }

    @Override
    public IType deepCopy(){
        return new IntType();
    }

    @Override
    public String toString(){
        return "int";
    }
}
