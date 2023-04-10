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

public class NewStmt implements IStmt{
    private String varName;
    private IExp expression;

    public NewStmt(String var,IExp exp){
        varName=var;
        expression=exp;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();
        if (!symTable.isVarDef((varName)))
            throw new MyException(varName + " is not in symTable");
        IValue varValue = symTable.lookUp(varName);
        if (!(varValue.getType() instanceof RefType))
            throw new MyException(varName + " is not of RefType");
        IValue evaluated = expression.eval(symTable, heap);
        IType locationType = ((RefValue) varValue).getLocationType();
        if (!locationType.equals(evaluated.getType()))
            throw new MyException(varName + " is not of " + evaluated.getType());
        int newPosition = heap.add(evaluated);
        symTable.put(varName, new RefValue(newPosition, locationType));
        state.setSymTable(symTable);
        state.setHeap(heap);
       // return state;
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeVar = typeEnv.lookUp(varName);
        IType typeExpr = expression.typeCheck(typeEnv);
        if (typeVar.equals(new RefType(typeExpr)))
            return typeEnv;
        else
            throw new MyException("NEW statement: right hand side and left hand side have different types.");
    }
    @Override
    public IStmt deepCopy(){
        return new NewStmt(varName,expression.deepCopy());
    }
    @Override
    public String toString(){
        return "New("+varName+","+expression+")";
    }
}
