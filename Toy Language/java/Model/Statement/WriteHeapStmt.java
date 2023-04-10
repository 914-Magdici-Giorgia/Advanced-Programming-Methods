package Model.Statement;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Expression.IExp;
import Model.PrgState.PrgState;
import Model.Type.IType;
import Model.Type.RefType;
import Model.Value.IValue;
import Model.Value.RefValue;

public class WriteHeapStmt implements IStmt{
    private String varName;
    private IExp expression;

    public WriteHeapStmt(String var,IExp exp){
        varName=var;
        expression=exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException{
        MyIDictionary<String, IValue> symTable=state.getSymTable();
        MyIHeap heap=state.getHeap();
        if(!symTable.isVarDef(varName))
            throw new MyException(varName+" is not in the symTable");
        IValue value=symTable.lookUp(varName);
        if(!(value instanceof RefValue))
            throw new MyException(value+" is not of RefType");
        RefValue refValue=(RefValue) value;
        IValue evaluated=expression.eval(symTable,heap);
        if(!evaluated.getType().equals(refValue.getLocationType()))
            throw new MyException(evaluated+" not of "+refValue.getLocationType());
        heap.update(refValue.getAddress(),evaluated);
        state.setHeap(heap);
        // return state;
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if (typeEnv.lookUp(varName).equals(new RefType(expression.typeCheck(typeEnv))))
            return typeEnv;
        else
            throw new MyException("WriteHeap: right hand side and left hand side have different types.");
    }

        @Override
    public IStmt deepCopy(){
        return new WriteHeapStmt(varName,expression.deepCopy());
    }

    @Override
    public String toString(){
        return "WriteHeap("+varName+", "+expression+")";
    }

}
