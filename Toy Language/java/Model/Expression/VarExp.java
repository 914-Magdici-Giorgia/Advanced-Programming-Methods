package Model.Expression;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Type.IType;
import Model.Value.IValue;

public class VarExp implements IExp{
    String id;

    public VarExp(String key){
        id=key;
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return typeEnv.lookUp(id);
    }
    @Override
    public IValue eval(MyIDictionary<String,IValue> symTable, MyIHeap heap) throws MyException{
        return symTable.lookUp(id);
    }

    @Override
    public String toString(){
        return id;
    }
    @Override
    public IExp deepCopy() {
        return new VarExp(id);
    }
}
