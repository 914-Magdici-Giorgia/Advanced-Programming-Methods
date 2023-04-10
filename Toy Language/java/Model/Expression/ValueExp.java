package Model.Expression;


import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Type.IType;
import Model.Value.IValue;

public class ValueExp implements IExp{
    IValue e;

    public ValueExp(IValue x){
        e=x;
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return e.getType();
    }
    @Override
    public IValue eval(MyIDictionary<String, IValue> symtable, MyIHeap heap)throws MyException {
        return e;
    }

    @Override
    public IExp deepCopy(){
        return new ValueExp(e.deepCopy());
    }

    @Override
    public String toString(){
        return e.toString();
    }
}
