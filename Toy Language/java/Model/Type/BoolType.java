package Model.Type;

import Model.Value.BoolValue;
import Model.Value.IValue;

public class BoolType implements IType{

    @Override
    public boolean equals(IType another){
        if (another instanceof BoolType)
            return true;
        else
            return false;
    }

    @Override
    public IValue defaultValue(){
        return new BoolValue(false);
    }

    @Override
    public IType deepCopy(){
        return new BoolType();
    }

    @Override
    public String toString(){
        return "bool";
    }
}
