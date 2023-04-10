package Model.Type;

import Model.Value.IValue;
import Model.Value.RefValue;

public class RefType implements  IType{
    private IType inner;

    public RefType(IType inner){
        this.inner=inner;
    }
    public IType getInner(){
        return inner;
    }

    @Override
    public boolean equals(IType another){
        if(another instanceof RefType)
            return inner.equals(((RefType) another).getInner());
        else
            return false;
    }
    @Override
    public String toString(){
        return "Ref("+inner.toString()+")";}
    public IValue defaultValue(){
        return new RefValue(0,inner);
    }

    @Override
    public IType deepCopy(){
        return new RefType(inner.deepCopy());
    }

}
