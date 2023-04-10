package Model.Expression;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Type.IType;
import Model.Type.RefType;
import Model.Value.IValue;
import Model.Value.RefValue;

public class ReadHeapExp implements IExp{
    private IExp expression;
    public ReadHeapExp(IExp exp){
        expression=exp;
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType type = expression.typeCheck(typeEnv);
        if (type instanceof RefType) {
            RefType refType = (RefType) type;
            return refType.getInner();
        } else
            throw new MyException("The rH argument is not a RefType.");
    }
    @Override
    public IValue eval(MyIDictionary<String,IValue> symTable, MyIHeap heap)throws MyException {
        IValue value=expression.eval(symTable,heap);
        if(!(value instanceof RefValue))
            throw new MyException(value+" is not of RefType");
        RefValue refValue=(RefValue) value;
        return heap.get(refValue.getAddress());
    }

    @Override
    public IExp deepCopy(){
        return new ReadHeapExp(expression.deepCopy());
    }

    @Override
    public String toString(){
        return "ReadHeap("+expression+")";
    }
}
